package helper;

import java.util.ArrayList;

public class ShoppingCart {

	ArrayList<CartItem> ShoppingCart;
	
	public ShoppingCart(){
		ShoppingCart = new ArrayList<CartItem>();
	}
	
	public void addItem(CartItem C){
		//if shopping cart is empty, add the item
		if(ShoppingCart.isEmpty()){
			ShoppingCart.add(C);
			return;
		}
		else{
			for(int i = 0; i < ShoppingCart.size(); i++){
				if(ShoppingCart.get(i).getMovieID() == C.getMovieID()){
					//iterates through the arrayList to see if the movie is already in the cart
					if(C.getQuantity() == 0){
						//if movie is already in the cart and the new quantity = 0, remove the item from cart
						ShoppingCart.remove(i);
						return;
					}
					else{
						//if movie is already in the cart and new quantity > 0, update the quantity of the item
						ShoppingCart.get(i).setQuantity(C.getQuantity());
						return;
					}
				}
			}
			
			//if item isn't in the cart yet and quantity of item != 0, add the item to the cart
			if(C.getQuantity() > 0){
				ShoppingCart.add(C);
				return;
			}
		}	
		
		//default
		return;
	}
	
	public int getTotalPrice(){
		int totalPrice = 0;
		for(int i = 0; i < ShoppingCart.size(); i++){
			totalPrice += ShoppingCart.get(i).getPrice() * ShoppingCart.get(i).getQuantity();
		}
		return totalPrice;
	}
	
	public int getID(int i){
		return ShoppingCart.get(i).getMovieID();
	}
	
	public int getLength(){
		return ShoppingCart.size();
	}
	
	public CartItem getItem(int i ){
		return ShoppingCart.get(i);
	}
}
