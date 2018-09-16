package cs601.project1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonParseException;

public class InvertedIndex {
	private HashMap<String, ListLocation> indexes;
	private HashMap<String, ArrayList<Product>> productsByAsin;
	private HashMap<String, Product> productsByLine;

	public InvertedIndex(HashMap<String, ListLocation> indexes, HashMap<String, ArrayList<Product>> productsByAsin,
			HashMap<String, Product> productsByLine) {
		super();
		this.indexes = indexes;
		this.productsByAsin = productsByAsin;
		this.productsByLine = productsByLine;
	}

	public InvertedIndex() {
		this.indexes = new HashMap<String, ListLocation>();
		this.productsByAsin = new HashMap<String, ArrayList<Product>>();
		this.productsByLine = new HashMap<String, Product>();
	}

	public HashMap<String, ListLocation> getIndexes() {
		return indexes;
	}

	public void setIndexes(HashMap<String, ListLocation> index) {
		this.indexes = index;
	}

	public HashMap<String, ArrayList<Product>> getProductsByAsin() {
		return productsByAsin;
	}

	public void setProductsByAsin(HashMap<String, ArrayList<Product>> productsByAsin) {
		this.productsByAsin = productsByAsin;
	}

	public HashMap<String, Product> getProductsByLine() {
		return productsByLine;
	}

	public void setProductsByLine(HashMap<String, Product> productsByLine) {
		this.productsByLine = productsByLine;
	}
	
	public void addProductToDictionary(Product product, String asin, String locationCode) {
		if(this.getProductsByAsin().containsKey(asin)) {
			this.getProductsByAsin().get(asin).add(product);
		} else {
			this.getProductsByAsin().put(asin, (new ArrayList<>(List.of(product))));
		}
		this.getProductsByLine().put(locationCode, product);
	}

	/**
	 * Get a textReviewText or Question/Answer split it to words and put in to the InvertedIndex
	 *
	 * @param  text  a textReview or Question/Answer
	 * @param  locationCode work as an Id of each query
	 * @throws JsonParseException
	 */
	public void addWordToIndex(String text, String locationCode) {
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
				listLocation.addToList(new Location(locationCode, word.getValue()));
			} else {
				ListLocation listLocation = new ListLocation(new ArrayList<Location>());
				listLocation.addToList(new Location(locationCode, word.getValue()));
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
		if(this.getIndexes().containsKey(word)) {
			ListLocation listLocation = this.getIndexes().get(word);
			//ArrayList<Location> locations = listLocation.sortByCount();
			for(Location location : listLocation.getListLocation()){
				if(this.getProductsByLine().containsKey(location.getLocationCode())) {
					result.add(this.getProductsByLine().get(location.getLocationCode()));
				}
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
		//ArrayList<Location> locations = listLocation.sortByCount();
		for(Location location : listLocation.getListLocation()) {
			if(this.getProductsByLine().containsKey(location.getLocationCode())) {
				result.add(this.getProductsByLine().get(location.getLocationCode()));
			}
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
		if(productsByAsin.containsKey(asin)) {
			results = productsByAsin.get(asin);
		}
		return results;
	}
}
