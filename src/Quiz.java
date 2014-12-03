import java.io.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Quiz {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/Psychology_Software";
    static final String USER = "root";
    static final String PASS = "";

    private String answerkey;
    private ArrayList<Question> questions = new ArrayList<Question>();
    private int quizID;
    private boolean submitted = false;
    private boolean saved = false;

    //creates a Quiz object
    public Quiz()
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
            sql = "SELECT AnswerKey FROM Quiz_Table WHERE QuizID = 1 ";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                //Retrieve by column name
                quizID  = 1;
                answerkey  = rs.getString("AnswerKey");
            }

            //populate question arraylist
            sql = "SELECT QuestionID from Question_Table WHERE QuizNum = " + this.quizID;
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                Question q = new Question(rs.getInt("QuestionID"));
                this.questions.add(q);
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

    //selects a question from the Arraylist to be edited.
    public void editQuestion(int choice, String question)
    {
        Question q = questions.get(choice);
        q.editQuestion(question);
    }

    public void addQuestion(String question)
    {
        int quesID;
        if(questions.size() > 0)
            quesID = questions.get(questions.size()-1).getQuestionID() + 1;
        else
            quesID = 1;
        Question q = new Question(quesID,question);
        questions.add(q);
    }

    public void deleteQuestion(int choice)
    {
        Question q = questions.get(choice);
        int qID = q.getQuestionID();
        Connection conn = null;
        Statement stmt = null;
        try{
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            //deletes answer from table.
            String sql = "DELETE FROM Question_Table WHERE QuestionID =" + qID;
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
        questions.remove(choice);

    }

    //update/change the answerkey.
    public void editAnswerKey(String akey)
    {
        answerkey = akey;
    }

    // REPLACED view()
    // returns the questions in order to be displayed on the screen.
    public ArrayList<Question> getQuestions()
    {
        return questions;
    }

    //returns the answer key to be used to check for correctness.
    public String getAnswerkey()
    {
        return answerkey;
    }

    //For the student to submit their solutions to be stored in the database.
    public void submit(int question, int answer, int studID)
    {

        if(submitted = false) {
            Connection conn = null;
            Statement stmt = null;
            try {
                //Register JDBC driver
                Class.forName(JDBC_DRIVER);

                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                stmt = conn.createStatement();
                String sql;

                //Quiz Table
                sql = "INSERT INTO StudentAnswer_Table VALUES StudID = " +studID +", QuestionID = " +
                        questions.get(question).getQuestionID() + ", Answer = " + answer;
                stmt.executeUpdate(sql);

                stmt.close();
                conn.close();
                submitted = true;
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

    //For the professor to save edits to answer key.
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
                sql = "UPDATE Quiz_Table SET AnswerKey = " + answerkey + "WHERE QuizID =" + quizID;
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

    public String toString()
    {
        return "QuizID: " + quizID + " AnswerKey: " + answerkey + "Question 1: "+ questions.get(0);
    }


}
