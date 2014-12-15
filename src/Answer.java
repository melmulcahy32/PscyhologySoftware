import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Answer {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/Psychology_Software";
    static final String USER = "root";
    static final String PASS = "";

    private String answer;
    private int answerID;
    private int type;
    private boolean saved;

    //retrieves specific answer from the database.
    public Answer (int answerID)
    {
        this.answerID = answerID;

        Connection conn = null;
        Statement stmt = null;
        try{
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            //Answer_Table
            sql = "SELECT Answer, AnswerType FROM Answer_Table WHERE AnswerID =" + answerID;
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                //Retrieve by column name
                this.answer = rs.getString("Answer");
                this.type = rs.getInt(("AnswerType"));
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

    //creates new answer in the database
    public Answer (String answer, int type, int questionNum)
    {
        this.answer = answer;
        this.type = type;

        Connection conn = null;
        Statement stmt = null;
        try{
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            sql = "SELECT MAX(AnswerID) FROM Answer_Table";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();

            int aID = rs.getInt("MAX(AnswerID)");
            this.answerID = aID+1;

            //Answer_Table
            sql = "INSERT INTO Answer_Table (AnswerID, Answer, AnswerType, QuestionNum) VALUES (" +
                    this.answerID + ", '" + this.answer + "', " + this.type + ", " + questionNum+")";
            stmt.executeUpdate(sql);

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

    //returns answerID
    public int getAnswerID()
    {
        return answerID;
    }

    //returns the answer text.
    public String getAnswer()
    {
        return answer;
    }

    //returns the answer type. 0 = fill in the blank, 1 = multiple choice.
    public int getType()
    {
        return type;
    }

    //edits the answer text and/or type.
    public void editAnswer(String answer, int type)
    {
        saved = false;
        this.answer = answer;
        this.type = type;
        //save to the database
    }

    //saves changes to the text to the database.
    public void save()
    {
        if(saved = false){
            Connection conn = null;
            Statement stmt = null;
            try{
                //Register JDBC driver
                Class.forName(JDBC_DRIVER);

                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                stmt = conn.createStatement();
                String sql ="UPDATE Answer_Table SET Answer = "+ this.answer + ", AnswerType =" +type +
                         " WHERE AnswerID=" + this.answerID;
                //stmt.executeUpdate(sql);
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
}
