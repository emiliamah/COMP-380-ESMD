import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import java.sql.*;
import java.util.Map;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        //

        CartService cartService = new CartService();

        // need to replace with database result
        VBox productView = new VBox(15);
        productView.setPadding(new Insets(20));
        Product[] demo = new Product[] {
                new Product(1, "White T-Shirt", 19.99, "images/white.jpg"),
                new Product(2, "Black T-Shirt", 21.99, "images/black.jpg"),
                new Product(3, "Graphic T-Shirt", 24.50, "images/graphic.jpg"),
                new Product(4, "Striped T-Shirt", 18.75, "images/striped.jpg"),
        };
        for (Product p : demo) {
            productView.getChildren().add(createProductCard(p, cartService));
        }
        ScrollPane productScroll = new ScrollPane(productView);
        productScroll.setFitToWidth(true);

        // Cart View
        VBox cartView = new VBox(15);
        cartView.setPadding(new Insets(20));
        ScrollPane cartScroll = new ScrollPane(cartView);
        cartScroll.setFitToWidth(true);

        // Top buttons
        Button viewCartBtn = new Button("View Cart");
        Button backToShopBtn = new Button("Back to Shop");

        HBox topBar = new HBox(10, viewCartBtn);
        topBar.setPadding(new Insets(10));
        topBar.setStyle("-fx-background-color: #eeeeee;");

        // Layout manager
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(productScroll);

        // Button actions
        viewCartBtn.setOnAction(e -> {
            cartView.getChildren().clear();

            for (Map.Entry<Product, Integer> entry : cartService.getCartItems().entrySet()) {
                Product p = entry.getKey();
                int quantity = entry.getValue();

                HBox itemRow = new HBox(10);
                itemRow.setPadding(new Insets(5));

                Label label = new Label(
                        p.getProductName() + " x" + quantity + " - $" +
                                String.format("%.2f", p.getProductPrice() * quantity)
                );
                label.setFont(Font.font("Arial", 14));

                Button removeBtn = new Button("Remove");
                removeBtn.setOnAction(ev -> {
                    cartService.removeFromCart(p);
                    viewCartBtn.fire(); // Refresh view
                });

                itemRow.getChildren().addAll(label, removeBtn);
                cartView.getChildren().add(itemRow);
            }

            // Total price
            Label totalLabel = new Label("Total: $" + String.format("%.2f", cartService.getTotalPrice()));
            totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            totalLabel.setPadding(new Insets(10, 0, 0, 0));

            Button checkoutBtn = new Button("Checkout");
            checkoutBtn.setOnAction(ev -> {
                root.setTop(null);
                root.setCenter(createCheckoutForm(root, topBar, productScroll, cartService));
            });

            cartView.getChildren().addAll(totalLabel, checkoutBtn);

            topBar.getChildren().setAll(backToShopBtn);
            root.setCenter(cartScroll);
        });

        backToShopBtn.setOnAction(e -> {
            topBar.getChildren().setAll(viewCartBtn);
            root.setCenter(productScroll);
        });

        // Final scene
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Clothing Store");
        primaryStage.show();
    }

    private HBox createProductCard(Product product, CartService cartService) {
        HBox card = new HBox(15);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color: #ccc; -fx-border-radius: 8; -fx-background-radius: 8; -fx-background-color: #f9f9f9;");

        ImageView imageView;
        try {
            Image img = new Image(product.getImagePath(), 100, 100, true, true);
            imageView = new ImageView(img);
        } catch (Exception e) {
            imageView = new ImageView(); // fallback
        }

        VBox details = new VBox(5);
        Label name = new Label(product.getProductName());
        name.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label price = new Label(String.format("$%.2f", product.getProductPrice()));
        price.setFont(Font.font("Arial", 14));

        Button addToCartBtn = new Button("Add to Cart");
        addToCartBtn.setOnAction(e -> cartService.addToCart(product));

        details.getChildren().addAll(name, price, addToCartBtn);
        card.getChildren().addAll(imageView, details);

        return card;
    }

    private VBox createCheckoutForm(BorderPane root, HBox topBar, ScrollPane productScroll, CartService cartService) {
        VBox form = new VBox(10);
        form.setPadding(new Insets(20));

        Label title = new Label("Checkout");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        javafx.scene.control.TextField firstNameField = new javafx.scene.control.TextField();
        firstNameField.setPromptText("First Name");

        javafx.scene.control.TextField lastNameField = new javafx.scene.control.TextField();
        lastNameField.setPromptText("Last Name");

        javafx.scene.control.TextField emailField = new javafx.scene.control.TextField();
        emailField.setPromptText("Email Address");

        javafx.scene.control.TextField cardNumberField = new javafx.scene.control.TextField();
        cardNumberField.setPromptText("Credit Card Number");

        javafx.scene.control.TextField cvvField = new javafx.scene.control.TextField();
        cvvField.setPromptText("CVV");

        javafx.scene.control.TextField expDateField = new javafx.scene.control.TextField();
        expDateField.setPromptText("Expiration Date (MM/YY)");

        Button submitBtn = new Button("Submit Order");
        submitBtn.setOnAction(e -> {
            String firstName = firstNameField.getText().trim();
            String lastName  = lastNameField.getText().trim();
            String email     = emailField.getText().trim();

            // ✅ Save order + items to MySQL
            try {
                long orderId = saveOrderToDatabase(firstName, lastName, email, cartService);
                System.out.println("✅ Order saved with ID: " + orderId);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Clear the cart after order
            cartService.clearCart();

            // Confirmation screen
            VBox confirmationView = new VBox(15);
            confirmationView.setPadding(new Insets(20));
            Label message = new Label("✅ Order placed successfully!");
            message.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            Button backBtn = new Button("Back to Shop");
            backBtn.setOnAction(ev -> {
                topBar.getChildren().setAll(new Button("View Cart")); // quick reset
                root.setTop(topBar);
                root.setCenter(productScroll);
            });

            confirmationView.getChildren().addAll(message, backBtn);
            root.setCenter(confirmationView);
        });

        form.getChildren().addAll(
                title,
                firstNameField,
                lastNameField,
                emailField,
                cardNumberField,
                cvvField,
                expDateField,
                submitBtn
        );

        return form;
    }

   //

    private long saveOrderToDatabase(String firstName,
                                     String lastName,
                                     String email,
                                     CartService cartService) throws SQLException {
        double total = cartService.getTotalPrice();

        String insertOrderSql =
                "INSERT INTO Orders (FirstName, LastName, Email, Total) VALUES (?, ?, ?, ?)";
        String insertItemSql =
                "INSERT INTO OrderItems (OrderId, ProductId, Quantity, UnitPrice) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            long orderId;

            try (PreparedStatement orderPs =
                         conn.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS)) {
                orderPs.setString(1, firstName);
                orderPs.setString(2, lastName);
                orderPs.setString(3, email);
                orderPs.setDouble(4, total);
                orderPs.executeUpdate();

                try (ResultSet keys = orderPs.getGeneratedKeys()) {
                    if (!keys.next()) {
                        throw new SQLException("Failed to obtain OrderId.");
                    }
                    orderId = keys.getLong(1);
                }
            }

            try (PreparedStatement itemPs = conn.prepareStatement(insertItemSql)) {
                for (Map.Entry<Product, Integer> entry : cartService.getCartItems().entrySet()) {
                    Product p = entry.getKey();
                    int qty = entry.getValue();

                    itemPs.setLong(1, orderId);
                    itemPs.setInt(2, p.getProductId());
                    itemPs.setInt(3, qty);
                    itemPs.setDouble(4, p.getProductPrice());
                    itemPs.addBatch();
                }
                itemPs.executeBatch();
            }

            conn.commit();
            return orderId;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
