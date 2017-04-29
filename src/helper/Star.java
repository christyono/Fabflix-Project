package helper;

public class Star {
	private int starID;
	private String firstName;
	private String lastName;
	private int dateOfBirth;
	private String photoURL;
	public Star()
	{
		starID = 0;
		firstName = null;
		lastName = null;
		dateOfBirth = 0;
		photoURL = null;
		
	}
	public Star(int starID, String firstName, String lastName, int dateOfBirth, String photoURL)
	{
		this.starID = starID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.photoURL = photoURL;
	}
	public int getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(int dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPhotoURL() {
		return photoURL;
	}
	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}
	public int getStarID() {
		return starID;
	}
	public void setStarID(int starID) {
		this.starID = starID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
