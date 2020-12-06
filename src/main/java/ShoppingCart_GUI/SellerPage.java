/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
//SELLER PAGE
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import ShoppingCart_main.Product;
import ShoppingCart_main.ShoppingCartSystem;

//GUI for seller page

public class SellerPage
{
    static JFrame frame;
    DefaultTableModel dm; 
    JTable table;
    JTable addTable;
    DefaultTableCellRenderer centerRenderer;
    ArrayList<String> productNames = new ArrayList();
    DefaultTableCellRenderer descriptionRenderer = new DefaultTableCellRenderer();
    
    
    public SellerPage()
    {
       frame = new JFrame();    //creates main frame
      
    }
    
     //display for sellers page
    public void display()
    {
        
        //Make panels
        JPanel mainPanel = new JPanel();    //panel for table
        JPanel northPanel = new JPanel();   //panel for buttonPanel
        JPanel southPanel = new JPanel();   //panel for main panel
        JPanel buttonPanel = new JPanel();   //panel for financial summary
        JPanel scrollBarPanel = new JPanel();   //panel for scroll bar
        JPanel removeButtonPanel = new JPanel();    //panel for remove buttons
        JPanel logoPanel = new JPanel();
        
        //Make scrollbar
        JScrollBar verticalBar = new JScrollBar(JScrollBar.VERTICAL, 30, 40, 0, 300);

        //Set layouts for panels
        mainPanel.setLayout(new BorderLayout());
        northPanel.setLayout(new FlowLayout());
        southPanel.setLayout(new FlowLayout());
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        scrollBarPanel.setLayout(new BorderLayout());
        removeButtonPanel.setLayout(new BorderLayout());
        logoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        //sets colors
        mainPanel.setBackground(new Color(43, 134, 179)); 
        northPanel.setBackground(new Color(43, 134, 179)); 
        southPanel.setBackground(new Color(43, 134, 179)); 
        buttonPanel.setBackground(new Color(43, 134, 179)); 
        northPanel.setBackground(new Color(43, 134, 179)); 
        logoPanel.setBackground(new Color(43, 134, 179)); 
        
        
        String name = ShoppingCartSystem.getActiveSeller().getUsername().substring(0, 1).toUpperCase() + ShoppingCartSystem.getActiveSeller().getUsername().substring(1);
        JLabel logo = new JLabel("Hello, " + name);
        logo.setFont(logo.getFont().deriveFont(33.0f));
        logoPanel.add(logo);
        
        //buttons
        JButton logout = new JButton("Logout");
        JButton financialSummary = new JButton("Financial Summary");
        JButton AddProduct = new JButton("Add Product");
        logout.setPreferredSize(new Dimension(150,75));
        financialSummary.setPreferredSize(new Dimension(150,75));
        
        
        //creates buttons
        buttonPanel.add(financialSummary);
        buttonPanel.add(logout);
        
        //creates northPanel
        northPanel.add(logoPanel);
        northPanel.add(Box.createRigidArea(new Dimension(600,5)));  //LOGO GOES HERE
        northPanel.add(buttonPanel);
        
        //scrollbar
        scrollBarPanel.add(verticalBar);
        
        //default table model
        DefaultTableModel dm = generateTable();
      
        //Creates the Table
       table = new JTable();
               
       table.getTableHeader().setReorderingAllowed(false);
       table.setOpaque(false);
       
       
       //Creates bottom table
       addTable = new JTable();
       addTable.getTableHeader().setReorderingAllowed(false);
       DefaultTableModel addTableModel = generateAddTable();   //generate model
       
       addTable.setModel(addTableModel);    //add table model
     
       table.setModel(dm);
       
       table.getColumn("Button").setCellRenderer(new ButtonRenderer());
       table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
       table.getColumn("Description").setCellRenderer(new ButtonRenderer());
       table.getColumn("Description").setCellEditor(new ButtonEditor(new JCheckBox()));
        
        //scrollpane
        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(new Color(43, 134, 179));
        scroll.getViewport().setOpaque(true);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        
       
        table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );
        
        mainPanel.add(Box.createRigidArea(new Dimension(400,400)));
        mainPanel.add(scroll, BorderLayout.CENTER);
        
        southPanel.add(AddProduct);
        southPanel.add(addTable);
        
