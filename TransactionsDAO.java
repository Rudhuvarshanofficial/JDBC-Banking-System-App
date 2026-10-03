
import java.sql.*;
import java.time.LocalDate;
public class TransactionsDAO {
    public static void updateStatus(int acc,int amt , String service,LocalDate date) throws SQLException{
        String query = "insert into transactions(acc,amt,operation,transDate) values(?,?,?,?)";
        Connection con = BankingDAO.getConnection();
        PreparedStatement pr = con.prepareStatement(query);
        pr.setInt(1, acc);
        pr.setInt(2, amt);
        pr.setString(3, service);
        //java.sql.Date sqlDate = 
        pr.setDate(4, java.sql.Date.valueOf(date));
        pr.executeUpdate();
    }


    public static void getMini(int acc) throws SQLException{
        String query= "select acc,amt,operation,transDate from transactions where acc = " + acc + ";" ;
        Connection con = BankingDAO.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        //rs.next();
        System.out.println("Acc | Amt |   Type   |  Date  ");
        while(rs.next()){
            System.out.println("  " + rs.getInt(1) + " | " + rs.getInt(2) + " | "+ rs.getString(3) + " | "+ rs.getDate(4) );
        }

    }
}
