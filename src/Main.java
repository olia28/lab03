import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Product> productsFromFile = FileService.readProductsFromFile("products.txt");

        Store store = new Store(productsFromFile, new ArrayList<>(), false);

        User user = new User("Ivan Shevchenko");

        List<Product> orderProducts = Arrays.asList(
                new Product("1", "Milk", 78.0, 10, "Ferma", ProductType.DIARY),
                new Product("2", "Bread", 90.0, 20, "Kolosok", ProductType.BAKERY),
                new Product("4", "Tea",95.75,40, "Lipton", ProductType.BEVERAGES),
                new Product("7", "Apples",40.99,30, "Dole", ProductType.FRUITS)
        );

        Order order = store.placeOrder(user, orderProducts);
        try {
            store.sellProducts(order);
        } catch (ReceiptGenerator.ReceiptEditingException e) {
            System.err.println("Error editing receipt: " + e.getMessage());
        }

        System.out.println(user.getPurchaseHistory());

        double averagePrice = store.calculateAveragePrice(productsFromFile);
        System.out.println("Average Price of Products: " + averagePrice);
    }
}
