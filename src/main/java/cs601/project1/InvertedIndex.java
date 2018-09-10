package cs601.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import cs601.project1.AmazonSearch.TYPE;

public class InvertedIndex {
	private HashMap<String, ListLocation> indexes;
	private ArrayList<Product> products;

	private static final Gson gson = new Gson();

	public InvertedIndex(HashMap<String, ListLocation> index, ArrayList<Product> product) {
		super();
		this.indexes = index;
		this.products = product;
	}

	public InvertedIndex() {
		this.indexes = new HashMap<String, ListLocation>();
		this.products = new ArrayList<Product>();
	}

	public HashMap<String, ListLocation> getIndex() {
		return indexes;
	}

	public void setIndex(HashMap<String, ListLocation> index) {
		this.indexes = index;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> product) {
		this.products = product;
	}

	/**
	 * Read each line from file and put it in the InvertedIndex and list product
	 *
	 * @param  path  an URL give the location of the file to read from
	 * @param  type the enumerator to determine that it will be put in Review or Qa
	 * @throws IOException
	 */
	public void addToIndex(Path path, TYPE type) throws IOException {
		String line = "";
		int count =0;
		try (
				BufferedReader br = Files.newBufferedReader(path, Charset.forName("ISO-8859-1"));
				){
			while((line = br.readLine()) != null) {
				count++;
				//System.out.println(count);
				try {
					if(type.equals(TYPE.REVIEW)) {
						this.addReview(line, count);
					} else {
						this.addQa(line, count);
					}
				}
				catch(JsonParseException jspe) {
					//jspe.printStackTrace();
					continue;
				}
			}
		} catch (IOException ioe) {
			throw ioe;
		};
	}

	/**
	 * Parse a line and put an object to list products
	 *
	 * @param  line  a line that will be parsed to Object(Review)
	 * @param  lineNumber work as an Id of each query
	 * @throws JsonParseException
	 */
	private void addReview(String line, int lineNumber) throws JsonParseException{
		try {
			Review review = gson.fromJson(line, Review.class);
			review.lineNumber = lineNumber;
			this.addWordToIndex(review.getReviewText(), lineNumber);
			products.add(review);
		} catch(JsonParseException jspe) {
			throw jspe;
		}
	}

	/**
	 * Parse a line and put an object to list products
	 *
	 * @param  line  a line that will be parsed to Object(Qa)
	 * @param  lineNumber work as an Id of each query
	 * @throws JsonParseException
	 */
	private void addQa(String line, int lineNumber) throws JsonParseException{
		try {
			Qa qa = gson.fromJson(line, Qa.class);
			qa.lineNumber = lineNumber;
			this.addWordToIndex(qa.getAnswer() + ":" + qa.getQuestion(), lineNumber);
			products.add(qa);
		} catch(JsonParseException jspe) {
			throw jspe;
		}
	}

	/**
	 * Get a textReviewText or Question/Answer split it to words and put in to the InvertedIndex
	 *
	 * @param  text  a textReview or Question/Answer
	 * @param  lineNumber work as an Id of each query
	 * @throws JsonParseException
	 */
	private void addWordToIndex(String text, int lineNumber) {
		String[] words = text.split("\\W+");
		HashMap<String, Integer> countWords = new HashMap<String, Integer>();
		for(String word : words) {
			word = word.replaceAll("[^A-Za-z0-9]", "").toLowerCase().trim();
			if(!word.equals("")) {
				if(!countWords.containsKey(word)) {
					countWords.put(word, 1);
				} else {
					countWords.put(word, countWords.get(word)+1);
				}
			}
		}
		for(Entry<String, Integer> word : countWords.entrySet()) {
			if(indexes.containsKey(word.getKey())) {
				ListLocation listLocation = indexes.get(word.getKey());
				listLocation.addToList(new Location(lineNumber, word.getValue()));
			} else {
				ListLocation listLocation = new ListLocation(new ArrayList<Location>());
				listLocation.addToList(new Location(lineNumber, word.getValue()));
				indexes.put(word.getKey(), listLocation);
			}
		}
	}
	
	/**
	 * Input a string and find out the Review/Qa that contain that string
	 *
	 * @param  word  a key to find Review/Qa
	 * @return ArrayList<Product> a list of product with Review/Qa
	 */
	public ArrayList<Product> getLineByWordAndSortByFreq(String word){
		ArrayList<Product> result = new ArrayList<Product>();
		if(this.getIndex().containsKey(word)) {
			ListLocation listLocation = this.getIndex().get(word);
			ArrayList<Location> locations = listLocation.sortByCount();
			for(Location location : locations) {
				Product product = this.getProductByLineNumber(location.getLineNumber());
				result.add(product);
			}
		}
		return result;
	}
	
	/**
	 * Input a string and find out the Review/Qa that contain that string as a partial word
	 *
	 * @param  partialWord  a key to find Review/Qa
	 * @return ArrayList<Product> a list of product with Review/Qa
	 */
	public ArrayList<Product> getLineByPartialWordAndSortByFreq(String partialWord){
		ArrayList<Product> result = new ArrayList<Product>();
		ListLocation listLocation = new ListLocation(new ArrayList<Location>());
		for(Entry<String, ListLocation> index : indexes.entrySet()) {
			if(index.getKey().contains(partialWord)) {
				listLocation.addListLocation(index.getValue());
			}
		}
		ArrayList<Location> locations = listLocation.sortByCount();
		for(Location location : locations) {
			Product product = this.getProductByLineNumber(location.getLineNumber());
			result.add(product);
		}
		return result;
	}
	
	/**
	 * Input a string(asin) and find out the product Review/Qa have the same asin
	 *
	 * @param  asin  a key to find product Review/Qa
	 * @return ArrayList<Product> a list of product with Review/Qa to print out
	 */
	public ArrayList<Product> getProductByAsin(String asin){
		ArrayList<Product> results = new ArrayList<Product>();
		for(Product p : this.products) {
			if(p.getAsin().equals(asin)) {
				results.add(p);
			}
		}
		return results;
	}
	
	/**
	 * Input a line number and return a product base on that number
	 *
	 * @param  lineNumber  a key to find a product with Review/Qa
	 * @return Product a product with review/qa
	 */
	private Product getProductByLineNumber(int lineNumber){
		for(Product p : this.products) {
			if(p.getLineNumber() == lineNumber) {
				return p;
			}
		}
		return null;
	}
}
