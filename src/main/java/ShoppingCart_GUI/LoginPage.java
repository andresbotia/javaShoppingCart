package ShoppingCart_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ShoppingCart_main.ShoppingCartSystem;

//GUI for login page

public class LoginPage 
{

    
    //displays login page
    public void display(){
        
    JFrame frame = new JFrame("Welcome to the Online Grocery Store:");
    
    
    JPanel logoPanel = new JPanel();
    JLabel logo = new JLabel("Welcome to the Online Grocery Store");
    logo.setFont(logo.getFont().deriveFont(24.0f));
    logoPanel.add(logo);
    
    JPanel loginPanel = new JPanel();
    loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
    
    JPanel userNamePanel = new JPanel();
    userNamePanel.setLayout(new FlowLayout());
    
    userNamePanel.add(new JLabel("Username: "));
    JTextField username = new JTextField();
    username.setPreferredSize( new Dimension( 100,20) );
    userNamePanel.add(username);
    
    JPanel passwordPanel = new JPanel();
    
    passwordPanel.setLayout(new FlowLayout());
    
    passwordPanel.add(new JLabel("Password: "));
    JPasswordField password = new JPasswordField();
    password.setPreferredSize( new Dimension( 100,20) );
    passwordPanel.add(password);
    
    JPanel textFieldsPanel = new JPanel();
    textFieldsPanel.setLayout(new BoxLayout(textFieldsPanel, BoxLayout.Y_AXIS));
    textFieldsPanel.add(userNamePanel);
    textFieldsPanel.add(passwordPanel);
    
   
    
    String[] menuOptions = {"Buyer", "Seller"};
    JComboBox dropMenu = new JComboBox(menuOptions);

    JButton login = new JButton("Login");
    
    
    JPanel inputPanel = new JPanel();
    
    inputPanel.add(dropMenu);
    inputPanel.add(login);
    
    
    loginPanel.add(Box.createRigidArea(new Dimension(0,25)));
    loginPanel.add(textFieldsPanel);
    loginPanel.add(inputPanel);
    
   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   frame.add(logoPanel, BorderLayout.NORTH);
   frame.add(loginPanel, BorderLayout.CENTER);
   frame.getContentPane().setBackground(Color.red);
   frame.setSize(500,300);
   frame.setVisible(true);
   frame.setLocationRelativeTo(null);  //set position
   
   
   login.addActionListener(new ActionListener() //controller
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
           String user = username.getText();
           String pass = password.getText();
           boolean isBuyer = false;
           boolean isSeller = false;
           if(dropMenu.getSelectedItem().equals("Buyer"))
           {
              isBuyer = true;
           }
           else
           {
               isSeller = true;
           }
           
            boolean isUser = ShoppingCartSystem.authenticate(user, pass, isBuyer);
                //if buyer is authenticated
                if(isUser)  //If authenticate works
                {
                    if(isBuyer) //if the user is a buyer
                    {
                        frame.setVisible(false);
                        ShoppingCartSystem.buyerPage.display();
                        
                    }
                    else if(isSeller)   //if the user is a seller
                    {
                        frame.setVisible(false);
                        ShoppingCartSystem.sellerPage.display();
                        
                    }
                    else    
                    {
                        JOptionPane.showMessageDialog(frame, "Wrong Username or Password.");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(frame, "Wrong Username or Password.");
                }
        }
    } );
   
    }
    
}