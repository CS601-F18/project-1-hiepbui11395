package cs601.project1;

public class Qa {
	private String asin;
	private String question;
	private String answer;
	
	public Qa(String asin, String question, String answer) {
		super();
		this.asin = asin;
		this.question = question;
		this.answer = answer;
	}
	
	public String getAsin() {
		return asin;
	}
	
	public void setAsin(String asin) {
		this.asin = asin;
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
	
	
}
