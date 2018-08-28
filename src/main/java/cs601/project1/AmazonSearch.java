package cs601.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

public class AmazonSearch {
	public enum TYPE {REVIEW, QA}
	public static void main(String[] args) {
		if(args.length !=4) {
			System.out.println("Usage: java -cp project1.jar cs601.project1.AmazonSearch -reviews <review_file_name> -qa <qa_file_name>");
			System.exit(1);
		}
		
		Path reviewPath = Paths.get(args[1]);
		Path qaPath = Paths.get(args[3]);
		ArrayList<Review> reviews = new ArrayList<Review>();
		//HashMap<String, ArrayList<WordIndex>> invertedIndex = new HashMap<String, ArrayList<WordIndex>>();
		HashMap<String, HashMap<TYPE, Integer>> invertedIndex = new HashMap<String, HashMap<TYPE, Integer>>();
		
		Gson gson = new Gson();
		try(
				BufferedReader br = Files.newBufferedReader(reviewPath, Charset.forName("ISO-8859-1"))
				){
			//Process review file
			String line = "";
			while((line=br.readLine())!=null) {
				Review review = gson.fromJson(line, Review.class);
				reviews.add(review);
				//Get the words in the ReviewText, then add to the index and list reviews
				String[] words = review.getReviewText().split(" ");
				//Remove special character and trim the word
				for(String word : words) {
					word = word.replaceAll("[^A-Za-z0-9]", "").toLowerCase().trim();
					if(invertedIndex.containsKey(word)) {
						HashMap<TYPE, Integer> wordIndex = invertedIndex.get(word);
						wordIndex.put(TYPE.REVIEW, wordIndex.get(TYPE.REVIEW)+1);
					} else {
						//ArrayList<WordIndex> wordIndexes = new ArrayList<WordIndex>();
						//wordIndexes.add(new WordIndex(TYPE.REVIEW, 1));
						HashMap<TYPE, Integer> wordIndex = new HashMap<TYPE, Integer>();
						wordIndex.put(TYPE.REVIEW, 1);
						invertedIndex.put(word, wordIndex);
					}
				}
				System.out.println(review);
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
	}

}
