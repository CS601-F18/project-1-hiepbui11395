package cs601.project1;

public abstract class Product {
	protected int lineNumber;
	protected String asin;

	public Product(int lineNumber, String asin) {
		super();
		this.lineNumber = lineNumber;
		this.asin = asin;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	
	public String getAsin() {
		return asin;
	}
	

	public void setAsin(String asin) {
		this.asin = asin;
	}
}
