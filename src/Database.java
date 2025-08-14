import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 Class: Database
 Date: 08-13-25
 Emilia Mahmoodi
 Singleton that connects to the MySQL database.

 */
public class Database {
    private static final Database instance = new Database();
    private static final String url = "jdbc:mysql://localhost:3306/esmdDB";
    private static final String username = "root";
    private static final String password = "@Emilia20";

    private Database() {}
    public static Database getInstance() {
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
