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
import com.google.gson.GsonBuilder;

public class AmazonSearch {
	public enum TYPE {REVIEW, QUESTION, ANSWER}
	public static void main(String[] args) {
		if(args.length !=4) {
			System.out.println("Usage: java -cp project1.jar cs601.project1.AmazonSearch -reviews <review_file_name> -qa <qa_file_name>");
			System.exit(1);
		}
		
		Path reviewPath = Paths.get(args[1]);
		Path qaPath = Paths.get(args[3]);
	}

}
