package sk.akademiasovy.tipos.server.db;

import sk.akademiasovy.tipos.server.Draw_Number;
import sk.akademiasovy.tipos.server.Registration;
import sk.akademiasovy.tipos.server.Ticket;
import sk.akademiasovy.tipos.server.User;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by host on 20.2.2018.
 */
public class MySQL {
    private Connection conn;
    private String driver = "com.mysql.jdbc.Driver";
    private String url="jdbc:mysql://localhost:3306/tipos";
    private String username="root";
    private String password="";

    public User getUser(String username, String password){
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, this.username, this.password);

            String query = "SELECT * from users where login like ? and password like ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                User user=new User(rs.getString("firstname"),rs.getString("lastname"),rs.getString("login"),rs.getString("email"));
                query = "UPDATE tokens SET token=? WHERE idu=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, user.getToken());
                ps.setInt(2,rs.getInt("id"));

                ps.executeUpdate();
                System.out.println(ps);
                return user;
            }
            return null;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void logout( String token) {
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, this.username, this.password);

            String query = "UPDATE tokens SET token=\"\" where token like ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,token);
            System.out.println(ps);
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean checkIfEmailOrLoginExist(String login, String email) {
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, this.username, this.password);

            String query = "SELECT count(*) as num FROM users WHERE login like ? OR email like ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,login);
            ps.setString(2,email);
            ResultSet rs=ps.executeQuery();
            System.out.println(ps);

            rs.next();
            if(rs.getInt("num")==0)
                return false;  // email ani login neexistuju
            else
                return true;  // login alebo email uz existuje v databaze

        }catch(Exception e){
            e.printStackTrace();
        }

        return true;
    }

    public void insertNewUserIntoDb(Registration registration) {
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, this.username, this.password);
            String query = "INSERT INTO users(firstname, lastname, email, login, password) "+
                    " VALUES (?,?,?,?,?)";
            PreparedStatement ps= conn.prepareStatement(query);
            ps.setString(1,registration.firstname);
            ps.setString(2,registration.lastname);
            ps.setString(3,registration.email);
            ps.setString(4,registration.login);
            ps.setString(5,registration.password);
            ps.executeUpdate();


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean checkLogin(String login) {
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, this.username, this.password);

            String query = "SELECT * FROM users Where login like ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,login);
            ResultSet rs=ps.executeQuery();

            if(rs.next())
                return true;
            else
                return false;

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkToken(String token) {
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, this.username, this.password);

            String query = "SELECT * FROM tokens Where token like ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,token);
            ResultSet rs=ps.executeQuery();

            if(rs.next())
                return true;
            else
                return false;

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void insertBets(Ticket ticket) {
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, this.username, this.password);

            String query = "INSERT INTO bets (idu) SELECT id FROM users where login like ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1,ticket.login);
            System.out.println(ps);
            ps.executeUpdate();
            query="SELECT max(id) as max from bets where idu = (SELECT id FROM users where login like ?)";
            ps = conn.prepareStatement(query);
            ps.setString(1,ticket .login);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int id_bet=rs.getInt("max");
            query = "INSERT INTO bet_details(idb,bet1, bet2, bet3, bet4,bet5) VALUES (?,?,?,?,?,?)";
            ps= conn.prepareStatement(query);
            ps.setInt(1,id_bet);
            ps.setInt(2,ticket.bet1);
            ps.setInt(3,ticket.bet2);
            ps.setInt(4,ticket.bet3);
            ps.setInt(5,ticket.bet4);
            ps.setInt(6,ticket.bet5);
            ps.executeUpdate();


        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public Draw_Number getDrawNumbers(int id) {
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, this.username, this.password);

            String query = "SELECT * from draw_history where id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Draw_Number dn = new Draw_Number(rs.getInt("ball1"),rs.getInt("ball2"),rs.getInt("ball3"),rs.getInt("ball4"),
                        rs.getInt("ball5"), null);
                return dn;
            }
            else return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List getActualTickets(String username) {
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, this.username, this.password);

            String query = "SELECT * from bets "
                    +" INNER JOIN users ON users.id=bets.idu "
                    +" INNER JOIN bet_details ON bets.id=bet_details.idb "
                    +" WHERE login like ? AND draw_id IS NULL";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1,username);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            List<Ticket> list=new ArrayList<>();
            while(rs.next()){
                int bet1=rs.getInt("bet1");
                int bet2=rs.getInt("bet2");
                int bet3=rs.getInt("bet3");
                int bet4=rs.getInt("bet4");
                int bet5=rs.getInt("bet5");
                Ticket ticket=new Ticket(bet1, bet2,bet3,bet4,bet5);
                list.add(ticket);
                return list;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
