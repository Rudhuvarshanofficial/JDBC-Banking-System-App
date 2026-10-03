import java.sql.*;

public class BankingDAO {
    private static final String  url="jdbc:mysql://localhost:3306/busResv";
    private static final String uname="root";
    private static final String pass="sri555";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, uname, pass);
    }
}
