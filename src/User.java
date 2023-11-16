import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<Order> purchaseHistory;

    public User(String name) {
        this.name = name;
        this.purchaseHistory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(List<Order> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    public void addPurchase(Order order) {
        purchaseHistory.add(order);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", purchaseHistory=" + purchaseHistory +
                '}';
    }
}
