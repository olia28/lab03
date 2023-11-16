import java.util.List;
import java.util.stream.Collectors;
public class Product {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private String manufacturer;
    private ProductType type;

    public Product(String id, String name, double price, int quantity, String manufacturer, ProductType type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
        this.type = type;

        if (type == ProductType.FRUITS || type == ProductType.VEGETABLES) {
            this.name += " Package";
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", manufacturer='" + manufacturer + '\'' +
                ", type=" + type +
                '}';
    }

}
