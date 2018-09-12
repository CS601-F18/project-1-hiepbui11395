package cs601.project1;

import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
	/**
	 * Processing the command of user
	 * 
	 * @param reviewIndex an InvertedIndex of review file
	 * @param qaIndex an InvertedIndex of Qa file
	 */
	public static void executeCommand(InvertedIndex reviewIndex, InvertedIndex qaIndex) {
		String commandLine = "";
		String command = "";
		String value = "";
		Scanner sc = new Scanner(System.in);
		System.out.println("List of command\n"
				+ "    - find {asin}\n"
				+ "    - reviewsearch {term}\n"
				+ "    - qasearch {term}\n"
				+ "    - reviewpartialsearch {term}\n"
				+ "    - qapartialsearch {term}\n"
				+ "    - exit");
		while(!command.equals("exit"))
		{
			System.out.print("\nEnter your command (Non case-sensitive): ");
			commandLine = sc.nextLine().toLowerCase();
			String commandValues[] = commandLine.trim().split(" ");
			if(commandValues.length!=2) {
				if(commandValues.length==1 && (commandValues[0].equals("help") || commandValues[0].equals("exit"))) {
					command = commandValues[0];
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
				Utils.find(value, reviewIndex, qaIndex);
				break;
			case "reviewsearch":
				Utils.searchByWord(value, reviewIndex);
				break;
			case "qasearch":
				Utils.searchByWord(value, qaIndex);
				break;
			case "reviewpartialsearch":
				Utils.searchByPartialWord(value, reviewIndex);
				break;
			case "qapartialsearch":
				Utils.searchByPartialWord(value, qaIndex);
				break;
			case "help":
				System.out.println("List of command\n"
						+ "    - find {asin}\n"
						+ "    - reviewsearch {term}\n"
						+ "    - qasearch {term}\n"
						+ "    - reviewpartialsearch {term}\n"
						+ "    - qapartialsearch {term}\n"
						+ "    - exit");
				break;
			case "exit":
				break;
			default:
				System.out.println("You may enter wrong command, please try again.\n If you don't remember, type \"help\"");
			}
		}
		sc.close();
		System.out.println("Thank you for using the program!");
	}

	/**
	 * execute the command find asin
	 * 
	 * @param asin the key to find product
	 * @param reviewIndex an InvertedIndex of review file
	 * @param qaIndex an InvertedIndex of Qa file
	 */
	private static void find(String asin, InvertedIndex reviewIndex, InvertedIndex qaIndex) {
		ArrayList<Product> reviews = reviewIndex.getProductByAsin(asin);
		ArrayList<Product> qas = qaIndex.getProductByAsin(asin);
		
		System.out.println("Reviews: ");
		Utils.printProductReviewOrQa(reviews);
		System.out.println("Questions and Answers: ");
		Utils.printProductReviewOrQa(qas);
	}

	/**
	 * execute the command search by word
	 * 
	 * @param term the key to find 
	 * @param Index an InvertedIndex of Review/Qa file
	 */
	private static void searchByWord(String term, InvertedIndex index) {
		ArrayList<Product> products = index.getLineByWordAndSortByFreq(term);
		Utils.printProductReviewOrQa(products);
	}

	/**
	 * execute the command search by partial word
	 * 
	 * @param term the key to find 
	 * @param Index an InvertedIndex of Review/Qa file
	 */
	private static void searchByPartialWord(String term, InvertedIndex index) {
		ArrayList<Product> products = index.getLineByPartialWordAndSortByFreq(term);
		Utils.printProductReviewOrQa(products);
	}

	/**
	 * print out the list of product
	 * 
	 * @param products  a list of product from the find and search command
	 */
	private static void printProductReviewOrQa(ArrayList<Product> products) {
		if(products.size()==0) {
			System.out.println(" Not available");
		}
		else {
			for(Product product : products) {
				if(product instanceof Review) {
					System.out.println(((Review)product).toString());
				} else {
					System.out.println(((Qa)product).toString());
				}
			}
		}
	}
}
