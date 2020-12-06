package ShoppingCart_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import ShoppingCart_main.Product;
import ShoppingCart_main.ProductList;
import ShoppingCart_main.ShoppingCartSystem;


//GUI for buyer page
public class BuyerPage 
{
    JTable table;
    DefaultTableModel dm;
    DefaultTableCellRenderer centerRenderer;
    
    
    public BuyerPage(){
       viewedShoppingCart = false; 
    }
    
    
    //displays buyer page when user is type buyer
    
    public void display()
    {
        //jpanels
        JPanel mainPanel = new JPanel();        //panel for table
        JPanel northPanel = new JPanel();       //panel for  buttonPanel
        JPanel southPanel = new JPanel();       //panel below the main page
        JPanel buttonPanel = new JPanel();      //panel for financial summary
        JPanel scrollBarPanel = new JPanel();   //panel for scroll bar
        JPanel removeButtonPanel = new JPanel();//panel for displaying remove buttons
        JPanel logoPanel = new JPanel();
        
        //scroll bar
        JScrollBar verticalBar = new JScrollBar(JScrollBar.VERTICAL, 30, 40, 0, 300);

        //layout for panels
        mainPanel.setLayout(new BorderLayout());
        northPanel.setLayout(new FlowLayout());
        southPanel.setLayout(new FlowLayout());
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        scrollBarPanel.setLayout(new BorderLayout());
        removeButtonPanel.setLayout(new BorderLayout());
        logoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        //setting colors
        mainPanel.setBackground(new Color(43, 134, 179)); //set color
        northPanel.setBackground(new Color(43, 134, 179)); //set color
        southPanel.setBackground(new Color(43, 134, 179)); //set color
        buttonPanel.setBackground(new Color(43, 134, 179)); //set color
        northPanel.setBackground(new Color(43, 134, 179)); //set color
        logoPanel.setBackground(new Color(43, 134, 179)); //set color
        
        
        String name = ShoppingCartSystem.getActiveBuyer().getUsername();
        JLabel logo = new JLabel("Hello, " + name + "!");
        logo.setFont(logo.getFont().deriveFont(40.0f));
        logoPanel.add(logo);
        
        //Makes buttons
        JButton logout = new JButton("Logout");
        JButton shoppingCart = new JButton("Shopping Cart");
        logout.setPreferredSize(new Dimension(150,75));
        shoppingCart.setPreferredSize(new Dimension(150,75));
        
        
        //creates buttons
        buttonPanel.add(shoppingCart);
        buttonPanel.add(logout);
        
        //creates northPanel
        northPanel.add(logoPanel);
        northPanel.add(Box.createRigidArea(new Dimension(600,5)));  //LOGO GOES HERE
        northPanel.add(buttonPanel);
        
        //adds scrollbar to the page
        scrollBarPanel.add(verticalBar);
        
        //default table
        DefaultTableModel dm = generateTable();
      
        //creating table
        table = new JTable();
     
        table.setModel(dm);
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
       
        table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );
        
        table.getColumn("Button").setCellRenderer(new ButtonRenderer());
        table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("Description").setCellRenderer(new ButtonRenderer());
        table.getColumn("Description").setCellEditor(new ButtonEditor(new JCheckBox()));
        //creates scroll panel
        JScrollPane scroll = new JScrollPane(table);
        
        
        //mainPanel
        mainPanel.add(Box.createRigidArea(new Dimension(400,400)));
        mainPanel.add(scrollBarPanel, BorderLayout.EAST);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        //south panel
        southPanel.add(Box.createRigidArea(new Dimension(100,100)));
        
        //JFrame
        //main frame    
        frame.getContentPane().setBackground(Color.blue);  //background color
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//close program
        frame.add(scroll, BorderLayout.CENTER );
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(southPanel, BorderLayout.SOUTH);
        frame.setSize(1000,700);
        frame.pack(); 
        frame.setLocationRelativeTo(null); //set position
        frame.setVisible(true); //set visible
        
