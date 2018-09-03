package cs601.project1;

public abstract class Product {
	protected int lineNumber;

	public Product(int lineNumber) {
		super();
		this.lineNumber = lineNumber;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
}
