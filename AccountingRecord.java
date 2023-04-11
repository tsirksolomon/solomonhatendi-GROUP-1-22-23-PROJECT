import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class AccountingRecord extends JFrame {
    private JTable table;
    private JTextField startDateField, endDateField;
    private JComboBox<String> filterComboBox;
    
    public AccountingRecord() {
        setTitle("Sales Record");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Initialize the table with the column names
        String[] columnNames = {"Sale ID", "UPC", "Quantity", "Subtotal", "Total", "Sale Date", "Transaction ID"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        
        // Connect to the database and retrieve the sales data
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DAWNS?autoReconnect=true&useSSL=false", "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Sales");
            
            // Add each row to the table model
            while (rs.next()) {
                int sale_id = rs.getInt("sale_id");
                String upc = rs.getString("upc");
                int quantity = rs.getInt("quantity");
                double subtotal = rs.getDouble("subtotal");
                double total = rs.getDouble("total");
                Timestamp sale_date = rs.getTimestamp("sale_date");
                int transaction_id = rs.getInt("transaction_id");
                
                Object[] row = {sale_id, upc, quantity, subtotal, total, sale_date, transaction_id};
                model.addRow(row);
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Add the table to the window
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);
        
        // Add the filter panel to the window
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout());
        
        JLabel startDateLabel = new JLabel("Date:");
        startDateField = new JTextField(10);
        filterPanel.add(startDateLabel);
        filterPanel.add(startDateField);
        
        filterComboBox = new JComboBox<String>(new String[]{"Before", "On/After"});
        filterPanel.add(filterComboBox);
        
        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyFilter();
            }
        });
        filterPanel.add(filterButton);
        
        getContentPane().add(filterPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }

    private void applyFilter() {
        String startDateStr = startDateField.getText();
        String filterType = (String) filterComboBox.getSelectedItem();
        
        // Validate the input dates
        if (startDateStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a start date.");
            return;
        }
        
        Timestamp startDate = Timestamp.valueOf(startDateStr + " 00:00:00");
        
        // Modify the SQL query based on the selected filter type and transaction id
        String sql = "SELECT * FROM Sales WHERE sale_date ";
        if (filterType.equals("Before")) {
            sql += "< '" + startDate + "'";
        } else {
            sql += "> '" + startDate + "'";
        }
        
        // Connect to the database and retrieve the filtered sales data
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DAWNS?autoReconnect=true&useSSL=false", "root", "");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            // Clear the table model
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            
            // Add each filtered row to the table model
            while (rs.next()) {
                int sale_id = rs.getInt("sale_id");
                String upc = rs.getString("upc");
                int quantity = rs.getInt("quantity");
                double subtotal = rs.getDouble("subtotal");
                double total = rs.getDouble("total");
                Timestamp sale_date = rs.getTimestamp("sale_date");
                int transaction_id = rs.getInt("transaction_id");

                
                Object[] row = {sale_id, upc, quantity, subtotal, total, sale_date, transaction_id};
                model.addRow(row);
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AccountingRecord();
    }}


