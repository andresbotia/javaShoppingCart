package ShoppingCart_main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

//class for sellers inventory

public class Inventory
{
    //constructor for inventory class
    public Inventory()
    {
        inventory = new ArrayList<>();
    }
       
    //iterator for products
    public Iterator<Product> getAllProducts()
   {
      return new
         Iterator<Product>()
         {
            @Override
            public boolean hasNext()
            {
               return current < inventory.size();
            }

            @Override
            public Product next()
            {
               return inventory.get(current++);
            }

            @Override
            public void remove()
            {
               throw new UnsupportedOperationException();
            }

            private int current = 0;
         };
   }
   //finds the next product on the list
    public Product getProduct(String aName)
    {
        Iterator iter = this.getAllProducts();
        Product product = (Product) iter.next();
         
        while(iter.hasNext()){
         
         if(product.getName().equals(aName)){
            break;
         }
         product = (Product) iter.next();
        }
         return product;
    }
    //adds product to the array
    public void addToInventory(Product aProduct)
    {
        inventory.add(aProduct);
    }
    
   
   //removes product 
    public void removeProduct(Product aProduct)
    {
        inventory.remove(aProduct);
    }
    
    //overwrites inventory file
    public void overWriteInventoryFile(Seller s)
    {
        String result = "";
        try
        {
        File fout = new File(s.getUsername() + "Inventory.txt");    //creates file
        FileOutputStream fos = new FileOutputStream(fout);  //creates output steam
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));    //creates writer
        
        //loop for inventory
        for(int i=0; i < inventory.size(); i++)
        {
            result += inventory.get(i).getName() + "\t";
            result += inventory.get(i).getPrice() + "\t";
            result += inventory.get(i).getDescription() + "\t";
            result += inventory.get(i).getinventoryQuantity() + "\t";
            result += inventory.get(i).getCost() + "\t";
            result += inventory.get(i).getSoldBy() + "\t";
            result += inventory.get(i).getTotalNumberSold();
            writer.write(result);   
            writer.newLine();   
            result = "";  
        }
        writer.close();
        }
        catch(Exception e){}
    }
    
    ////checks if there is enough stock to checkout the product
    public boolean checkStock(Product product)
    {
       Product tempInventoryProduct = getProduct(product.getName());
        return tempInventoryProduct.getinventoryQuantity() >= product.getCartQuantity();
    }
   
    //creates sellers financial summary
    public String getFinancialSummary()
    {
        String result;
        
        double totalProfit = 0;
        double totalCost = 0;
        double totalRevenue = 0;
        int totalProductsSold = 0;
        
        Iterator iter = this.getAllProducts();
        while(iter.hasNext())
        {
            Product p = (Product) iter.next();
            totalRevenue += (p.getPrice() * p.getTotalNumberSold());
            totalCost += p.getCost()*(p.getTotalNumberSold()+p.getinventoryQuantity());
            totalProductsSold += p.getTotalNumberSold();
        }
        
        totalProfit = totalRevenue - totalCost;
        if(totalProfit < 0){
            totalProfit = 0;
        }
        
        String totalProfitText = new DecimalFormat("#.##").format(totalProfit);
        String totalRevenueText = new DecimalFormat("#.##").format(totalRevenue);
        result = "Total Products Sold: " + totalProductsSold + "\nTotal Profit: $" + totalProfitText + "\nRevenue: $" + totalRevenueText; 
        return result;
    }
    
    //inventory size accessor
    public int getSize()
    {
        return inventory.size();
    }
    
    //clears inventory
    public void clearInventory()
    {
        inventory.clear();
    }
 
    private ArrayList<Product> inventory;
}
