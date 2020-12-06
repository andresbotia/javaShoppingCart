package ShoppingCart_main;

import java.util.ArrayList;
import java.util.Iterator;

//product list class
public class ProductList 
{
    public ProductList(){
        productList = new ArrayList<>();
    }
    
    //iterator to cycle through products
    public static Iterator<Product> getAllProducts()
   {
      return new
         Iterator<Product>()
         {
            @Override
            public boolean hasNext()
            {
               return current < productList.size();
            }

            @Override
            public Product next()
            {
               return productList.get(current++);
            }

            @Override
            public void remove()
            {
               throw new UnsupportedOperationException();
            }

            private int current = 0;
         };
   }
    
    //finds product by name 
    public static Product getOneProduct(String name, String soldBy){
        Iterator iter = ProductList.getAllProducts();
         Product product = (Product) iter.next();
         
        while(iter.hasNext()){
         
         if(product.getName().equals(name) && product.getSoldBy().equals(soldBy)){
            break;
        }
         
         product = (Product) iter.next();
         
        }
         return product;
    }
    
    //adds product to list
    public static void addToProductList(Product product)
    {
        productList.add(product);
    }
    
    private static ArrayList<Product> productList;
}
