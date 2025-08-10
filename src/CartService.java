import java.util.HashMap;
import java.util.Map;

public class CartService
{
    private Map<Product, Integer> cartItems = new HashMap<>();

    public void addToCart(Product product)
    {
        cartItems.put(product, cartItems.getOrDefault(product, 0) + 1);
        System.out.println("Added to cart: " + product.getName());
    }

    public void removeFromCart(Product product)
    {
        if (cartItems.containsKey(product))
        {
            int quantity = cartItems.get(product);
            if (quantity > 1)
            {
                cartItems.put(product, quantity - 1);
            } else
            {
                cartItems.remove(product);
            }
            System.out.println("Removed from cart: " + product.getName());
        }
    }

    public Map<Product, Integer> getCartItems() {
        return cartItems;
    }

    public double getTotalPrice() {
        return cartItems.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public void clearCart() {
        cartItems.clear();
    }

    public int getItemCount() {
        return cartItems.values().stream().mapToInt(Integer::intValue).sum();
    }
}
