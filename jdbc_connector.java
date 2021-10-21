import java.sql.*;

public class jdbc_connector {

    Connection con;
    jdbc_connector()
    {
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/sravan";
            con = DriverManager.getConnection(url,"root","password");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void printTable()
    {
        try {

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM student");
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while(rs.next())
            {
                for(int i = 1;i<=columnsNumber;i++)
                {
                    System.out.print(rs.getString(i)+" ");
                }
                System.out.println();
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void InsertIntoTable(int id, String fac, String section, String sub, int time, String week)
    {
        String query = "INSERT into TimeTable values(?,?,?,?,?,?)";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.setString(2, fac);
            stmt.setString(3, section);
            stmt.setString(4, sub);
            stmt.setInt(5, time);
            stmt.setString(6, week);
            stmt.executeUpdate();
            con.close();

        } catch (Exception e) {
            System.out.print(e);
        }
        
    }

    public void something()
    {
        System.out.println("this is printing");
    }


    public static void main(String[] args) {
        
        jdbc_connector js = new jdbc_connector();
        js.InsertIntoTable(1, "ramu", "sec1", "math", 1, "wednesday");
        
        try {
            js.con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
         

    }
}
