import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

public class Store {
    private List<Product> products;
    private List<Order> orders;
    private ReceiptGenerator receiptGenerator;

    public Store(List<Product> products, List<Order> orders, boolean isPaid) {
        this.products = products;
        this.orders = orders;
        this.receiptGenerator = new ReceiptGenerator(isPaid);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void sellProducts(Order order) throws ReceiptGenerator.ReceiptEditingException {
        try {

            for (Product productToSell : order.getProducts()) {
                Product storedProduct = findProductById(productToSell.getId());
                if (storedProduct.getQuantity() < productToSell.getQuantity() ||
                        !storedProduct.getName().equalsIgnoreCase(productToSell.getName())) {
                    throw new IllegalArgumentException("Product " + productToSell.getName() + " is not available.");
                }

                storedProduct.setQuantity(storedProduct.getQuantity() - productToSell.getQuantity());
            }

            receiptGenerator.generateReceipt(order, "receipt.txt");


            order.getUser().addPurchase(order);

            products.removeAll(order.getProducts());

        } catch (ProductNotFoundException e) {
            System.err.println("Error selling products: " + e.getMessage());
        }
    }

    public void editProduct(Product editedProduct) throws ProductNotFoundException {
        boolean productFound = false;

        for (int i = 0; i < products.size(); i++) {
            Product currentProduct = products.get(i);

            if (currentProduct.getId().equals(editedProduct.getId())) {
                products.set(i, editedProduct);
                productFound = true;
                break;
            }
        }

        if (!productFound) {
            throw new ProductNotFoundException("Product not found for editing. ID: " + editedProduct.getId());
        }
    }

    private Product findProductById(String productId) throws ProductNotFoundException {
        return products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product not found. ID: " + productId));
    }

    public Order placeOrder(User user, List<Product> products) {
        Order order = new Order(user, products);
        orders.add(order);
        return order;
    }

    public List<Product> filterAndSortProducts(double minPrice, double maxPrice) {
        return products.stream()
                .filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());
    }

    public double calculateAveragePrice(List<Product> products) {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .average()
                .orElse(0.0);
    }

    public double getUserExpenses(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return orders.stream()
                .filter(order -> order.getUser().equals(user))
                .filter(order -> order.getOrderDate().isAfter(startDate) && order.getOrderDate().isBefore(endDate))
                .flatMap(order -> order.getProducts().stream())
                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .sum();
    }

    public void sortProductsByPrice(List<Product> products) {
        Collections.sort(products, (p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
    }
    public Map<String, Integer> getTotalQuantityByProductForUser(User user, List<Order> purchaseHistory) {
        return purchaseHistory.stream()
                .filter(order -> order.getUser().equals(user))
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.groupingBy(Product::getId, Collectors.summingInt(Product::getQuantity)));
    }
}
