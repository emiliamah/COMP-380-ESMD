public class Main{
    public static void main(String[] args) {
        ProductDatabase db = new ProductDatabase();
        for(Product product : new ProductDatabase().getProduct()){
            System.out.println(product.getProductId() + " " + product.getProductName() + " " + product.getProductPrice());
        }
    }
}