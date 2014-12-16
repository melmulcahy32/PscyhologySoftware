import java.sql.*;
import java.util.ArrayList;
public class Passage
{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://psychology-project.c8gnizp6tgr7.us-east-1.rds.amazonaws.com/Psychology_Software";

    //  Database credentials
    static final String USER = "username";
    static final String PASS = "password1";

    private int passageID;
    private boolean saved;
    String passage;
    ArrayList<SMS> smsList = new ArrayList<SMS>();

    //retrieves passage and associated SMS messages.
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

            //Passage_Table
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

    //returns passage content
    public String viewPassage()
    {
        return passage;
    }

    //updates the passage content.
    public void editPassage(String passage)
    {
      this.passage = passage;
        saved = false;
       
    }

    //returns passageID
    public int getPassageID()
    {return passageID;
    }

    //returns arraylist of SMS associated with this passage.
    public ArrayList<SMS> getSMSList()
    {return smsList;}

    //saves any changes to the database.
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
