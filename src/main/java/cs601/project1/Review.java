package cs601.project1;

public class Review extends Product{
	private String reviewText;

	public Review(int lineNumber, String reviewText) {
		super(lineNumber);
		this.reviewText = reviewText;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	
	public String toString() {
		return this.lineNumber + " : " + this.reviewText;
	}
}
