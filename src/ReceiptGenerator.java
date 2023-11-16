import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiptGenerator {

    private boolean isPaid;

    public ReceiptGenerator(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public void generateReceipt(Order order, String filePath) throws ReceiptEditingException {
        if (isPaid) {
            throw new ReceiptEditingException("Cannot edit a paid receipt.");
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("Receipt");
            writer.println("---------------------------");
            writer.println("Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            writer.println("User: " + order.getUser().getName());
            writer.println("Products:");

            for (Product product : order.getProducts()) {
                writer.println(product.getName() + " - $" + product.getPrice() + " x " + product.getQuantity());
            }

            double totalAmount = order.getProducts().stream()
                    .map(product -> product.getPrice() * product.getQuantity())
                    .reduce(0.0, Double::sum);
            writer.println("---------------------------");
            writer.println("Total: $" + totalAmount);
        } catch (IOException e) {
            System.err.println("Error writing receipt to file: " + e.getMessage());
        }
    }

    public static class ReceiptEditingException extends Exception {
        public ReceiptEditingException(String message) {
            super(message);
        }
    }
}
