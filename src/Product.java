// This Product.java class serves to 'describe' what a product is to our program
// (i.e. every product has an ID, a name, a price, and an image)...
public class Product {
    private int id;
    private String name;
    private double price;
    private String imagePath;

    public Product(int id, String name, double price, String imagePath) {
        this.id = id; this.name = name; this.price = price; this.imagePath = imagePath;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getImagePath() { return imagePath; }

    @Override public String toString() { return name + " - $" + String.format("%.2f", price); }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        return id == ((Product) o).id;
    }
    @Override public int hashCode() { return Integer.hashCode(id); }
}