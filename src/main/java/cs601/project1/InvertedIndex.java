package cs601.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class InvertedIndex {
	private HashMap<String, ListLocation> index;


	/**
	 * Add word from file to the InvertedIndex
	 * 
	 * @param br
	 * @throws IOException
	 */
	public void addToIndex(BufferedReader br) throws IOException {
		
		//Init Gson instance
		GsonBuilder gb = new GsonBuilder();
		gb.disableHtmlEscaping();
		Gson gson = gb.create();
		
		String line = "";
		int count =0;
		while((line = br.readLine()) != null) {
			
		}
	}

	public List<Integer> getLineByWordAndSortByFreq(String word){

		return null;
	}
	
	public void updateListLocation(Location location) {
		
	}
}
