package helper;
import java.sql.Date;
import java.util.ArrayList;

public class Movie {
	private String title;
	private int year;
	private int movieID;
	private String director;
	private String bannerURL;
	private String trailerURL;
	private int price;
	private ArrayList<Star> listOfStars = new ArrayList<Star>();
	private ArrayList<Genre> listOfGenres = new ArrayList<Genre>();
	public Movie()
	{
		movieID = 0;
		title = null;
		year = 0;
		director = null;
		bannerURL = null;
		trailerURL = null;
		price = 0;
		
	}
	public Movie(int movieID, String title, int year, String director, String bannerURL, String trailerURL)
	{
		this.movieID = movieID;
		this.title = title;
		this.year = year;
		this.director = director;
		this.bannerURL = bannerURL;
		this.trailerURL = trailerURL;
		this.price = 2;
	}
	public int getID()
	{
		return movieID;
	}
	public void setID(int movieID)
	{
		this.movieID = movieID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public int getMovieID() {
		return movieID;
	}
	public void setMovieID(int movieID) {
		this.movieID = movieID;
	}
	public String getBannerURL() {
		return bannerURL;
	}
	public void setBannerURL(String bannerURL) {
		this.bannerURL = bannerURL;
	}
	public String getTrailerURL() {
		return trailerURL;
	}
	public void setTrailerURL(String trailerURL) {
		this.trailerURL = trailerURL;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public void addStar(int starID, String firstName, String lastName, Date dateOfBirth, String photoURL)
	{
		Star movieStar = new Star(starID, firstName, lastName, dateOfBirth, photoURL);
		listOfStars.add(movieStar);
	}
	public void addGenre(int genreID, String name)
	{
		Genre genre = new Genre(genreID, name);
		listOfGenres.add(genre);
	}
	public ArrayList<Star> getStarList()
	{
		return listOfStars;
	}
	public ArrayList<Genre> getGenreList()
	{
		return listOfGenres;
	}
}
