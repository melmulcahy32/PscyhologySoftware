import java.sql.*;

public class SMS
{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/Psychology_Software";
    static final String USER = "root";
    static final String PASS = "";

    private int line = 8;
    private String message = "Hello";
    private boolean smsAnswered = false;
    private boolean saved = true;
    private int smsID;
    public SMS(int smsid)
    {
        this.smsID = smsid;

        Connection conn = null;
        Statement stmt = null;
        try{
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            //Quiz Table
            sql = "SELECT Message, Line FROM SMS_Table WHERE SMSID =" + smsID;
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                //Retrieve by column name
                this.message = rs.getString("Message");
                this.line = rs.getInt("Line");
            }

            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
    }

    public void submit(int studID, Time time, String response)
    {
        if(smsAnswered = false) {
            Connection conn = null;
            Statement stmt = null;
            try {
                //Register JDBC driver
                Class.forName(JDBC_DRIVER);

                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                stmt = conn.createStatement();
                String sql;

                //Quiz Table
                sql = "INSERT INTO StudentSMS_Table VALUES StudID = " +studID +", SMSID = " +
                    this.smsID + ", SMSTime = " + time + ", Response = " + response;
                stmt.executeUpdate(sql);

                stmt.close();
                conn.close();
                smsAnswered = true;
            } catch (SQLException se) {
                //Handle errors for JDBC
                se.printStackTrace();
            } catch (Exception e) {
                //Handle errors for Class.forName
                e.printStackTrace();
            } finally {
                //finally block used to close resources
                try {
                    if (stmt != null)
                        stmt.close();
                } catch (SQLException se2) {
                }// nothing we can do
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }//end try
        }
    }

    public void save()
    {
        if(saved = false)
        {
            Connection conn = null;
            Statement stmt = null;
            try{
                //Register JDBC driver
                Class.forName(JDBC_DRIVER);

                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                stmt = conn.createStatement();
                String sql;

                //Quiz Table
                sql = "UPDATE SMS_Table SET Message = " + message + ", Line = " +
                        line + "WHERE SMSID = " + smsID;
                ResultSet rs = stmt.executeQuery(sql);

                rs.close();
                stmt.close();
                conn.close();
                System.out.println(this.toString());
            }catch(SQLException se){
                //Handle errors for JDBC
                se.printStackTrace();
            }catch(Exception e){
                //Handle errors for Class.forName
                e.printStackTrace();
            }finally{
                //finally block used to close resources
                try{
                    if(stmt!=null)
                        stmt.close();
                }catch(SQLException se2){
                }// nothing we can do
                try{
                    if(conn!=null)
                        conn.close();
                }catch(SQLException se){
                    se.printStackTrace();
                }//end finally try
            }//end try
            saved = true;
        }
    }

    public String getMessage()
    {
        return message;
    }

    public void editSMS(String message, int line)
    {
        this.message = message;
        this.line = line;
    }

    public int getLine()
    {
        return line;
    }

    public String toString()
    {
        String s = message + line;
        return s;
    }
}
