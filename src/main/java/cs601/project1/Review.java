package cs601.project1;

public class Review {
	private String asin;
	private String reviewText;
	
	public Review(String asin, String reviewText) {
		super();
		this.asin = asin;
		this.reviewText = reviewText;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	
	public String toString() {
		return this.asin + " : " + this.reviewText;
	}
}
