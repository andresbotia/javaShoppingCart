/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ShoppingCart_main;

import ShoppingCart_GUI.LoginPage;
import ShoppingCart_GUI.SellerPage;
import ShoppingCart_GUI.InvoicePage;
import ShoppingCart_GUI.DescriptionPage;
import ShoppingCart_GUI.FinancialSummaryPage;
import ShoppingCart_GUI.CheckOutPage;
import ShoppingCart_GUI.CartPage;
import ShoppingCart_GUI.BuyerPage;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


//class for the whole shopping cart system
public class ShoppingCartSystem 
{
    //main for program
     public static void main(String[] args)
     {
         buyerList = new ArrayList();
         sellerList = new ArrayList();
         
         ProductList p = new ProductList();
         
         initializeViewPages();
         
         initializeBuyers();
         
         initializeSellers();
         
         updateProductList(sellerList);
         
         Iterator<Product> iter = ProductList.getAllProducts();
         while(iter.hasNext())
         {
             Product tempProduct = (Product) iter.next();
             System.out.println(tempProduct.getName());
         }
         
         loginPage.display();
         
     }
     
     //reads and creates inventory from txt files
     public static void readFromFile(Seller seller, String fileName)
    {
        try
        {
        
        List<String> lines = Files.readAllLines(Paths.get(fileName)); 
        
        ArrayList<String> tokens = new ArrayList();
        for (int i = 0; i < lines.size(); i++)  
        {
            StringTokenizer st = new StringTokenizer(lines.get(i), "\t"); 
            while(st.hasMoreTokens())
            {
                tokens.add(st.nextToken());  
            }   

            Product tempProduct = new Product(tokens.get(0), Double.parseDouble(tokens.get(1)), tokens.get(2), Integer.parseInt(tokens.get(3)),
                    Double.parseDouble(tokens.get(4)), tokens.get(5), Integer.parseInt(tokens.get(6)));
            seller.getInventory().addToInventory(tempProduct);
            tokens.clear();
        }
        
        }
        catch(Exception e){}
       
    }
     
     //updates product list
     public static void updateProductList(ArrayList<Seller> sellerList)
     {
         for (int i = 0; i < sellerList.size(); i++)
         {
             Iterator<Product> iter = sellerList.get(i).getInventory().getAllProducts();
             while(iter.hasNext())
             {
                 Product tempProduct = iter.next();
                 ProductList.addToProductList(tempProduct);
             }
         }
     }
     
     //user login
     public static boolean authenticate(String username, String password, boolean isBuyer)
     {
         if (isBuyer)
         {
             Buyer tempBuyer = findBuyer(username);
             if(tempBuyer == null)
             {
                 return false;
             }
             boolean isUser = tempBuyer.isPasswordMatch(password);
             if(isUser)
             {
                 activeBuyer = tempBuyer;
                 return isUser;
             }
             else
             {
                 
                 return isUser; //false
             }
             
         }
         else
         {
             Seller tempSeller = findSeller(username);
             if(tempSeller == null)
             {
                 return false;
             }
             boolean isUser = tempSeller.isPasswordMatch(password);
             if(isUser)
             {
                 activeSeller = tempSeller;
                 return isUser;
             }
             else
             {
                 return isUser; //false
             }
             
         }
     }
     
     //find seller based on username
     public static Seller findSeller(String username)
     {
        for(int i = 0; i < sellerList.size(); i++)
        {
            if (sellerList.get(i).getUsername().equals(username))
            {
                return sellerList.get(i);
            }
            else
            {
                //Throw error
            }
        }
         return null;
     }
     
     //returns buyer based on username
     public static Buyer findBuyer (String username)
     {
         for(int i = 0; i < buyerList.size(); i++)
         {
              if (buyerList.get(i).getUsername().equals(username))
            {
                return buyerList.get(i);
            }
            else
            {
                //Throw error
            }
         }
         return null;
     }
     
     //active buyer
     public static Buyer getActiveBuyer()
     {
         return activeBuyer;
     }
    
     //clears buyer
     public static void clearActiveBuyer(){
         activeBuyer = null;
     }
     
     //active seller
     public static Seller getActiveSeller()
     {
         return activeSeller;
     }
     
     //clears active seller
      public static void clearActiveSeller(){
         activeSeller = null;
     }
      
     //initializing GUI 
     private static void initializeViewPages()
     {
         buyerPage = new BuyerPage();
         sellerPage = new SellerPage();
         loginPage = new LoginPage();
         invoicepage = new InvoicePage();
         cartpage = new CartPage();
         checkOutPage = new CheckOutPage();
         finanSummaryPage = new FinancialSummaryPage();
         descriptionPage = new DescriptionPage();
         
     }
     
     //buyers info
     private static void initializeBuyers()
     {
        ///buyers
         Buyer userTest = new Buyer("userTest", "123");
         Buyer Michael = new Buyer("michael Scott", "dundermifflin");
         buyerList.add(userTest);
         buyerList.add(Michael);
         
     }
     
     //seller info
     private static void initializeSellers()
     {
         //sellers
         Seller daniel = new Seller("daniel", "123");
         Seller andres = new Seller("andres", "321");
         Seller rishi = new Seller("rishi", "786");
         
         
         //Add to sellerList
         sellerList.add(daniel);
         sellerList.add(andres);
         sellerList.add(rishi);
         
         
         //Reading from files
         readFromFile(daniel, "danielInventory.txt");
         readFromFile(andres, "andresInventory.txt");
         readFromFile(rishi, "rishiInventory.txt");
         
     }
     
     //makes purchases when checkout is confirmed
     public static void makePurchases(ShoppingCart acart)
     {
         ShoppingCart cart = acart;
         Iterator iter = cart.getAllProducts();
          Product tempProduct;
         while(iter.hasNext())
         {
            tempProduct = (Product) iter.next();
             Seller s = findSeller(tempProduct.getSoldBy());
             Iterator iter2 = s.getInventory().getAllProducts();
             
             while(iter2.hasNext())
             {
                
                Product temp = (Product) iter2.next();
                if(temp.getName().equals(tempProduct.getName()))
                {
                    temp.setInventoryQuantity(temp.getinventoryQuantity() - tempProduct.getCartQuantity());
                    temp.setTotalNumberSold(temp.getTotalNumberSold() + tempProduct.getCartQuantity());
                    s.getInventory().overWriteInventoryFile(s);
                    updateInventory(s);
                    updateProductList(sellerList);
                }
             }
         }
     }
     
     //updates sellers inventory files
     public static void updateInventory(Seller s)
     {
         s.getInventory().clearInventory();
         readFromFile(s, s.getUsername() +"Inventory.txt");
         
     }
     
     public static ArrayList<Seller> getSellerList()
     {
         return sellerList;
     }
     
     static ArrayList getBuyerList()
    {
        return buyerList;
    }
     
     private static ArrayList<Buyer> buyerList;
     private static ArrayList<Seller> sellerList;
     private static Buyer activeBuyer;
     private static Seller activeSeller;
     
     public static BuyerPage buyerPage;
     public static SellerPage sellerPage;
     public static LoginPage loginPage;
     public static InvoicePage invoicepage;
     public static CartPage cartpage;
     public static CheckOutPage checkOutPage;
     public static DescriptionPage descriptionPage;
     public static FinancialSummaryPage finanSummaryPage;
    
}
