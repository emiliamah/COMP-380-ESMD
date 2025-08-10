public class Main
{
    public static void main(String[] args) {
        ProductService service = new ProductService();

        service.addProduct(new Product(1, "White T-Shirt", 19.99, "images/white.jpg"));
        service.addProduct(new Product(2, "Black T-Shirt", 21.99, "images/black.jpg"));
        service.addProduct(new Product(3, "Graphic T-Shirt", 24.50, "images/graphic.jpg"));
        service.addProduct(new Product(4, "Striped T-Shirt", 18.75, "images/striped.jpg"));

        for (Product p : service.getAllProducts()) {
            System.out.println(p.getName() + " - $" + p.getPrice());
        }
    }
}
