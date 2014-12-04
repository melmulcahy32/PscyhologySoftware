import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
public class Professor extends User implements ActionListener
{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://sql2.freemysqlhosting.net/sql260287";
    static final String USER = "sql260287";
    static final String PASS = "uE6!gF6*";

    JFrame frame = new JFrame("Answer Quiz");
    JFrame frame1 = new JFrame("Answer SMS");
    JFrame frame2 = new JFrame("Edit Quiz");
    JFrame frame3 = new JFrame("Edit Passage");
    JFrame frame5 = new JFrame("View Passage");
    JFrame frame6 = new JFrame("Dashboard");
    JFrame frame7 = new JFrame("User Settings");
    JFrame login;
    JFrame editQuestion = new JFrame("Edit Question");
    JFrame editSMSs = new JFrame("Edit SMS");

    JPanel panel = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();
    JPanel panel5 = new JPanel();
    JPanel eQues = new JPanel();

    JRadioButton j = new JRadioButton();
    JButton sPassage = new JButton("Save Passage");
    JButton sQuiz = new JButton("Edit Question");
    JButton sQues = new JButton("Save Question");
    JButton sSMS = new JButton("Edit SMS");
    JButton saveSMS = new JButton("Save SMS");
    JButton sUser = new JButton("Save User Settings");
    JButton addQues = new JButton("Add Question");
    JButton delQues = new JButton("Delete Question");
    JButton addAnswer = new JButton("Add Answer");
    JButton delAnswer = new JButton("Delete Answer");
    JButton addSMS = new JButton("Add SMS");
    JButton button1 = new JButton("Save");
    JButton button3 = new JButton("OK");
    JButton button4 = new JButton("Preview Quiz");
    JButton button5 = new JButton("Preview SMS");
    JButton button6 = new JButton("Submit");
    JButton eQuiz = new JButton("Edit Quiz");
    JButton ePassage = new JButton("Edit Passage");
    JButton eSMS = new JButton("Edit SMS");
    JButton eUser = new JButton("Edit User Settings");
    JButton logout = new JButton("Log Out");

    ButtonGroup qRadioButtons;
    JRadioButton[] rButtons;
    JTextField tf2 = new JTextField("Edited Text Here");
    JTextField userText = new JTextField();
    JPasswordField passwordText = new JPasswordField();

    JTextArea vpassage = new JTextArea(200,200);
    JTextArea epassage = new JTextArea(20,20);

    JTextField eQuestion = new JTextField();
    JTextField[] answers;
    JTextField edSMS = new JTextField();
    JTextField smsLine = new JTextField();

    ArrayList <Question> ques;
    ArrayList <Answer> ans;
    ArrayList <SMS> sms;
    StringBuilder sb = new StringBuilder();
    String text, test,answer, SMS, data;
    Question currQuestion;
    Passage p;
    Quiz q;
    SMS sm;

    int passageID;

    private String username;
    private String password;
    boolean loggedin = false;
    boolean savedSettings = false;

