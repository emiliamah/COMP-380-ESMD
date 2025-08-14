import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class ProductDatabase {
    public List<Product> getProduct() {
        List<Product> product = new ArrayList<>();
        String query = "SELECT ProductId, ProductName, ProductPrice FROM Product";

        try(Connection connection = Database.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()){
                product.add(new Product(resultSet.getInt("ProductId"), resultSet.getString("ProductName"), resultSet.getDouble("ProductPrice")));

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return product;
    }
}