        mainPanel.add(southPanel,BorderLayout.SOUTH );
        
        
        //JFrame
        frame.getContentPane().setBackground(Color.blue);  
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        //frame.add(scroll, BorderLayout.CENTER );
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(mainPanel);   
        frame.setSize(1000,700);
        frame.pack();       
        frame.setLocationRelativeTo(null);  
        frame.setVisible(true); 
        
        logout.addActionListener(new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
          frame.setVisible(false); 
          ShoppingCartSystem.clearActiveSeller();
          ShoppingCartSystem.loginPage.display();
        }
    } );
        
        financialSummary.addActionListener(new ActionListener()
       {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
          frame.setVisible(false); 
          
            try
            {
                ShoppingCartSystem.finanSummaryPage.display(ShoppingCartSystem.getActiveSeller());
            } catch (IOException ex)
            {
                Logger.getLogger(SellerPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } );
        
        AddProduct.addActionListener(new ActionListener()   //listener for add button
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               
               String name = (String) addTable.getValueAt(0,0);
               String price =  (String) addTable.getValueAt(0,1);
               String description = (String) addTable.getValueAt(0, 2);
               String Stock = (String) addTable.getValueAt(0, 3);
               String cost = (String) addTable.getValueAt(0, 4);
               
               Product product = new Product(name, Double.parseDouble(price), description, Integer.parseInt(Stock),Double.parseDouble(cost) , ShoppingCartSystem.getActiveSeller().getUsername() ,0);
               ShoppingCartSystem.getActiveSeller().getInventory().addToInventory(product);
               ShoppingCartSystem.getActiveSeller().getInventory().overWriteInventoryFile(ShoppingCartSystem.getActiveSeller());
                DefaultTableModel dm = generateTable();
                table.setModel(dm);

                table.getColumn("Button").setCellRenderer(new ButtonRenderer());
                table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
                table.getColumn("Description").setCellRenderer(new ButtonRenderer());
                table.getColumn("Description").setCellEditor(new ButtonEditor(new JCheckBox()));


                table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );

                table.repaint();
            }
        });
        
    }
    
   //table for sellers page
    public  DefaultTableModel generateAddTable()
    {
        dm = new DefaultTableModel();
        dm.setDataVector(new Object[][]  { }, new Object[] {"Product", "Price","Description","Stock", "Cost",});
        JTextField tf7 = new JTextField(10);
        Object[] row = {"Product", "Price","Description","Stock", "Cost"};
        dm.addRow(row);
        return dm;
    }
    public DefaultTableModel generateTable()
    {
        dm = new DefaultTableModel()
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                if(column == 6) return false;
                else return true;
                 
            }
        };   
        
        dm.setDataVector(new Object[][]  {  }, new Object[] { "Button", "Product", "Price","Description", "Stock", "Cost",  "Total Sold"});
        Iterator iter = ShoppingCartSystem.getActiveSeller().getInventory().getAllProducts();
        
       
        while(iter.hasNext())
        {
            
            Product tempProduct = (Product) iter.next();
            productNames.add(tempProduct.getName());
            
            
            Object[] row = new Object[] {"Update", tempProduct.getName(), tempProduct.getPrice(),"Click for Description", tempProduct.getinventoryQuantity(),  tempProduct.getCost(),  tempProduct.getTotalNumberSold()};
            dm.addRow(row);
           
        }
        
        return dm;
    }
    //decorator for Buttons
    
    class ButtonRenderer extends JButton implements TableCellRenderer 
    {

        public ButtonRenderer() 
        {
          setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column)
        {
          if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
          } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
          }
          setText((value == null) ? "" : value.toString());
          return this;
        }
}

    //JTable button editor
    
    class ButtonEditor extends DefaultCellEditor 
    {
        protected JButton button;

        private String label;

        private boolean isPushed;
        
        //constructor
        public ButtonEditor(JCheckBox checkBox) 
        {
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
            boolean isSelected, int row, int column) 
        {
          if (isSelected) 
          {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
          } 
          else 
          {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
          }
          label = (value == null) ? "" : value.toString();
          System.out.println(table.getValueAt(row, column + 1).toString());

          if(column == 0)   // Update Button
          {
                if(table.getValueAt(row, column + 4).equals("0"))   
                {
                   String name = table.getValueAt(row, column + 1).toString();  
                   Product temp = ShoppingCartSystem.getActiveSeller().getInventory().getProduct(name);
                   ShoppingCartSystem.getActiveSeller().getInventory().removeProduct(temp);

                   ShoppingCartSystem.getActiveSeller().getInventory().overWriteInventoryFile(ShoppingCartSystem.getActiveSeller());
                   ShoppingCartSystem.updateProductList(ShoppingCartSystem.getSellerList());

                }
                else    //Price or quantity change
                {
                    String name = table.getValueAt(row, column + 1).toString();
                    //new price
                    String price = table.getValueAt(row, column + 2).toString();
                    String quantity = table.getValueAt(row, column + 4).toString();
                    int q = Integer.parseInt(quantity);

                    Product temp = ShoppingCartSystem.getActiveSeller().getInventory().getProduct(productNames.get(row));  
                    temp.setName(name);
                    temp.setPrice(Double.parseDouble(price));
                    temp.setInventoryQuantity(q);
                    ShoppingCartSystem.getActiveSeller().getInventory().overWriteInventoryFile(ShoppingCartSystem.getActiveSeller());
                }


              if (ShoppingCartSystem.getActiveSeller().getInventory().getSize() > 0) 
              {
                    productNames.clear();
                    dm = generateTable();
                    table.setModel(dm);

                    table.getColumn("Button").setCellRenderer(new ButtonRenderer());
                    table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
                    table.getColumn("Description").setCellRenderer(new ButtonRenderer());
                    table.getColumn("Description").setCellEditor(new ButtonEditor(new JCheckBox()));


                    table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
                    table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
                    table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
                    table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
                    table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );

                    table.repaint();
              }

              else //empty table
              {
                productNames.clear();
                 dm.setDataVector(new Object[][]  {  }, new Object[] {"Button", "Product", "Price","Description", "Stock", "Cost",  "Total Sold"});
                 table.setModel(dm);
                table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(4).setCellRenderer( descriptionRenderer );
                table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );
                table.getColumn("Button").setCellRenderer(new ButtonRenderer());
                table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
                table.getColumn("Description").setCellRenderer(new ButtonRenderer());
                table.getColumn("Description").setCellEditor(new ButtonEditor(new JCheckBox()));
                table.repaint();
              }

              button.setText(label);

          }
          else if(column == 3)  //Description Button
          {
              String name = table.getValueAt(row, column - 2).toString();
              Product temp = ShoppingCartSystem.getActiveSeller().getInventory().getProduct(name);
              frame.setVisible(false);
              try
              {
                  ShoppingCartSystem.descriptionPage.display(temp);
              } catch (IOException ex)
              {
                  Logger.getLogger(SellerPage.class.getName()).log(Level.SEVERE, null, ex);
              }
          }

          else  
          {

              if (ShoppingCartSystem.getActiveSeller().getInventory().getSize() > 0)
              {
                productNames.clear();
                DefaultTableModel dm2 = generateTable();
                table.setModel(dm2);

                table.getColumn("Button").setCellRenderer(new ButtonRenderer());
                table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
                table.getColumn("Description").setCellRenderer(new ButtonRenderer());
                table.getColumn("Description").setCellEditor(new ButtonEditor(new JCheckBox()));

                table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );

                table.repaint();
          }

          else
            {
                productNames.clear();
                 dm.setDataVector(new Object[][]  {  }, new Object[] { "Button", "Product", "Price","Description", "Stock", "Cost",  "Total Sold"});
                 table.setModel(dm);
                table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(5).setCellRenderer( centerRenderer );
                table.getColumnModel().getColumn(6).setCellRenderer( centerRenderer );
                table.getColumn("Button").setCellRenderer(new ButtonRenderer());
                table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
                table.getColumn("Description").setCellRenderer(new ButtonRenderer());
                table.getColumn("Description").setCellEditor(new ButtonEditor(new JCheckBox()));

                table.repaint();
            }
          }
          return button;
        }

        //returns object based on button

        public Object getCellEditorValue() 
        {
          if (isPushed) {
            JOptionPane.showMessageDialog(button, "Product Removed From Inventory");
          }
          isPushed = false;
          return new String(label);
        }

        public boolean stopCellEditing() {
          isPushed = false;
          return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
          super.fireEditingStopped();
        }
       }
        public void removeRow(int row)
        {
            dm.removeRow(row);
        }
   
   
    }