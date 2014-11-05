/**
 * Created by melmulcahy on 11/5/14.
 */
import java.sql.*;

public class DatabaseExample {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/Psychology_Software";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            //Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //Execute a queries
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT Username, Fname, Lname, Password FROM Professor_Table";
            ResultSet rs = stmt.executeQuery(sql);

            //Extract data from result set
            //Professor Table
            while(rs.next()){
                //Retrieve by column name
                String user  = rs.getString("Username");
                String fn = rs.getString("Fname");
                String ln = rs.getString("Lname");
                String pw = rs.getString("Password");

                //Display values
                System.out.println("Prof- username: " + user + " FName: " + fn + " Lname: " + ln + " Pass: " + pw);
            }
            //Student Table
            sql = "SELECT StudentID, PassageNum, TimePassage FROM Student_Table";
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                //Retrieve by column name
                int sID  = rs.getInt("StudentID");
                int passageNum = rs.getInt("PassageNum");
                Time tPassage = rs.getTime("TimePassage");

                //Display values
                System.out.println("Student- ID: " + sID + " PassNum: " + passageNum + " Time: " + tPassage);
            }
            //Passage Table
            sql = "SELECT PassageID, Passage FROM Passage_Table";
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                //Retrieve by column name
                int passageID  = rs.getInt("PassageID");
                String passage = rs.getString("Passage");

                //Display values
                System.out.println("Passage- ID: " + passageID + " Content: " + passage);
            }
            //SMS Table
            sql = "SELECT SMSID, Line, Message, PassageNum FROM SMS_Table";
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                //Retrieve by column name
                int smsID  = rs.getInt("SMSID");
                int line  = rs.getInt("Line");
                String message = rs.getString("Message");
                int passageNum = rs.getInt("PassageNum");


                //Display values
                System.out.println("SMS- ID: " + smsID + " Line: " + line+ " Message: " + message+ " Passage:" +passageNum);
            }
            //Quiz Table
            sql = "SELECT QuizID, AnswerKey, PassageNum FROM Quiz_Table";
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                //Retrieve by column name
                int quizID  = rs.getInt("QuizID");
                String answerKey  = rs.getString("AnswerKey");
                int passageNum = rs.getInt("PassageNum");


                //Display values
                System.out.println("Quiz- ID: " + quizID + " Answers: " + answerKey+ " Passage: " +passageNum);
            }
            //Update Example
            //sql ="UPDATE Answer_Table SET answer = 'Answer3' WHERE AnswerID=3";
            //stmt.executeUpdate(sql);

            //Question/Answer Table
            sql = "SELECT Question FROM Question_Table WHERE QuestionID = 1";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                String question = rs.getString("Question");
                System.out.println(question);
            }
            sql = "SELECT Answer FROM Answer_Table WHERE QuestionNum = 1";
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                //Retrieve by column name
                String answer = rs.getString("Answer");
                 //Display values
                System.out.println(answer);
            }



            // Clean-up environment
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
        System.out.println("Goodbye!");
    }//end main
}//end FirstExample