package ShoppingCart_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import ShoppingCart_main.Seller;
import ShoppingCart_main.ShoppingCartSystem;

/**
 * GUI class for the sellers financial summary page.
 * @author ASUS
 */

public class FinancialSummaryPage 
{
    
    /**
     * This method generates and displays the sellers financial summary based on inventory product values.
     * @param aSeller The active seller.
     * @throws IOException if decoration image file cannot be found.
     */
    
    public void display(Seller aSeller) throws IOException{
        
        this.seller = aSeller;
        
        JFrame frame = new JFrame("Product Description:");
        frame.setSize(1000,700); 
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        
        String name = seller.getUsername().substring(0, 1).toUpperCase() + seller.getUsername().substring(1);
        JLabel pageTitle = new JLabel(name + "'s Financial Statistics");
        pageTitle.setFont(pageTitle.getFont().deriveFont(40.0f));
        
        JButton logout = new JButton("Logout");
        logout.setPreferredSize(new Dimension(100,30));
        logout.setFont(logout.getFont().deriveFont(12.0f));
        JButton back = new JButton("Back to Homepage");
        logout.setPreferredSize(new Dimension(150,75));
        back.setPreferredSize(new Dimension(150,75));
        back.setFont(back.getFont().deriveFont(12.0f));
        
        topPanel.add(pageTitle);
        topPanel.add(Box.createRigidArea(new Dimension(150,0)));
        topPanel.add(back);
        topPanel.add(logout);
        topPanel.setBackground(new Color(43, 134, 179)); //set color
        
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(new Color(43, 134, 179)); //set color
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(43, 134, 179)); //set color
        
        JTextArea financialSummary = new JTextArea(seller.getInventory().getFinancialSummary());
        financialSummary.setFont(financialSummary.getFont().deriveFont(30.0f));
        financialSummary.setPreferredSize(new Dimension(400,150));
        financialSummary.setBackground(new Color(43, 134, 179));
        financialSummary.setEditable(false);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0,50)));
        mainPanel.add(financialSummary);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
        
        
        try{
        BufferedImage img = ImageIO.read(new File("C:\\Users\\ASUS\\Desktop\\School\\Object Oriented Programming\\Shopping Cart\\ShoppingCartApplication_main\\ShoppingCartApplication_Main\\src\\shoppingcartapplication_main\\pics\\pics\\dollarsign.png"));
        Image tmp = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        
        JLabel picLabel = new JLabel(new ImageIcon(dimg));
        JPanel picPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        picPanel.setBackground(new Color(43, 134, 179)); //set color
        picPanel.add(picLabel);
        mainPanel.add(picPanel);
        }
        catch(IOException EX){
           System.out.println("File not found :(");
        }
        
        centerPanel.add(mainPanel);
        
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.add(topPanel, BorderLayout.NORTH);
       frame.add(centerPanel, BorderLayout.CENTER);
       frame.setLocationRelativeTo(null);  
       frame.setVisible(true);
        
        
       
    logout.addActionListener(new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
          frame.setVisible(false); 
          ShoppingCartSystem.loginPage.display();
        }
    } );
   
    back.addActionListener(new ActionListener() //listener for homepage button
            {

                @Override
                public void actionPerformed(ActionEvent ae)
                {
                    frame.setVisible(false);
                    ShoppingCartSystem.sellerPage.display();
                }
            });
    
    
   } 
    
    private Seller seller;
}