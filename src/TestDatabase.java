import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

public class TestDatabase {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/esmdDB";
        String username = "root";
        String password = "@Emilia20";

        try (Connection conn = DriverManager.getConnection(url, username, password)){
            System.out.println("Connected to database");

            String sql = "SELECT ProductId, ProductName, ProductPrice FROM Product";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                int id = rs.getInt("ProductId");
                String name = rs.getString("ProductName");
                double price = rs.getDouble("ProductPrice");
                System.out.println(id + " " + name + " " + "$" + price);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
