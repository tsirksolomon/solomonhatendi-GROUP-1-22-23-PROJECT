import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class InvoicePrintable implements Printable {
    private String invoice;

    public InvoicePrintable(String invoice) {
        this.invoice = invoice;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        graphics.setFont(new Font("Courier", Font.PLAIN, 12));
        int lineHeight = graphics.getFontMetrics().getHeight();
        int x = (int) pageFormat.getImageableX();
        int y = (int) pageFormat.getImageableY() + lineHeight;

        for (String line : invoice.split("\n")) {
            graphics.drawString(line, x, y);
            y += lineHeight;
        }

        return PAGE_EXISTS;
    }
}
