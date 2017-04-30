package helper;
import java.sql.Date;
import java.util.ArrayList;

public class Star {
	private int starID;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String photoURL;
	private ArrayList<Movie> starredMovies = new ArrayList<Movie>();
	public Star()
	{
		starID = 0;
		firstName = null;
		lastName = null;
		dateOfBirth = null;
		photoURL = null;
		
		
	}
	public Star(int starID, String firstName, String lastName, Date dateOfBirth2, String photoURL)
	{
		this.starID = starID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth2;
		this.photoURL = photoURL;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
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
	public void addStarredMovie(int movieID, String title, int year, String director, String bannerURL, String trailerURL)
	{
		Movie movie = new Movie(movieID, title, year, director, bannerURL, trailerURL);
		starredMovies.add(movie);
		
	}
	public ArrayList<Movie> getStarredMovieList()
	{
		return starredMovies;
	}
	
}
