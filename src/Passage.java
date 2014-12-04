import java.sql.*;
import java.util.ArrayList;
public class Passage
{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://sql2.freemysqlhosting.net/sql260287";
    static final String USER = "sql260287";
    static final String PASS = "uE6!gF6*";

    private int passageID;
    private int lineNum;
    private boolean saved;
    String passage;
    ArrayList<SMS> smsList = new ArrayList<SMS>();
    public Passage( int passageID)
    {
        this.passageID = passageID;
        Connection conn = null;
        Statement stmt = null;
        try{
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            //Quiz Table
            sql = "SELECT Passage FROM Passage_Table WHERE PassageID =" + this.passageID;
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                //Retrieve by column name
                this.passage = rs.getString("Passage");
            }

            //populate SMS messages
            sql = "SELECT SMSID FROM SMS_Table WHERE PassageNum = " + this.passageID;
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                int smsid = rs.getInt("SMSID");
                SMS s = new SMS(smsid);
                smsList.add(s);
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
    
    public String viewPassage()
    {
        return passage;
    }
    
    public void editPassage(String passage)
    {
      this.passage = passage;
        saved = false;
       
    }

    public int getPassageID()
    {return passageID;
    }
    public int getLine()
    {
       return lineNum;
    }
    
    public void updateLine(int lineNum)
    {
      this.lineNum = lineNum;
    }
    
    public void getSMS(int lineNum)
    {
    
    }

    public ArrayList<SMS> getSMSList()
    {return smsList;}



    public void save()
    {
        if(!saved)
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
                sql = "UPDATE Passage_Table SET passage = '" + passage +
                        "' WHERE PassageID =" + passageID;
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
                saved = true;
            }//end try
        }
    }

}
