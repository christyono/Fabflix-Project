package helper;

public class CartItem {
	private int movieID;
	private String Title;
	private int Price;
	private int Quantity;
	
	public CartItem(int movieID, String Title, int Price, int Quantity){
		this.movieID = movieID;
		this.Title = Title;
		this.Price = Price;
		this.Quantity = Quantity;
	}
	
	public int getMovieID(){
		return movieID;
	}
	
	public String getTitle(){
		return Title;
	}
	
	public int getPrice(){
		return Price;
	}
	
	public int getQuantity(){
		return Quantity;
	}
	
	public void setQuantity(int q){
		Quantity = q;
		return;
	}
	
	public void setPrice(int p){
		Price = p;
		return;
	}
}
