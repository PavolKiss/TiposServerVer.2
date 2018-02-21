package TiposServer.db;

package sk.akademiasovy.tipos.server.db;

import sk.akademiasovy.tipos.server.User;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