        // Logout Button 
        logout.addActionListener(new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
          frame.setVisible(false); 
          ShoppingCartSystem.getActiveBuyer().getCart().clearCart();
          ShoppingCartSystem.clearActiveBuyer();
          ShoppingCartSystem.loginPage.display();
        }
    } );
        
        shoppingCart.addActionListener(new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
          if (ShoppingCartSystem.getActiveBuyer().getCart().getSize() != 0)
          {
            if(viewedShoppingCart == false)
            {
              viewedShoppingCart = true;
              frame.setVisible(false); 
              ShoppingCartSystem.cartpage.display(ShoppingCartSystem.getActiveBuyer().getCart());
            }
            else
            {
                frame.setVisible(false);
                ShoppingCartSystem.cartpage.repaintTable(ShoppingCartSystem.getActiveBuyer().getCart());
            }
          }
          else
          {
              JOptionPane.showMessageDialog(frame, "You have no items in your shopping cart.");
          }
          
        }
    } );
    }
    
    
    //repaints table if user left the page
     public void repaintTable()
    {
        frame.setVisible(true);
        dm = generateTable();
        table.setModel(dm);
        
        table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
        table.getColumn("Button").setCellRenderer(new ButtonRenderer());
        table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("Description").setCellRenderer(new ButtonRenderer());
        table.getColumn("Description").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.repaint();
    }
     
     
     //creates default table model 
    
    public DefaultTableModel generateTable()
    {
        dm = new DefaultTableModel()
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                if(column == 1 || column == 0 || column == 4) return true;
                else return false;
                 
            }
        };  
        dm.setDataVector(new Object[][]  {  }, new Object[] { "Button", "Quantity", "Product", "Price", "Description", "Stock", "Sold by"});
        Iterator iter = ProductList.getAllProducts();
        
        
        while(iter.hasNext())
        {
            
            Product tempProduct = (Product) iter.next();
            
            Object[] row = new Object[] {"Add", "1", tempProduct.getName(), tempProduct.getPrice(),"Click for Description", tempProduct.getinventoryQuantity(), tempProduct.getSoldBy()};
            dm.addRow(row);
           
        }
        
        return dm;
    }
    
   
    //decorates jTable buttons
    class ButtonRenderer extends JButton implements TableCellRenderer 
    {

        public ButtonRenderer() 
        {
          setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column)
        {
          if (isSelected) 
          {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
          } else 
          {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
          }
          setText((value == null) ? "" : value.toString());
          return this;
        }
}

    //editor for JTable buttons
    class ButtonEditor extends DefaultCellEditor 
    {
        protected JButton button;

        private String label;

        private boolean isPushed;
        private boolean add;
        
        //constructor for button
        public ButtonEditor(JCheckBox checkBox) {
          super(checkBox);
          button = new JButton();
          button.setOpaque(true);

          button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              fireEditingStopped();
            }
          });
        }

    

    public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected, int row, int column) {
      if (isSelected) 
      {
          
        button.setForeground(table.getSelectionForeground());
        button.setBackground(table.getSelectionBackground());
      } else 
      {
        button.setForeground(table.getForeground());
        button.setBackground(table.getBackground());
      }
      label = (value == null) ? "" : value.toString();
      System.out.println(table.getValueAt(row, column + 1).toString());
      if(column == 0)
      {
          //adds to shopping cart
        String name = table.getValueAt(row, column + 2).toString();  
        String seller = table.getValueAt(row, column + 6).toString();  
        String quantity = table.getValueAt(row, column + 1).toString();
        Product temp = ProductList.getOneProduct(name, seller);
        ShoppingCartSystem.getActiveBuyer().getCart().addToCart(temp, Integer.parseInt(quantity));
        add = true;
        JOptionPane.showMessageDialog(button, "Product Added to Shopping Cart");
      }
      if(column == 4)
      {
          String name = table.getValueAt(row, column - 2).toString();
          String soldBy =  table.getValueAt(row, column + 2).toString();
          Product temp = ProductList.getOneProduct(name, soldBy);
          frame.setVisible(false);
          try
          {
              ShoppingCartSystem.descriptionPage.display(temp);
          } catch (IOException ex)
          {
              Logger.getLogger(SellerPage.class.getName()).log(Level.SEVERE, null, ex);
          }
          add = false;
      }
      
      button.setText(label);
      isPushed = true;
      return button;
    }
    
   

    public Object getCellEditorValue() 
    {
      if (isPushed) 
      {
        // System.out.println(label + ": Ouch!");
      }
      isPushed = false;
      return new String(label);
    }

    public boolean stopCellEditing() 
    {
      isPushed = false;
      return super.stopCellEditing();
    }

    protected void fireEditingStopped() 
    {
      super.fireEditingStopped();
    }
   }

     JFrame frame = new JFrame("Buyer Page");
     boolean viewedShoppingCart;
}