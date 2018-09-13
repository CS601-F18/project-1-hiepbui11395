package cs601.project1;

public class Qa extends Product {
	private String question;
	private String answer;
	
	public Qa(int lineNumber, String asin, String question, String answer) {
		super(lineNumber, asin);
		this.question = question;
		this.answer = answer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String toString() {
		return String.format(" - ASIN: %s\n - Question: %s\n - Answer: %s \n\n", this.getAsin(), this.question, this.answer);
	}
}
