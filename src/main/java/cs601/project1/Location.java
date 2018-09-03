package cs601.project1;

public class Location {
	private int lineNumber;
	private int count;
	
	public Location(int lineNumber, int count) {
		super();
		this.lineNumber = lineNumber;
		this.count = count;
	}
	
	public Location(int lineNumber) {
		super();
		this.lineNumber = lineNumber;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Location)) {
			return false;
		}
		Location l = (Location)o;
		if(this.getLineNumber() == l.getLineNumber()) {
			return true;
		}
		return false;
	}
}
