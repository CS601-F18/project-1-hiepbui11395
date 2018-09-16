package cs601.project1;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AmazonSearch {
	public enum TYPE {REVIEW, QA}
	public static void main(String[] args) {
		if(args.length !=4 || !args[0].equals("-reviews") || !args[2].equals("-qa")) {
			System.out.println("Usage: java -cp project1.jar cs601.project1.AmazonSearch -reviews <review_file_name> -qa <qa_file_name>");
			System.exit(1);
		}
		
		InvertedIndex reviewIndex = new InvertedIndex();
		InvertedIndex qaIndex = new InvertedIndex();

		try {
			long startTime = System.nanoTime();
			Utils.addToIndex(args[1], reviewIndex, TYPE.REVIEW);
			Utils.addToIndex(args[3], qaIndex, TYPE.QA);
			//Check time of the program
			long endTime = System.nanoTime();
			double time = (double)(endTime - startTime) / 1000000000.0;
			System.out.println("After: " + time);
			System.out.println("Creating index finished! You can use the program now");
			Utils.executeCommand(reviewIndex, qaIndex);
		} catch (IOException e) {
			System.out.println("Can not read your file, please try again!");
		}
	}
}
