import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GroceryGUI{
    private static double totalValue = 0;
    public static void main(String[]args){

        //     frames
        JFrame frame = new JFrame("Grocery System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,600);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        
        //     Hashset 
        HashSet itemList = new HashSet<String>();

        //     ListModels
        DefaultListModel<String> GroceryListModel = new DefaultListModel<>();
        JList<String> GroceryList = new JList<>(GroceryListModel);
        GroceryList.setFont(new Font("Comic Sans MS", Font.BOLD, 12));

        DefaultListModel<String> TransacListModel = new DefaultListModel<>();
        JList<String> transactionList = new JList<>(TransacListModel);
        transactionList.setFont(new Font("Comic Sans MS", Font.BOLD, 13));


        //     ScrollPane
        JScrollPane GScrollPane = new JScrollPane(GroceryList);
        GScrollPane.setBounds(12,45,500,470);
        
        JScrollPane TScrollPane = new JScrollPane(transactionList);
        transactionList.setBackground(Color.gray);
        TScrollPane.setBounds(512,265,360,250);
        

        //     panels
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.ORANGE);
        controlPanel.setBounds(0,520,890,50);

        JPanel totalPanel = new JPanel();
        totalPanel.setBackground(Color.GREEN);
        totalPanel.setBounds(512,45,360,190);
        totalPanel.setLayout(new BorderLayout(1,1));
        

        //     labels
        JLabel itemLab = new JLabel("Item Name:");
        itemLab.setFont(new Font("Tahoma", Font.BOLD, 11));

        JLabel quantLab = new JLabel("Quantity:");
        quantLab.setFont(new Font("Tahoma", Font.BOLD, 11));

        JLabel priceLab = new JLabel("Price Each:");
        priceLab.setFont(new Font("Tahoma", Font.BOLD, 11));

        JLabel titleGrocery = new JLabel("List of Groceries ");
        titleGrocery.setBounds(185,-20,150,100);
        titleGrocery.setFont(new Font("Comic Sans MS", Font.BOLD, 16));

        JLabel titleTransaction = new JLabel("Transaction Log");
        titleTransaction.setBounds(625,200,150,100);
        titleTransaction.setFont(new Font("Comic Sans MS", Font.BOLD, 16));

        JLabel totalLabel = new JLabel("          Total Value: P" + totalValue);
        totalLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));

        //     textFields
        JTextField addField = new JTextField(10);
        JTextField quantField = new JTextField(5);
        JTextField priceField = new JTextField(5);
        
        //     buttons
        JButton addButton = new JButton("Add Item");
        addButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String itemName = addField.getText().toUpperCase();
                String quantity = quantField.getText();
                String price = priceField.getText();
                if(!itemName.isEmpty() && !quantity.isEmpty() && !price.isEmpty()){
                    try {
                        int quant = Integer.parseInt(quantity);
                        double pera = Double.parseDouble(price);
                        if(quant <= 0 || pera <= 0){
                            throw new NumberFormatException();
                        }
                        String addItem = " " + itemName + " - (Quantity: " + quant + ")" + " (Price Each: P"+ pera +")";
                        if(!itemList.contains(itemName)) {
                            GroceryListModel.addElement(addItem);
                            itemList.add(itemName);
                            addField.setText("");
                            quantField.setText("");
                            priceField.setText("");
                            TransacListModel.addElement(" Added - " + itemName + " ("+quant+")" + " (P"+pera+")");
                            totalValue += quant * pera;
                            totalLabel.setText("         Total Value: P" + totalValue);
                        }else{
                            JOptionPane.showMessageDialog(frame, "Item already exists in the Groceries.", "ERROR!", JOptionPane.ERROR_MESSAGE);
                        }
                    }catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid quantity or price (NUMBERS ONLY!).", "ERROR!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter an Item Name, Quantity and Price.", "ERROR!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JButton removeButton = new JButton("Remove Item");
        removeButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        removeButton.setBackground(Color.pink);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                int selectedIndex = GroceryList.getSelectedIndex();
                if(selectedIndex != -1){
                    String removedItem = GroceryListModel.getElementAt(selectedIndex);
                    if(removedItem != null){
                        int quantityIndex = removedItem.indexOf("Quantity: ") + "Quantity: ".length();
                        int quantityEndIndex = removedItem.indexOf(")", quantityIndex);
                        int quant = Integer.parseInt(removedItem.substring(quantityIndex, quantityEndIndex));

                        int priceIndex = removedItem.indexOf("Price Each: P") + "Price Each: P".length();
                        int priceEndIndex = removedItem.indexOf(")", priceIndex);
                        double price = Double.parseDouble(removedItem.substring(priceIndex, priceEndIndex));

                        GroceryListModel.remove(selectedIndex);
                        String itemName = removedItem.substring(1, removedItem.indexOf(" - "));
                        itemList.remove(itemName);
                        TransacListModel.addElement(" Removed - " + removedItem);
                        totalValue -= quant * price;
                        totalLabel.setText("          Total Value: P" + totalValue);
                    }else{
                        JOptionPane.showMessageDialog(frame, "Selected item is null.", "ERROR!", JOptionPane.ERROR_MESSAGE);
                    }

                }else{
                    JOptionPane.showMessageDialog(frame, "Please select an item to remove.", "ERROR!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton clearButton = new JButton("Clear All");
        clearButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        clearButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                GroceryListModel.clear();
                itemList.clear();
                TransacListModel.clear();
                TransacListModel.addElement(" Cleared the Groceries and TransactionLog");
                totalValue = 0;
                totalLabel.setText("          Total Value: P" + totalValue);
            }
        });

        //add to become visible

        totalPanel.add(totalLabel);

        controlPanel.add(itemLab);
        controlPanel.add(addField);
        controlPanel.add(quantLab);
        controlPanel.add(quantField);
        controlPanel.add(priceLab);
        controlPanel.add(priceField);

        controlPanel.add(addButton);
        controlPanel.add(removeButton);
        controlPanel.add(clearButton);

        frame.add(titleTransaction);
        frame.add(titleGrocery);
        frame.add(GScrollPane);
        frame.add(TScrollPane);
        frame.add(totalPanel);
        frame.add(controlPanel);
        
        frame.setVisible(true);

    }
}