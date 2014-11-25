import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.awt.BorderLayout;  
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
/**
 * Created by melmulcahy on 11/9/14.
 */
public class Student extends User implements ActionListener {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/Psychology_Software";
    static final String USER = "root";
    static final String PASS = "";

    ArrayList timeSMS = new ArrayList();
    int passage;
    Time time;
    int idNumber;
    boolean finishedReading = false;
    boolean answeredQuestion = false;
    boolean answeredSMS = false;

    JFrame pframe = new JFrame("Start Passage");
    JFrame qframe = new JFrame("answerQuiz");
    JFrame sframe = new JFrame("answerSMS");
    JFrame passframe = new JFrame("Passage");

    JScrollPane scroll = new JScrollPane();

    JTextField tf = new JTextField("Text Here");

    JPanel passagePanel = new JPanel();
    JPanel quizPanel = new JPanel();
    JButton submit = new JButton("Submit");
    JButton read = new JButton("Read");
    JButton takeQuiz = new JButton("Take quiz");

    Quiz q = new Quiz();
    ArrayList <Question> questions = q.getQuestions();
    int numQuestions = questions.size();
    int curr = 0;


    public Student(int id, int passageNum)
    {
        this.idNumber = id;
        this.passage = passageNum;

        Connection conn = null;
        Statement stmt = null;
        try{
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            //Quiz Table
            sql = "INSERT INTO Student_Table (StudentID, PassageNum)  VALUES (" + id + ", " + passageNum +")";
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
            viewPassage();
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

    public void viewPassage() {

        pframe.setVisible(true);
        pframe.setSize(500,200);
        pframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        pframe.add(panel);
        read = new JButton("Read Passage");
        panel.add(read);
        read.addActionListener(this);

    }

      public void answerSMS()
    {
        
        

    }


    public void answerQuiz()
    {
        qframe.setLocationRelativeTo(null);
        qframe.pack();
        qframe.setSize(500,500);
        qframe.add(quizPanel);
        quizPanel.removeAll();

        JLabel currQues = new JLabel();
        Question currentQuestion = questions.get(curr);
        currQues.setText(currentQuestion.getQuestion());

        ArrayList <Answer> answers = currentQuestion.getAnswers();
        int size = answers.size();

        JRadioButton[] buttons = new JRadioButton[size];
        ButtonGroup radioButtons = new ButtonGroup();

        for(int i = 0; i < size; i++)
        {
            buttons[i] = new JRadioButton(answers.get(i).getAnswer());
            radioButtons.add(buttons[i]);
            quizPanel.add(buttons[i]);
        }

        quizPanel.setLayout(new BoxLayout(quizPanel,BoxLayout.LINE_AXIS));
        quizPanel.add(currQues);
        submit.setSize(20,20);
        quizPanel.add(submit);
        submit.addActionListener(this);
        qframe.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {

        //displays passage
        if(e.getActionCommand().equals("Read Passage"))
        {
            Passage p = new Passage(this.passage);

            pframe.dispose();
            passframe.setVisible(true);
            passframe.setSize(500, 500);
            passframe.add(passagePanel, BorderLayout.CENTER);
            takeQuiz.addActionListener(this);
            passagePanel.add(takeQuiz);
            JTextArea passage = new JTextArea(5,20);

            passage.setEditable(false);
            passage.setText(p.viewPassage());

            passage.setLineWrap(true);
            passage.setWrapStyleWord(true);
            passagePanel.add(passage);

            JScrollPane scroll = new JScrollPane (passage);

            scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
            passagePanel.add(scroll);
            scroll.setVisible(true);
          
            // Assuming the below line of code to be connected to the database.
            
        }
        else if(e.getActionCommand().equals("Submit"))
        {
            qframe.dispose();
            curr++;
            JOptionPane.showMessageDialog(qframe,"Answers Submitted!","Confirmation",JOptionPane.PLAIN_MESSAGE);
            Runner.mainFrame.setVisible(true);
            if(curr < numQuestions)
                answerQuiz();
        }
        else if(e.getActionCommand().equals("Take quiz"))
        {
            this.finishedReading = true;
            //update time here//
            passframe.dispose();
            answerQuiz();
        }
    }

}
