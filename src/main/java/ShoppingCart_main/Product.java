package ShoppingCart_main;

public class Product 
{
    //constructor for products, sets the name,price,etc
    public Product(String name, double price, String description, int quantity, double cost, String soldBy, int totalNumberSold)
    {
        this.name = name;
        this.price = price;
        this.description = description;
        this.inventoryQuantity = quantity;
        this.cost = cost;
        this.soldBy = soldBy;
        this.cartQuantity = 0;
        this.totalNumberSold = totalNumberSold;
    }
    //accessor for name
    public String getName()
    {
        return this.name;
    }
    //accessor for description
    public String getDescription()
    {
        return this.description;
    }
    //accessor for price
    public double getPrice()
    {
        return this.price;
    }
    //inventory quantity accessor
    public int getinventoryQuantity()
    {
        return this.inventoryQuantity;
    }
    //accessor for inventory quantity
    public int getCartQuantity()
    {
        return this.cartQuantity;
    }
    //accessor for # sold
    public int getTotalNumberSold()
    {
        return this.totalNumberSold;
    }
    //cost accessor
    public double getCost()
    {
        return this.cost;
    }
    //sold by accessor
    public String getSoldBy()
    {
        return this.soldBy;
    }
    //product name mutator
    public void setName(String aName)
    {
        this.name = aName;
    }
    //mutator for description
    public void setDescription(String aDescription)
    {
        this.description = aDescription;
    }
    //mutator for price
    public void setPrice(double aPrice)
    {
        this.price = aPrice;
    }
    //mutator for sellers inventory quantity
    public void setInventoryQuantity (int aQuantity)
    {
        this.inventoryQuantity = aQuantity;
        if(this.inventoryQuantity < 0)
        {
            this.inventoryQuantity = 0 - this.inventoryQuantity;
        }
    }
    //shopping cart quantity mutator
    public void setCartQuantity (int aQuantity)
    {
        this.cartQuantity = aQuantity;
    }
    //mutator for shopping cart cost
    public void setCost (double aCost)
    {
        this.cost = aCost;
    }
    
    //mutator for # sold
    public void setTotalNumberSold(int s)
    {
        this.totalNumberSold = s;
    }
    
   
    
    private String name;
    private String description;
    private double price;
    private int inventoryQuantity;
    private int cartQuantity;
    private int totalNumberSold;
    private double cost;
    private String soldBy;
}
