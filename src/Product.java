/**
 An online store has a list of products with the name and the price.
 @author Emilia Mahmoodi
 @version 1.0
 */

public class Product {
    /**
     Represents product details like id, name, and price.
     */
    private int productId;
    private String productName;
    private double productPrice;
    private String imagePath;

    /**
      Constructs a product with id, name, and price.
      @param productId the product id
      @param productName the product name
      @param productPrice the product price
     */
    public Product(int productId, String productName, double productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    /**
     Constructs a product with id, name, price and image path
     */

    public Product(int productId, String productName, double productPrice, String imagePath) {
        this(productId, productName, productPrice);
        this.imagePath = imagePath;
    }

    /**
     @return the product id
     */
    public int getProductId() {return productId;}

    /**
     @return the product name
     */
    public String getProductName() {return productName;}

    /**
     @return the product price
     */
    public double getProductPrice() {return productPrice;}

    /**
     @return the product image
     */
    public String getImagePath() {return imagePath;}


    /**
     Displays the productId.
      @param productId the product id
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     Displays the prouductName.
     @param productName the product name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     Displays the productPrice.
     @param productPrice the product price
     */
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    /**
     Displays the imagePath.
     * @param imagePath
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
