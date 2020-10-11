package beans;

public class Track {
	String title;
	int number;
	
	public Track() {
		title = "";
		number = 0;
	}
	
	public Track(String title, int number) {
		this.title = title;
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
