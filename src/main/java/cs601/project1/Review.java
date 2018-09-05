package cs601.project1;

public class Review extends Product{
	private String reviewText;

	public Review(int lineNumber, String asin, String reviewText) {
		super(lineNumber, asin);
		this.reviewText = reviewText;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	
	public String toString() {
		return this.reviewText;
	}
}
