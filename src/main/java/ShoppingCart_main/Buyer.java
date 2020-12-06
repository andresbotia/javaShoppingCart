package ShoppingCart_main;

public class Buyer extends Account
{
    //constructor for buyer
    public Buyer(String aUsername, String aPassword) {
        super(aUsername, aPassword);
        cart = new ShoppingCart();
    }
    
    public ShoppingCart getCart()
     {
        return cart;
     }
    private ShoppingCart cart;
    
}
