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

            //Quiz Table
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
    public Answer (int answerID, String answer, int type, int questionNum)
    {
        this.answerID = answerID;
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

            //Quiz Table
            sql = "INSERT INTO Answer_Table (AnswerID, Answer, AnswerType, QuestionNum) VALUES (" +
                    this.answerID + ", '" + this.answer + "', " + this.type + ", " + questionNum+")";
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

    public int getAnswerID()
    {
        return answerID;
    }

    //returns the answer text.
    public String getAnswer()
    {
        return answer;
    }

    //returns the answer type. 1 = fill in the blank, 0 = multiple choice.
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
    public boolean getSaved()
    {
        return saved;
    }

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
