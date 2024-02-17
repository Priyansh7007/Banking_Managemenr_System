import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Main {
    public static void main(String[] args) throws Exception{
        String id = "103";
        String name = "Faizan";
        String Address = "Sonbhadra";

        Class.forName("com.mysql.cj.jdbc.Driver");

        // Create connection
        Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/mall","root","1234");

        // create Query
        String q = "insert into register values(?,?,?)";

        // Create statement
        PreparedStatement ps =  con.prepareStatement(q);
        ps.setString(1,id);
        ps.setString(2,name);
        ps.setString(3,Address);
        int i = ps.executeUpdate();

        if(i>0){
            System.out.println("Insert data Successfully......");
        }
        else {
            System.out.println("Some error....");
        }

        con.close();
    }
}