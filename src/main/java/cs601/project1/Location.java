package cs601.project1;

public class Location {
	private int line;
	private int count;
	
	public Location(int line, int count) {
		super();
		this.line = line;
		this.count = count;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
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
		if(this.getLine() == l.getLine()) {
			return true;
		}
		return false;
	}
}
