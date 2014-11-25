/**
 * Created by Mike on 11/16/14.
 */
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.*;

public class Question {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/Psychology_Software";
    static final String USER = "root";
    static final String PASS = "";

    private String question;
    private int questionID;
    private ArrayList<Answer> answers = new ArrayList<Answer>();
    private boolean saved;

    //creates new Question object.
    public Question (int questionID)
    {
        this.questionID = questionID;

        Connection conn = null;
        Statement stmt = null;
        try{
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            //Quiz Table
            sql = "SELECT Question FROM Question_Table WHERE QuestionID =" + questionID;
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                //Retrieve by column name
                this.question = rs.getString("Question");
            }

            //populate answer arraylist
            sql = "SELECT AnswerID FROM Answer_Table WHERE QuestionNum =" + questionID;
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                Answer a = new Answer(rs.getInt("AnswerID"));
                answers.add(a);
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

    public int getQuestionID()
    {
        return questionID;
    }
    //returns the question's text.
    public String getQuestion()
    {
        return question;
    }

    //returns an arraylist of the possible answers.
    public ArrayList<Answer> getAnswers()
    {
        return answers;
    }

    //for the professor to save changes.
    public void saved()
    {
        if(saved = false){
            Connection conn = null;
            Statement stmt = null;
            try{
                //Register JDBC driver
                Class.forName(JDBC_DRIVER);

                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                stmt = conn.createStatement();
                String sql = "UPDATE Question_Table SET Question = " + question + "WHERE QuestionID = " + questionID;
                ResultSet rs = stmt.executeQuery(sql);

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

            saved = true;
        }
    }

    //updates the question content.
    public void editQuestion(String question)
    {
        this.question = question;
        saved = false;
    }

    //adds a new answer to the arraylist.
    public void addAnswer(String answer, int type)
    {
        int lastID = answers.get(answers.size()-1).getAnswerID() + 1;
        Answer a = new Answer(lastID, answer, type, questionID);
        answers.add(a);
    }

    //removes an answer from the arraylist.
    public void deleteAnswer(int choice)
    {
        Answer a = answers.get(choice);
        int aID = a.getAnswerID();
        Connection conn = null;
        Statement stmt = null;
        try{
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            //deletes answer from table.
            String sql = "DELETE FROM Answer_Table WHERE AnswerID =" + aID;
            ResultSet rs = stmt.executeQuery(sql);

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
        answers.remove(choice);

    }

    //edits a particular answer.
    public void editAnswer(int choice, String answer, int type) {
        Answer a = answers.get(choice);
        a.editAnswer(answer, type);
    }
    public String toString()
    {
        return "\n" + question + "\n" + answers.get(0).getAnswer();
    }
}
