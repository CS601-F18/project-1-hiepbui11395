package cs601.project1;

import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
	public static void executeCommand(InvertedIndex reviewIndex, InvertedIndex qaIndex) {
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
		Utils.printProductReviewOrQa(reviews);
		System.out.println("Questions and Answers: ");
		Utils.printProductReviewOrQa(qas);
	}

	private static void searchByWord(String term, InvertedIndex index) {
		ArrayList<Product> products = index.getLineByWordAndSortByFreq(term);
		Utils.printProductReviewOrQa(products);
	}

	private static void searchByPartialWord(String term, InvertedIndex index) {
		ArrayList<Product> products = index.getLineByPartialWordAndSortByFreq(term);
		Utils.printProductReviewOrQa(products);
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
