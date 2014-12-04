import java.sql.*;

public class SMS
{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://sql2.freemysqlhosting.net/sql260287";
    static final String USER = "sql260287";
    static final String PASS = "uE6!gF6*";

    private int line;
    private String message;
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
        if(smsAnswered == false) {
            Connection conn = null;
            Statement stmt = null;
            try {
                //Register JDBC driver
                Class.forName(JDBC_DRIVER);

                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                stmt = conn.createStatement();
                String sql;

                //Quiz Table
                sql = "INSERT INTO StudentSMS_Table(StudID, SMSID, SMSTime, Response) VALUES (" +studID +"," +
                    this.smsID + ",'" + time + "', '" + response+"')";
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
        if(saved == false)
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
                sql = "UPDATE SMS_Table SET Message = '" + message + "', Line = '" +
                        line + "' WHERE SMSID = '" + smsID+"'";
                stmt.executeUpdate(sql);

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
        saved = false;
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
