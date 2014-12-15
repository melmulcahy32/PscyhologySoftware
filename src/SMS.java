import java.sql.*;

public class SMS
{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/Psychology_Software";
    static final String USER = "root";
    static final String PASS = "";

    private int line;
    private String message;
    private boolean smsAnswered = false;
    private boolean saved = true;
    private int smsID;

    //retrives specific SMS from database
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

    //creates a new SMS in the database.
    public SMS(int smsid, String message, int line, int passage)
    {
        this.smsID = smsid;
        this.message = message;
        this.line = line;

        Connection conn = null;
        Statement stmt = null;
        try{
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            sql = "INSERT INTO SMS_Table (SMSID,Message,Line,PassageNum) VALUES (" +
                    this.smsID + ",'"+ this.message+"'," + this.line+"," +passage+")";
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
    }

    //stores student's responses into the database.
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

    //saves professor's changes to the database.
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

    //returns message
    public String getMessage()
    {
        return message;
    }

    //allows the professor to edit the SMS's message and line num.
    public void editSMS(String message, int line)
    {
        this.message = message;
        this.line = line;
        saved = false;
    }

    //returns line number.
    public int getLine()
    {
        return line;
    }

}
