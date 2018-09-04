package cs601.project1;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AmazonSearch {
	public enum TYPE {REVIEW, QA}
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		if(args.length !=4) {
			System.out.println("Usage: java -cp project1.jar cs601.project1.AmazonSearch -reviews <review_file_name> -qa <qa_file_name>");
			System.exit(1);
		}
		
		Path reviewPath = Paths.get(args[1]);
		Path qaPath = Paths.get(args[3]);
		InvertedIndex reviewIndex = new InvertedIndex();
		InvertedIndex qaIndex = new InvertedIndex();
		
		try {
			reviewIndex.addToIndex(reviewPath, TYPE.REVIEW);
			System.out.println("Finished phase 1");
			qaIndex.addToIndex(qaPath, TYPE.QA);
			long endTime = System.nanoTime();
			double time = (double)(endTime - startTime) / 1000000000.0;
			System.out.println(time);
			//AmazonSearch.find("3998899561", reviewIndex, qaIndex);
			AmazonSearch.searchByWord("charge", reviewIndex);
			System.out.println("Finished");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void find(String asin, InvertedIndex reviewIndex, InvertedIndex qaIndex) {
		ArrayList<Review> reviews = (ArrayList<Review>) reviewIndex.getProductByAsin(asin);
		ArrayList<Qa> qas = (ArrayList<Qa>) qaIndex.getProductByAsin(asin);
		
		System.out.println("Reviews: ");
		for(Review review : reviews) {
			System.out.println(review.getReviewText());
		}
		System.out.println("Questions and Answers: ");
		for(Qa qa : qas) {
			System.out.format("Question: %s - Answer: %s \n", qa.getQuestion(), qa.getAnswer());
		}
	}
	
	private static void searchByWord(String term, InvertedIndex index) {
		index.getLineByWordAndSortByFreq(term);
	}

}
