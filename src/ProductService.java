import java.util.ArrayList;

public class ProductService
{
    private final ArrayList<Product> products = new ArrayList<>();

    public ProductService() {
        products.add(new Product(1, "T-Shirt", 19.99, ""));
        products.add(new Product(2, "Jeans", 49.99, ""));
        products.add(new Product(3, "Sneakers", 89.00, ""));
        products.add(new Product(4, "Socks (3-pack)", 9.50, ""));
        products.add(new Product(5, "Sunglasses", 14.99, ""));
        products.add(new Product(6, "Hoodie", 39.99, ""));
        products.add(new Product(7, "Sweatpants", 29.99, ""));
        products.add(new Product(8, "Leggings", 24.99, ""));
        products.add(new Product(9, "Jacket", 79.99, ""));
        products.add(new Product(10, "Cargo Pant", 34.99, ""));
    }

    public void addProduct(Product p)
    {
        products.add(p);
    }
    public ArrayList<Product> getAllProducts()
    {
        return products;
    }
}