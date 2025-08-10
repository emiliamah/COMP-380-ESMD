import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class StoreApp extends Application {
    private final ProductService productService = new ProductService();
    private final CartService cartService = new CartService();
    private final ListView<Product> productListView = new ListView<>();
    private final TextArea cartArea = new TextArea();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Online Store");

        // left: products
        productListView.getItems().setAll(productService.getAllProducts());
        Button addToCartBtn = new Button("Add to Cart");

        // right: cart + total + actions
        TextArea cartArea = this.cartArea;
        Label totalLabel = new Label("Total: $0.00");
        Button removeBtn = new Button("Remove Selected");
        Button clearBtn  = new Button("Clear Cart");
        Button checkoutBtn = new Button("Checkout");

        addToCartBtn.setOnAction(e -> {
            Product selected = productListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                cartService.addToCart(selected);
                updateCartDisplay();
                totalLabel.setText(String.format("Total: $%.2f", cartService.getTotalPrice()));
            }
        });

        removeBtn.setOnAction(e -> {
            Product selected = productListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                cartService.removeFromCart(selected);
                updateCartDisplay();
                totalLabel.setText(String.format("Total: $%.2f", cartService.getTotalPrice()));
            }
        });

        clearBtn.setOnAction(e -> {
            cartService.clearCart();
            updateCartDisplay();
            totalLabel.setText("Total: $0.00");
        });

        checkoutBtn.setOnAction(e -> showCheckoutDialog(primaryStage));

        cartArea.setEditable(false);

        VBox leftPane = new VBox(10, new Label("Products:"), productListView, addToCartBtn);
        VBox rightPane = new VBox(10,
                new Label("Cart:"), cartArea, totalLabel,
                new HBox(10, removeBtn, clearBtn, checkoutBtn)
        );
        HBox root = new HBox(20, leftPane, rightPane);
        root.setStyle("-fx-padding: 20;");

        primaryStage.setScene(new Scene(root, 700, 420));
        primaryStage.show();
    }

    private void updateCartDisplay() {
        StringBuilder sb = new StringBuilder();
        cartService.getCartItems().forEach((p, qty) ->
                sb.append(p.getName())
                        .append("  x").append(qty)
                        .append("  @ $").append(String.format("%.2f", p.getPrice()))
                        .append("  = $").append(String.format("%.2f", p.getPrice() * qty))
                        .append("\n")
        );
        cartArea.setText(sb.toString());
    }

    private void showCheckoutDialog(Stage owner) {
        TextField name = new TextField();     name.setPromptText("Full name");
        TextField email = new TextField();    email.setPromptText("Email");
        TextField card = new TextField();     card.setPromptText("Card number");
        TextField cvv  = new TextField();     cvv.setPromptText("CVV");
        TextField exp  = new TextField();     exp.setPromptText("MM/YY");

        VBox form = new VBox(8, new Label("Checkout"), name, email, card, cvv, exp);
        Button submit = new Button("Submit");
        Label note = new Label("(Demo only — no real payment)");
        VBox box = new VBox(10, form, submit, note);
        box.setStyle("-fx-padding: 16;");

        Stage dlg = new Stage();
        dlg.initOwner(owner);
        dlg.setTitle("Checkout");
        dlg.setScene(new Scene(box, 320, 300));
        dlg.show();

        submit.setOnAction(e -> {
            if (name.getText().isEmpty() || email.getText().isEmpty()) {
                note.setText("Please fill required fields.");
                return;
            }
            note.setText("Order placed! Total: $" + String.format("%.2f", cartService.getTotalPrice()));
            cartService.clearCart();
            updateCartDisplay();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}