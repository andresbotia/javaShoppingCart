package ShoppingCart_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import ShoppingCart_main.Product;
import ShoppingCart_main.ShoppingCartSystem;


// GUI for description page
public class DescriptionPage 
{
    //displays description for a specific item
    public void display(Product aProduct) throws IOException{
        
        this.product = aProduct;
        
        JFrame frame = new JFrame("Product Description:");
        frame.setSize(1000,700); 
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        
        JLabel productTitle = new JLabel("Product:   " + product.getName());
        productTitle.setFont(productTitle.getFont().deriveFont(40.0f));
        
        JButton logout = new JButton("Logout");
        logout.setPreferredSize(new Dimension(100,30));
        logout.setFont(logout.getFont().deriveFont(12.0f));
        JButton back = new JButton("Back to Homepage");
        back.setPreferredSize(new Dimension(150,30));
        back.setFont(back.getFont().deriveFont(12.0f));
        
        topPanel.add(productTitle);
        topPanel.add(Box.createRigidArea(new Dimension(350,0)));
        topPanel.add(back);
        topPanel.add(logout);
        topPanel.setBackground(new Color(43, 134, 179)); //set color
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        mainPanel.setBackground(new Color(43, 134, 179)); //set color
        
        JPanel leftMainPanel = new JPanel();
        leftMainPanel.setLayout(new BoxLayout(leftMainPanel, BoxLayout.Y_AXIS));
        
        try{
        BufferedImage img = ImageIO.read(new File("C:\\Users\\ASUS\\Desktop\\School\\Object Oriented Programming\\Shopping Cart\\ShoppingCartApplication_main\\ShoppingCartApplication_Main\\src\\shoppingcartapplication_main\\pics\\" + product.getName()+ ".jpg"));
        Image tmp = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        
        JLabel picLabel = new JLabel(new ImageIcon(dimg));
        leftMainPanel.add(picLabel);
        }
        catch(IOException EX){
           System.out.println("File not found :(");
        }
        
        JLabel price = new JLabel("Price:   " + product.getPrice());
        price.setFont(price.getFont().deriveFont(25.0f));
        
        JLabel stock = new JLabel("In Stock:   " + product.getinventoryQuantity());
        stock.setFont(stock.getFont().deriveFont(25.0f));
        
        String name = product.getSoldBy().substring(0, 1).toUpperCase() + product.getSoldBy().substring(1);
        JLabel seller = new JLabel("Seller: " +name );
        seller.setFont(seller.getFont().deriveFont(25.0f));
        
        leftMainPanel.add(Box.createRigidArea(new Dimension(0,50)));
        leftMainPanel.add(price);
        leftMainPanel.add(Box.createRigidArea(new Dimension(0,20)));
        leftMainPanel.add(stock);
        leftMainPanel.add(Box.createRigidArea(new Dimension(0,20)));
        leftMainPanel.add(seller);
        leftMainPanel.setBackground(new Color(43, 134, 179)); //set color
        
        JPanel rightMainPanel = new JPanel();
        rightMainPanel.setLayout(new BoxLayout(rightMainPanel, BoxLayout.Y_AXIS));
        
        description = new JTextArea(product.getDescription());
        description.setFont(description.getFont().deriveFont(16.0f));
        description.setPreferredSize(new Dimension(450,400));
        description.setBackground(new Color(43, 134, 179));
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        
        if(ShoppingCartSystem.getActiveBuyer() == null)
        {
            description.setEditable(true);
        }
        else
        {
            description.setEditable(false);
        }
        
        JLabel title = new JLabel("Description: ");
        title.setFont(title.getFont().deriveFont(24.0f));
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(title);
        titlePanel.setBackground(new Color(43, 134, 179)); //set color

        
        rightMainPanel.add(titlePanel);
        rightMainPanel.add(Box.createRigidArea(new Dimension(0,20)));
        rightMainPanel.add(description);
        rightMainPanel.setBackground(new Color(43, 134, 179)); //set color
        
        if(ShoppingCartSystem.getActiveBuyer() == null)
        {
            JPanel updateButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            updateButtonPanel.setBackground(new Color(43, 134, 179));
            JButton update = new JButton("Update Description");
            updateButtonPanel.add(update);
            rightMainPanel.add(updateButtonPanel);
            update.addActionListener(new ActionListener()
             {
                @Override
                public void actionPerformed(ActionEvent ae)
                {
                  String temp = product.getDescription();
                  if(!temp.equals(description.getText()))
                  {
                    product.setDescription(description.getText());
                    ShoppingCartSystem.getActiveSeller().getInventory().overWriteInventoryFile(ShoppingCartSystem.getActiveSeller());
                    description.repaint();
                    JOptionPane.showMessageDialog(frame, "The Description has been updated");
                  }

                }
             } );
            
            back.addActionListener(new ActionListener() //listener for main page button
            {

                @Override
                public void actionPerformed(ActionEvent ae)
                {
                    frame.setVisible(false);
                    ShoppingCartSystem.sellerPage.display();
                }
            });
        }
        else
        {
            back.addActionListener(new ActionListener() //listener for main page button
            {

                @Override
                public void actionPerformed(ActionEvent ae)
                {
                    frame.setVisible(false);
                    ShoppingCartSystem.buyerPage.display();
                }
            });
        }
      
        
        mainPanel.add(leftMainPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(80,0)));
        mainPanel.add(rightMainPanel);
        

       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.add(topPanel, BorderLayout.NORTH);
       frame.add(mainPanel, BorderLayout.CENTER);
       frame.setLocationRelativeTo(null);  //set position
       frame.setVisible(true);
       
    logout.addActionListener(new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
          frame.setVisible(false);
          if(ShoppingCartSystem.getActiveBuyer() != null)
          {
            ShoppingCartSystem.getActiveBuyer().getCart().clearCart();
            ShoppingCartSystem.clearActiveBuyer();
            ShoppingCartSystem.loginPage.display();
          }
          else
          {
              ShoppingCartSystem.clearActiveSeller();
              ShoppingCartSystem.loginPage.display();
          }
        }
    } );
   
   } 
    
    private Product product;
    private JTextArea description;
    
}
