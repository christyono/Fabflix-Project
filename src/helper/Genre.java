package helper;

public class Genre {
	private int genreID;
	private String name;
	public Genre()
	{
		genreID = 0;
		name = null;
	}
	public Genre(int genreID, String name)
	{
		this.genreID = genreID;
		this.name = name;
	}
	public int getGenreID() {
		return genreID;
	}
	public void setGenreID(int genreID) {
		this.genreID = genreID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
