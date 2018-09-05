package cs601.project1;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

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
			qaIndex.addToIndex(qaPath, TYPE.QA);
			System.out.println("Creating index finished! You can use the program now");
			AmazonSearch.executeCommand(reviewIndex, qaIndex);
			//Check time of the program
			long endTime = System.nanoTime();
			double time = (double)(endTime - startTime) / 1000000000.0;
			System.out.println(time);
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}

	private static void executeCommand(InvertedIndex reviewIndex, InvertedIndex qaIndex) {
		String commandLine = "";
		String command = "";
		String value = "";
//		Console console = System.console();
		Scanner sc = new Scanner(System.in);
		System.out.println("List of command\n"
				+ "    - find {asin}\n"
				+ "    - reviewsearch {term}\n"
				+ "    - qasearch {term}\n"
				+ "    - reviewpartialsearch {term}\n"
				+ "    - qapartialsearch {term}\n");
		while(!command.equals("exit"))
		{
//			commandLine = console.readLine("Enter your command: ");
			System.out.print("Enter your command: ");
			commandLine = sc.nextLine();
			String commandValues[] = commandLine.trim().split(" ");
			if(commandValues.length!=2) {
				if(commandValues.length==1 && commandValues[0].equals("help")) {
					command = "help";
				} else {
					command = "wrongcommand";
				}
			}
			else {
				command = commandValues[0];
				value = commandValues[1];
			}
			switch(command) {
			case "find": 
				AmazonSearch.find(value, reviewIndex, qaIndex);
				break;
			case "reviewsearch":
				AmazonSearch.searchByWord(value, reviewIndex);
				break;
			case "qasearch":
				AmazonSearch.searchByWord(value, qaIndex);
				break;
			case "reviewpartialsearch":
				AmazonSearch.searchByPartialWord(value, reviewIndex);
				break;
			case "qapartialsearch":
				AmazonSearch.searchByPartialWord(value, qaIndex);
				break;
			case "help":
				System.out.println("List of command\n"
						+ "    - find {asin}\n"
						+ "    - reviewsearch {term}\n"
						+ "    - qasearch {term}\n"
						+ "    - reviewpartialsearch {term}\n"
						+ "    - qapartialsearch {term}\n");
				break;
			default:
				System.out.println("You may enter wrong command, please try again.\n If you don't remember, type \"help\"");
			}
		}
		sc.close();
		System.out.println("Thank you for using the program!");
	}

	private static void find(String asin, InvertedIndex reviewIndex, InvertedIndex qaIndex) {
		ArrayList<Product> reviews = reviewIndex.getProductByAsin(asin);
		ArrayList<Product> qas = qaIndex.getProductByAsin(asin);

		System.out.println("Reviews: ");
		AmazonSearch.printProductReviewOrQa(reviews);
		System.out.println("Questions and Answers: ");
		AmazonSearch.printProductReviewOrQa(qas);
	}

	private static void searchByWord(String term, InvertedIndex index) {
		ArrayList<Product> products = index.getLineByWordAndSortByFreq(term);
		AmazonSearch.printProductReviewOrQa(products);
	}

	private static void searchByPartialWord(String term, InvertedIndex index) {
		ArrayList<Product> products = index.getLineByPartialWordAndSortByFreq(term);
		AmazonSearch.printProductReviewOrQa(products);
	}

	private static void printProductReviewOrQa(ArrayList<Product> products) {
		int count = 0;
		for(Product product : products) {
			count++;
			if(product instanceof Review) {
				System.out.println(((Review)product).toString());
			} else {
				System.out.println(((Qa)product).toString());
			}
		}
		System.out.println(count);
	}
}
