import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CreateSale extends JFrame implements ActionListener {
    private JComboBox<String> itemComboBox;
    private JSpinner quantitySpinner;
    private JButton addButton;
    private JButton saveButton;
    private JButton invoiceButton;
    private JTable saleTable;
    private DefaultTableModel saleTableModel;
    private JLabel totalLabel;
    private double saleTotal;

    public CreateSale() {
        setTitle("Create Sale");

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        itemComboBox = new JComboBox<>();
        populateItemComboBox();
        topPanel.add(new JLabel("Item:"));
        topPanel.add(itemComboBox);

        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        topPanel.add(new JLabel("Quantity:"));
        topPanel.add(quantitySpinner);

        addButton = new JButton("Add Item");
        addButton.addActionListener(this);
        topPanel.add(addButton);

        add(topPanel, BorderLayout.NORTH);

        saleTableModel = new DefaultTableModel();
        saleTableModel.addColumn("UPC");
        saleTableModel.addColumn("Name");
        saleTableModel.addColumn("Quantity");
        saleTableModel.addColumn("Subtotal");

        saleTable = new JTable(saleTableModel);
        add(new JScrollPane(saleTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        totalLabel = new JLabel("Total: $0.00");
        bottomPanel.add(totalLabel);

        saveButton = new JButton("Save Sale");
        saveButton.addActionListener(this);
        bottomPanel.add(saveButton);

        invoiceButton = new JButton("Generate Invoice");
        invoiceButton.addActionListener(this);
        bottomPanel.add(invoiceButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void populateItemComboBox() {
        try {
            String url = "jdbc:mysql://localhost:3306/DAWNS?autoReconnect=true&useSSL=false";
            String user = "root";
            String password = "";
            Connection conn = DriverManager.getConnection(url, user, password);

            String sql = "SELECT upc, name FROM items";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                itemComboBox.addItem(rs.getString("upc") + " - " + rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String selectedItem = (String) itemComboBox.getSelectedItem();
            if (selectedItem != null) {
                String[] parts = selectedItem.split(" - ");
                String upc = parts[0];
                addItemToSale(upc, (Integer) quantitySpinner.getValue());
            }
        } else if (e.getSource() == saveButton) {
            saveSale();
        } else if (e.getSource() == invoiceButton) {
            generateInvoice();
        }
    }

    private void addItemToSale(String upc, int quantity) {
        try {
            String url = "jdbc:mysql://localhost:3306/DAWNS?autoReconnect=true&useSSL=false";
            String user = "root";
            String password = "";
            Connection conn = DriverManager.getConnection(url, user, password);
    
            String sql = "SELECT * FROM items WHERE upc = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, upc);
            ResultSet rs = pstmt.executeQuery();
    
            if (rs.next()) {
                String name = rs.getString("name");
                double unitPrice = rs.getDouble("unit_price");
                int availableUnits = rs.getInt("units");
    
                if (quantity <= availableUnits) {
                    double subtotal = unitPrice * quantity;
                    saleTableModel.addRow(new Object[]{upc, name, quantity, subtotal});
    
                    saleTotal += subtotal;
                    totalLabel.setText(String.format("Total: $%.2f", saleTotal));
                } else {
                    JOptionPane.showMessageDialog(this, "Not enough units available", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void saveSale() {
        try {
            String url = "jdbc:mysql://localhost:3306/DAWNS?autoReconnect=true&useSSL=false";
            String user = "root";
            String password = "";
            Connection conn = DriverManager.getConnection(url, user, password);

            // create a new transaction record
            String sql = "INSERT INTO Transactions () VALUES ()";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            int transactionId = 0;
            if (rs.next()) {
                transactionId = rs.getInt(1);
            }

            // save each item in the sale with the transaction id as the foreign key
            for (int i = 0; i < saleTableModel.getRowCount(); i++) {
                String upc = (String) saleTableModel.getValueAt(i, 0);
                int quantity = (Integer) saleTableModel.getValueAt(i, 2);
                double subtotal = (Double) saleTableModel.getValueAt(i, 3);

                sql = "INSERT INTO sales (upc, quantity, subtotal, total, transaction_id) VALUES (?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, upc);
                pstmt.setInt(2, quantity);
                pstmt.setDouble(3, subtotal);
                pstmt.setDouble(4, saleTotal);
                pstmt.setInt(5, transactionId);
                pstmt.executeUpdate();

                // update the item's units in the items table
                sql = "UPDATE items SET units = units - ? WHERE upc = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, quantity);
                pstmt.setString(2, upc);
                pstmt.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Sale saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            saleTableModel.setRowCount(0);
            saleTotal = 0;
            totalLabel.setText(String.format("Total: $%.2f", saleTotal));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    private void generateInvoice() {
        JFrame invoiceFrame = new JFrame("Invoice");
        JPanel invoicePanel = new JPanel(new BorderLayout());
        JTextArea invoiceTextArea = new JTextArea();
    
        invoiceTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        invoiceTextArea.setEditable(false);
    
        String invoiceText = "";
        invoiceText += String.format("%50s\n", "Invoice");
        invoiceText += "\n";
        invoiceText += String.format("Date: %s\n", java.time.LocalDate.now());
        invoiceText += "\n";
        invoiceText += String.format("%-12s %-40s %12s %12s\n", "UPC", "Name", "Quantity", "Subtotal");
    
        for (int i = 0; i < saleTableModel.getRowCount(); i++) {
            String upc = (String) saleTableModel.getValueAt(i, 0);
            String name = (String) saleTableModel.getValueAt(i, 1);
            int quantity = (Integer) saleTableModel.getValueAt(i, 2);
            double subtotal = (Double) saleTableModel.getValueAt(i, 3);
    
            invoiceText += String.format("%-12s %-40s %12d %,12.2f\n", upc, name, quantity, subtotal);
        }
    
        invoiceText += "\n";
        invoiceText += String.format("%64s\n", "Total: " + String.format("$%,.2f", saleTotal));
    
        invoiceTextArea.setText(invoiceText);
        invoicePanel.add(new JScrollPane(invoiceTextArea), BorderLayout.CENTER);
    
        invoiceFrame.getContentPane().add(invoicePanel);
        invoiceFrame.setSize(800, 600);
        invoiceFrame.setLocationRelativeTo(null);
        invoiceFrame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new CreateSale();
    }}
    
    