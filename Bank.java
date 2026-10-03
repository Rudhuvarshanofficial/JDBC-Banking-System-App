import java.sql.*;
import java.time.LocalDate;
public class Bank {
    public boolean isExits(int acc) throws SQLException{
        String query = "select acc from customer where acc=?;";
        Connection con = BankingDAO.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, acc);
        ResultSet rs = st.executeQuery();
        int val=0;
        if(rs.next()){
            val = rs.getInt(1);
        }
        
        return val>0 ? true : false;
    }

    public boolean checkPass(int pass,int acc) throws SQLException{
        String query = "select pass from customer where acc=" + acc
         + ";";
        Connection con = BankingDAO.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        //st.setInt(1, acc);
        ResultSet rs = st.executeQuery(query);
        rs.next();
        int checker = rs.getInt(1);
        return pass == checker ? true : false;
        
    }

    public String getName(int acc) throws SQLException{
        String query = "select cName from customer where acc=" + acc
         + ";";
        Connection con = BankingDAO.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery(query);
        rs.next();
        return rs.getString(1);
    }

    public void displayBalance(int acc) throws SQLException{
        String query = "select amt from customer where acc=" + acc
         + ";";
        Connection con = BankingDAO.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery(query);
        rs.next();
        int i =rs.getInt(1);
        System.out.println("Balance : " + i);
        // String service = "Withdraw";
        // LocalDate date = LocalDate.now();
        // .updateStatus(i, acc, service, date);
    }

    public boolean checkWithdrawl(int amt,int acc) throws SQLException{
        String query = "select amt from customer where acc=" + acc
         + ";";
        Connection con = BankingDAO.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery(query);
        rs.next();
        int balance = rs.getInt(1);
        return amt<=balance ;
    }

    public void withdrawl(int amt,int acc) throws SQLException{
        LocalDate date = LocalDate.now();
        String service ="Withdraw";
        String query = "select amt from customer where acc=" + acc
         + ";";
        Connection con = BankingDAO.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery(query);
        rs.next();
        int balance = rs.getInt(1);
        int remaining = balance - amt;
        String query2 = "update customer set amt= ? where acc= ? ;";
        PreparedStatement pr = con.prepareStatement(query2);
        pr.setInt(1, remaining);
        pr.setInt(2, acc);
        pr.executeUpdate();
        System.out.println("$" + amt + " successfully withdrawled");
        System.out.println("Thanks for visiting our bank!!");
        TransactionsDAO.updateStatus(acc,amt,service,date);
    }

    public boolean checkBalance(int acc,int amtTo) throws SQLException{
        String query = "select amt from customer where acc=" + acc
         + ";";
        Connection con = BankingDAO.getConnection();
        PreparedStatement st = con.prepareStatement(query);
        ResultSet rs = st.executeQuery(query);
        rs.next();
        int i =rs.getInt(1);
        return amtTo<=i;
    }

    public void accTransfer(int acc , int acc2 ,int amtTo,int balance) throws SQLException{
        Bank bank = new Bank();
        LocalDate date = LocalDate.now();
        int sender =0,receiver =0;

        Connection con = BankingDAO.getConnection();
        con.setAutoCommit(false);
        String query2 = "update customer set amt = ? where acc = ?;";
        PreparedStatement pr = con.prepareStatement(query2);
        pr.setInt(1, balance-amtTo);
        pr.setInt(2, acc);
        sender = pr.executeUpdate();

        
        int balance2 = bank.getBalance(acc2);
        String query3 = "update customer set amt = ? where acc = ?;";
        PreparedStatement pr1 = con.prepareStatement(query3);
        pr1.setInt(1, balance2+amtTo);
        pr1.setInt(2, acc2);
        receiver=pr1.executeUpdate();

        if(sender==1 && receiver==1 ){
            con.commit();
            TransactionsDAO.updateStatus(acc, amtTo, "Acc Transfer", date);
            TransactionsDAO.updateStatus(acc2, amtTo, "From AT", date);
            System.out.println("Amount transfered successfully");
        }
        else con.rollback();
    }

    public int getBalance(int acc) throws SQLException{
        String query = "select amt from customer where acc =" + acc + " ;";
        Connection con = BankingDAO.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        rs.next();
        int balance = rs.getInt(1);
        return balance;
    }

    public void deposit(int acc,int amt)throws SQLException{
        String query = "update customer set amt = ? where acc = ? ;";
        Connection con = BankingDAO.getConnection();
        con.setAutoCommit(false);
        PreparedStatement pr = con.prepareStatement(query);
        int total = new Bank().getBalance(acc) + amt;
        pr.setInt(1,total);
        pr.setInt(2, acc);
        int checker = pr.executeUpdate();
        if (checker>0){
            con.commit();
            System.out.println("Amount deposited successfully ");
        }
        else con.rollback();
    }


}
