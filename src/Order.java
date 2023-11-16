import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private User user;
    private List<Product> products;
    private LocalDateTime orderDate;

    public Order(User user, List<Product> products) {
        this.user = user;
        this.products = products;
        this.orderDate = LocalDateTime.now();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "user=" + user +
                ", products=" + products +
                ", orderDate=" + orderDate +
                '}';
    }
}
