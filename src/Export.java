import java.sql.*;
import java.util.Scanner;
import java.io.*;
public class Export
{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://sql2.freemysqlhosting.net/sql260287";
    static final String USER = "sql260287";
    static final String PASS = "uE6!gF6*";

    public Export()
    {
    
    }
    
    public void export()
    {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("data.xls", true)));
            Connection conn = null;
            Statement stmt = null;
            try {
                //Register JDBC driver
                Class.forName(JDBC_DRIVER);

                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                stmt = conn.createStatement();
                String sql;

                //Quiz Table
                sql = "SELECT StudentID, PassageNum, TimePassage FROM Student_Table";
                ResultSet rs = stmt.executeQuery(sql);

                out.println("Student Passage Times");
                out.println("StudID\tPassageNum\tTimePassage");
                while (rs.next()) {
                    out.println("" + rs.getInt("StudentID") + "\t" + rs.getInt("PassageNum") + "\t" + rs.getTime("TimePassage"));
                }

                sql = "SELECT StudID, QuestionID, Answer FROM StudentAnswer_Table";
                rs = stmt.executeQuery(sql);
                out.println("Student Answers to Quiz");
                out.println("StudID\tQuestionID\tAnswer");
                while (rs.next()) {
                    out.println("" + rs.getInt("StudID") + "\t" + rs.getInt("QuestionID") + "\t" + rs.getInt("Answer"));
                }

                out.println("Student SMS Responses");

                sql = "SELECT StudID, SMSID, SMSTime, Response FROM StudentSMS_Table";
                rs = stmt.executeQuery(sql);

                out.println("StudID\tSMSID\tSMSTime\tResponse");

                while (rs.next()) {
                    out.println("" + rs.getInt("StudID") + "\t" + rs.getInt("SMSID") + "\t" + rs.getTime("SMSTime") + "\t" + rs.getString("Response"));
                }

                out.close();
                rs.close();
                stmt.close();
                conn.close();
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
        catch (IOException e)
        {}
    }
}
