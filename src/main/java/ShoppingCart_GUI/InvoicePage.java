package ShoppingCart_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;
import ShoppingCart_main.ShoppingCart;
import ShoppingCart_main.ShoppingCartSystem;

//GUI for invoice page

public class InvoicePage 
{
   
         // displays the generated summary in the shopping cart page
        public void display(ShoppingCart aCart)
        {
        
            cart = aCart;
            
        JFrame frame = new JFrame("Invoice");
        frame.setSize(1000,700);
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        String name = ShoppingCartSystem.getActiveBuyer().getUsername();
        JLabel productTitle = new JLabel("\t\tThank you for shopping!");
        productTitle.setFont(productTitle.getFont().deriveFont(40.0f));
        
        JButton logout = new JButton("Logout");
        logout.setPreferredSize(new Dimension(150,75));
        logout.setFont(logout.getFont().deriveFont(12.0f));
        JButton back = new JButton("Back to Homepage");
        back.setPreferredSize(new Dimension(150,75));
        back.setFont(back.getFont().deriveFont(12.0f));
        
        topPanel.add(productTitle);
        topPanel.add(Box.createRigidArea(new Dimension(200,0)));
        topPanel.add(back);
        topPanel.add(logout);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        JScrollPane scroll = new JScrollPane();
        
        
        
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new FlowLayout());
        
        double total = ShoppingCartSystem.getActiveBuyer().getCart().getTotalPrice();
        String totalText = new DecimalFormat("#.##").format(total);
        JLabel totalTextLabel = new JLabel();
        String line = "-------------------------------------------";
        JTextArea invoice = new JTextArea(cart.generateSummary() + "\n"+line +"\nTotal: $ " + totalText );
        invoice.setFont(invoice.getFont().deriveFont(20.0f));
        scroll.add(invoice);
        
        mainPanel.add(invoice);
        mainPanel.add(totalPanel);
                

       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.add(topPanel, BorderLayout.NORTH);
       frame.add(mainPanel,BorderLayout.CENTER);
       frame.setLocationRelativeTo(null);
       
       frame.setVisible(true);
       ShoppingCartSystem.getActiveBuyer().getCart().clearCart();
       ShoppingCartSystem.updateProductList(ShoppingCartSystem.getSellerList());
   
       back.addActionListener(new ActionListener() 
       {

            @Override
            public void actionPerformed(ActionEvent ae)
            {
                frame.setVisible(false);
                ShoppingCartSystem.getActiveBuyer().getCart().clearCart();
                ShoppingCartSystem.updateProductList(ShoppingCartSystem.getSellerList());
               ShoppingCartSystem.buyerPage.repaintTable();
            }
        });
       
       logout.addActionListener(new ActionListener() 
       {
           
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                ShoppingCartSystem.getActiveBuyer().getCart().clearCart();
                frame.setVisible(false);
                ShoppingCartSystem.loginPage.display();
            }
        });
       
   } 
        
      
         private ShoppingCart cart;
}