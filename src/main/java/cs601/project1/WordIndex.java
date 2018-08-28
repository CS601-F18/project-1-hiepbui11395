package cs601.project1;

import cs601.project1.AmazonSearch.TYPE;

public class WordIndex {
	private TYPE type;
	private int numberOfAttend;
	
	public WordIndex(TYPE review, int numberOfAttend) {
		super();
		this.type = review;
		this.numberOfAttend = numberOfAttend;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public int getNumberOfAttend() {
		return numberOfAttend;
	}

	public void setNumberOfAttend(int numberOfAttend) {
		this.numberOfAttend = numberOfAttend;
	}
}