    //SMS sms = new SMS();
    public Professor ()
    {
        login = new JFrame("Log In");
        login.setLocationRelativeTo(null);
        login.setSize(300,170);
        login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel lpanel = new JPanel();
        login.add(lpanel);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10,10,80,25);
        lpanel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 10, 160, 25);
        lpanel.add(userText);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(10,40,80,25);
        lpanel.add(passLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 40, 160, 25);
        lpanel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setBounds(10, 80, 80, 25);
        lpanel.add(loginButton);

        login.setVisible(true);
    }
    public void viewPassage()
    {

    }

    public void viewDashboard() {
        frame6.setVisible(true);
        frame6.setSize(500, 200);
        frame6.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame6.add(panel5);
        panel5.add(ePassage);
        panel5.add(eQuiz);
        panel5.add(eSMS);
        panel5.add(eUser);
        panel5.add(logout);
        ePassage.addActionListener(this);
        eQuiz.addActionListener(this);
        eSMS.addActionListener(this);
        eUser.addActionListener(this);
        logout.addActionListener(this);
        sPassage.addActionListener(this);
        button1.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);
        button5.addActionListener(this);
        button6.addActionListener(this);
    }

    public void login(String user, String pass)
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
            sql = "SELECT Password FROM Professor_Table WHERE Username = '" + user +"'";
            ResultSet rs = stmt.executeQuery(sql);

            rs.next();
            String dPass = rs.getString("Password");

            if(dPass.equals(pass))
            {
                loggedin = true;
                this.username = user;
                this.password = pass;
                viewDashboard();
            }
            else {
                login.dispose();
                Runner.mainFrame.setVisible(true);
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

    public void logout()
    {

        loggedin = false;
        frame.dispose();
        frame1.dispose();
        frame2.dispose();
        frame3.dispose();
        frame5.dispose();
        frame6.dispose();
        frame7.dispose();
        Runner.mainFrame.setVisible(true);
    }

    public void editQuiz()
    {
        panel2.removeAll();
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel selQuestion = new JLabel("Please Select a Question to Edit:");
        panel2.add(selQuestion);
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        q = new Quiz();
        ques = q.getQuestions();
        rButtons = new JRadioButton[ques.size()];
        qRadioButtons = new ButtonGroup();

        for(int i = 0; i < ques.size(); i++)
        {
            rButtons[i] = new JRadioButton(ques.get(i).getQuestion());
            qRadioButtons.add(rButtons[i]);
            panel2.add(rButtons[i]);
        }

        frame2.setLocationRelativeTo(null);
        frame2.pack();
        frame2.setSize(500,500);
        JScrollPane jsp = new JScrollPane(panel2);

        frame2.add(jsp);
        sQuiz.setSize(20, 20);
        sQuiz.addActionListener(this);
        panel2.add(sQuiz);

        addQues.addActionListener(this);
        delQues.addActionListener(this);

        panel2.add(addQues);
        panel2.add(delQues);
        panel2.add(button4);
        frame2.setVisible(true);
    }
    public void editQuestion(Question q)
    {
        currQuestion = q;
        editQuestion.setLocationRelativeTo(null);
        editQuestion.pack();
        editQuestion.setSize(1000, 1000);
        eQues.setLayout(new BoxLayout(eQues, BoxLayout.Y_AXIS));
        eQuestion.setPreferredSize(new Dimension(750, 24));
        eQuestion.setText(q.getQuestion());
        eQuestion.setMaximumSize(eQuestion.getPreferredSize());
        eQuestion.setEditable(true);
        eQues.add(eQuestion);
        ans = q.getAnswers();

        answers = new JTextField[ans.size()];

        for(int i = 0; i < ans.size(); i++)
        {
            answers[i] = new JTextField(ans.get(i).getAnswer());
            answers[i].setPreferredSize(new Dimension(500,24));
            answers[i].setMaximumSize(answers[i].getPreferredSize());
            answers[i].setEditable(true);
            eQues.add(answers[i]);
        }

        addAnswer.addActionListener(this);
        delAnswer.addActionListener(this);
        sQues.addActionListener(this);
        eQues.add(addAnswer);
        eQues.add(delAnswer);
        eQues.add(sQues);

        JScrollPane jsp = new JScrollPane(eQues);

        editQuestion.add(jsp);
        editQuestion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editQuestion.setVisible(true);


    }

    public void editPassage(int passage)
    {
        panel3.removeAll();
        passageID = passage;
        p = new Passage(passage);
        panel3.setLayout(new BoxLayout(panel3,BoxLayout.Y_AXIS));
        vpassage.setText(p.viewPassage());
        vpassage.setEditable(true);
        frame3.setLocationRelativeTo(null);
        frame3.pack();
        frame3.setSize(1000, 1000);
        sPassage.setSize(20,20);
        panel3.add(sPassage);
        frame3.add(panel3);
        frame3.setVisible(true);

        JLabel title = new JLabel("An Overview of Personality Disorders");
        panel3.add(title);
        panel3.add(vpassage);
        vpassage.setLineWrap(true);
        vpassage.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane (vpassage);
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        panel3.add(scroll);
        scroll.setVisible(true);

    }

    public void editSMS()
    {
        ArrayList<Integer> passNums = new ArrayList<Integer>();

        Connection conn = null;
        Statement stmt = null;
        try{
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            //Quiz Table
            sql = "SELECT PassageID FROM Passage_Table";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                passNums.add(rs.getInt("PassageID"));
            }

            String label = "Passage IDs Available: ";
            for(int i = 0; i < passNums.size(); i++)
            {
                String add = passNums.get(i) + " ";
                label+= add;
            }

            String selection = JOptionPane.showInputDialog(null, label,"Select which Passage's SMS to Edit");
            int sel = Integer.parseInt(selection);

            editPassSMS(sel);

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

    public void editPassSMS(int passNum)
    {
        p = new Passage(passNum);
        sms = p.getSMSList();
        panel1.removeAll();
        editSMSs.setLocationRelativeTo(null);
        editSMSs.pack();
        editSMSs.setSize(500,500);

        panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));

        rButtons = new JRadioButton[sms.size()];
        qRadioButtons = new ButtonGroup();

        for(int i = 0; i < sms.size(); i++)
        {
            rButtons[i] = new JRadioButton("\""+sms.get(i).getMessage() +"\", at line: " + sms.get(i).getLine());
            qRadioButtons.add(rButtons[i]);
            panel1.add(rButtons[i]);
        }
        sSMS.setActionCommand("SMS Message");
        sSMS.addActionListener(this);
        panel1.add(sSMS);

        addSMS.addActionListener(this);

        panel1.add(addSMS);
        editSMSs.add(panel1);
        editSMSs.setVisible(true);
    }

    public void editSMSMessage(SMS s)
    {
        sm = s;
        panel1.removeAll();
        edSMS.setText(sm.getMessage());
        smsLine.setText("" + sm.getLine());
        edSMS.setPreferredSize(new Dimension(500,24));
        edSMS.setMaximumSize(edSMS.getPreferredSize());
        edSMS.setEditable(true);
        smsLine.setPreferredSize(new Dimension(500,24));
        smsLine.setMaximumSize(smsLine.getPreferredSize());
        smsLine.setEditable(true);

        saveSMS.addActionListener(this);
        JLabel line = new JLabel("Line Number");
        JLabel mess = new JLabel("Message");
        panel1.add(saveSMS);
        panel1.add(line);
        panel1.add(smsLine);
        panel1.add(mess);
        panel1.add(edSMS);
        editSMSs.add(panel1);
        editSMSs.setVisible(true);

    }
    public void editUserSettings()
    {
        frame7.setLocationRelativeTo(null);
        frame7.pack();
        frame7.setSize(500,500);
        frame7.add(panel);
        sUser.setSize(20,20);
        panel.add(sUser);
        frame7.setVisible(true);
    }

    public void answerSMS()
    {
      

    }

    public void answerQuiz()
    {
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setSize(500,500);  
        frame.add(panel);
        panel.add(j);       
        button1.setSize(20,20);
        panel.add(button3);
        frame.setVisible(true); 
    }

    public void save()
    {

    }

    public void choosePassage()
    {
        ArrayList<Integer> passNums = new ArrayList<Integer>();

        Connection conn = null;
        Statement stmt = null;
        try{
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            //Quiz Table
            sql = "SELECT PassageID FROM Passage_Table";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                passNums.add(rs.getInt("PassageID"));
            }

            String label = "Passage IDs Available: ";
            for(int i = 0; i < passNums.size(); i++)
            {
                String add = passNums.get(i) + " ";
                label+= add;
            }

            String selection = JOptionPane.showInputDialog(null, label,"Select Passage to Edit");
            int sel = Integer.parseInt(selection);

            editPassage(sel);

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

    public void actionPerformed(ActionEvent e) 
    {
        if(e.getActionCommand().equals("Edit Passage"))
        {
            choosePassage();
        }
        else if(e.getActionCommand().equals("Edit Quiz"))
        {
            editQuiz();
        }
        else if(e.getActionCommand().equals("Edit SMS"))
        {
            editSMS();
        }
        else if(e.getActionCommand().equals("Edit User Settings"))
        {
            editUserSettings();
        }
        else if(e.getActionCommand().equals("Save Passage"))
        {
            frame3.dispose();
            p.editPassage(vpassage.getText());
            p.save();
            JOptionPane.showMessageDialog(frame,"Changes Submitted!","Confirmation",JOptionPane.PLAIN_MESSAGE);
          
        }
        else if(e.getActionCommand().equals("Preview Quiz"))
        {
            answerQuiz();
        }
        else if(e.getActionCommand().equals("Preview SMS"))
        {
            answerSMS();
        }
        else if(e.getActionCommand().equals("OK"))
        {
            frame5.dispose();
            frame.dispose();
            frame1.dispose();
        }
        else if(e.getActionCommand().equals("Log Out"))
        {
            frame6.dispose();
            logout();
        }
        else if(e.getActionCommand().equals("Submit"))
        {
            JOptionPane.showMessageDialog(frame, "Changes Submitted!", "Confirmation", JOptionPane.PLAIN_MESSAGE);
            
        }
        else if(e.getActionCommand().equals("Login"))
        {
            String u = userText.getText();
            char[] p = passwordText.getPassword();
            String pass = new String(p);
            login(u,pass);
            login.dispose();
        }
        else if(e.getActionCommand().equals("Edit Question"))
        {
            for(int i = 0; i < ques.size(); i++)
            {
                if(rButtons[i].isSelected())
                {
                    editQuestion(ques.get(i));
                    break;
                }
            }
            panel2.removeAll();
            frame2.dispose();
        }
        else if(e.getActionCommand().equals("Add Answer"))
        {
            eQues.removeAll();
            editQuestion.dispose();
            String input = JOptionPane.showInputDialog("Add Answer", "Insert answer here");
            if(!(input.equals("")))
                currQuestion.addAnswer(input,1);
            editQuestion(currQuestion);
        }
        else if(e.getActionCommand().equals("Delete Answer")) {
            eQues.removeAll();
            editQuestion.dispose();
            String input = JOptionPane.showInputDialog("Delete Answer", "Enter Answer Number:");
            int a = Integer.parseInt(input);
            if(!(input == null))
                currQuestion.deleteAnswer(a-1);
            editQuestion(currQuestion);
        }
        else if(e.getActionCommand().equals("Save Question"))
        {
            ques.clear();
            ans.clear();
            eQues.removeAll();
            editQuestion.dispose();
            answers = new JTextField[0];
            currQuestion.editQuestion(eQuestion.getText());
            currQuestion.save();
            panel2.removeAll();
            editQuiz();
        }
        else if(e.getActionCommand().equals("Delete Question")){
            for(int i = 0; i < ques.size(); i++)
            {
                if(rButtons[i].isSelected())
                {
                    q.deleteQuestion(i);
                    break;
                }
            }
            panel2.removeAll();
            frame2.dispose();
            JOptionPane.showMessageDialog(null,"Question Deleted");
            editQuiz();
        }
        else if(e.getActionCommand().equals("Add Question"))
        {
            panel2.removeAll();
            frame2.dispose();
            String input = JOptionPane.showInputDialog("Add Question", "Insert question here");
            if(!(input.equals("")))
                q.addQuestion(input);
            JOptionPane.showMessageDialog(null,"Question Added");
            editQuiz();
        }
        else if(e.getActionCommand().equals("SMS Message"))
        {
            editSMSs.dispose();
            for(int i = 0; i < sms.size(); i++)
            {
                if(rButtons[i].isSelected())
                {
                    editSMSMessage(sms.get(i));
                    break;
                }
            }
        }
        else if(e.getActionCommand().equals("Save SMS"))
        {
            sm.editSMS(edSMS.getText(),Integer.parseInt(smsLine.getText()));
            sm.save();
            editSMSs.dispose();
            editSMS();
        }
        else if(e.getActionCommand().equals("Add SMS"))
        {
            String input = JOptionPane.showInputDialog("Add SMS", "Insert Message Here");
            String line = JOptionPane.showInputDialog("Add Line Number","Insert line number");
            if(!(input.equals(""))) {
                int l = Integer.parseInt((line));
                int id = sms.size() + 1;

                sms.add(new SMS(id,input,l,p.getPassageID()));
            }
            JOptionPane.showMessageDialog(null,"SMS Added");
            panel1.removeAll();
            editPassSMS(p.getPassageID());
        }

    }
}