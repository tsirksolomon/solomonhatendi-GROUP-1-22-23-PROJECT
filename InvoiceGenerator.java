import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.*;
import java.text.SimpleDateFormat;

public class InvoiceGenerator {
    private static final String INVOICE_FORMAT = "Invoice ID: %d\nDate: %s\n\n%-20s%-10s%-10s%-10s\n\n%s\n\nTotal: $%.2f";

    public static String generateInvoice(int saleId) {
        String result = "";
        try {
            String url = "jdbc:mysql://localhost:3306/DAWNS?autoReconnect=true&useSSL=false";
            String user = "root";
            String password = "";
            Connection conn = DriverManager.getConnection(url, user, password);

            // Fetch sale details
            String sql = "SELECT s.id, s.date, i.name, s.quantity, i.unit_price, s.subtotal FROM sales s JOIN items i ON s.upc = i.upc WHERE s.id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, saleId);
            ResultSet rs = pstmt.executeQuery();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            double total = 0;
            StringBuilder items = new StringBuilder();
            String date = "";

            while (rs.next()) {
                if (date.isEmpty()) {
                    date = dateFormat.format(rs.getDate("date"));
                }
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                double unitPrice = rs.getDouble("unit_price");
                double subtotal = rs.getDouble("subtotal");

                items.append(String.format("%-20s%-10d%-10.2f%-10.2f\n", name, quantity, unitPrice, subtotal));
                total += subtotal;
            }

            result = String.format(INVOICE_FORMAT, saleId, date, "Item", "Qty", "Price", "Total", items.toString(), total);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void printInvoice(String invoice) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new InvoicePrintable(invoice));

        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        }
    }
}
