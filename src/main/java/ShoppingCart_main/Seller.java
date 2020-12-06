package ShoppingCart_main;

//class for seller objects
public class Seller extends Account
{
    //constructor for seller class
    public Seller(String aUsername, String aPassword)
    {
        super(aUsername, aPassword);
        inventory = new Inventory();
    }
    
    //accessor for inventory
    public Inventory getInventory()
    {
        return this.inventory;
    }
    private Inventory inventory;
}
