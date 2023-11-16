import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class FileService {
    public static List<Product> readProductsFromFile(String filename) {
        List<Product> products = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            products = reader.lines()
                    .map(line -> line.split(","))
                    .filter(parts -> parts.length == 6)
                    .map(parts -> {
                        try {
                            double price = Double.parseDouble(parts[2]);
                            int quantity = Integer.parseInt(parts[3]);
                            ProductType type = ProductType.valueOf(parts[5].toUpperCase());
                            return new Product(parts[0], parts[1], price, quantity, parts[4], type);
                        } catch (IllegalArgumentException | NumberFormatException e) {
                            System.err.println("Error parsing numeric values in line: " + Arrays.toString(parts));
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading file: " + e.getMessage());
        }

        return products;
    }


    public void saveProductsToFile(List<Product> products, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(products);
        }
    }

    public List<Order> readPurchaseHistoryFromFile(String filePath) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Order>) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Error reading purchase history from file", e);
        }
    }

    public void savePurchaseHistoryToFile(List<Order> purchaseHistory, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(purchaseHistory);
        }
    }
}
