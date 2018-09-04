package cs601.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import cs601.project1.AmazonSearch.TYPE;

public class InvertedIndex {
	private HashMap<String, ListLocation> index;
	private ArrayList<Product> products;

	private static final Gson gson = new Gson();

	public InvertedIndex(HashMap<String, ListLocation> index, ArrayList<Product> product) {
		super();
		this.index = index;
		this.products = product;
	}

	public InvertedIndex() {
		// TODO Auto-generated constructor stub
		this.index = new HashMap<String, ListLocation>();
		this.products = new ArrayList<Product>();
	}

	public HashMap<String, ListLocation> getIndex() {
		return index;
	}



	public void setIndex(HashMap<String, ListLocation> index) {
		this.index = index;
	}

	public ArrayList<Product> getData() {
		return products;
	}

	public void setData(ArrayList<Product> product) {
		this.products = product;
	}

	/**
	 * Add word from file to the InvertedIndex
	 * 
	 * @param type
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
					jspe.printStackTrace();
					continue;
				}
			}
		} catch (IOException ioe) {
			throw ioe;
		};
	}

	private void addReview(String line, int lineNumber) throws JsonParseException{
		try {
			Review review = gson.fromJson(line, Review.class);
			review.lineNumber = lineNumber;
			this.addWordToIndex(review.getReviewText(), lineNumber);
			products.add(review);
		} catch(JsonParseException jspe) {
			jspe.printStackTrace();
		}
	}

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

	private void addWordToIndex(String text, int lineNumber) {
		String[] words = text.split("[\\s@&.?$+-:/]+");
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
			if(index.containsKey(word.getKey())) {
				ListLocation listLocation = index.get(word.getKey());
				listLocation.addToList(new Location(lineNumber, word.getValue()));
			} else {
				ListLocation listLocation = new ListLocation(new ArrayList<Location>());
				listLocation.addToList(new Location(lineNumber, word.getValue()));
				index.put(word.getKey(), listLocation);
			}
		}
	}

	public void getLineByWordAndSortByFreq(String word){
		int count = 0;
		if(this.getIndex().containsKey(word)) {
			ListLocation listLocation = this.getIndex().get(word);
			ArrayList<Location> locations = listLocation.sortByCount();
			for(Location location : locations) {
				ArrayList<Product> products = this.getProductByLineNumber(location.getLineNumber());
				for(Product product : products) {
					count++;
					if(product instanceof Review) {
						System.out.println(((Review)product).getReviewText());
					} else {
						System.out.format("Question: %s - Answer: %s \n", 
								((Qa)product).getQuestion(), 
								((Qa)product).getAnswer());
					}
				}
			}
		}
		System.out.println(count);
	}
	
	public ArrayList<? extends Product> getProductByAsin(String asin){
		ArrayList<Product> results = new ArrayList<Product>();
		for(Product p : this.products) {
			if(p.getAsin().equals(asin)) {
				results.add(p);
			}
		}
		return results;
	}
	
	public ArrayList<Product> getProductByLineNumber(int lineNumber){
		ArrayList<Product> results = new ArrayList<Product>();
		for(Product p : this.products) {
			if(p.getLineNumber() == lineNumber) {
				results.add(p);
			}
		}
		return results;
	}
}
