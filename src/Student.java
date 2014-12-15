import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.*;
import java.util.ArrayList;

import java.awt.event.*;
import javax.swing.Timer;


public class Student extends User implements ActionListener {


    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/Psychology_Software";
    static final String USER = "root";
    static final String PASS = "";

    int passage;
    Time time;
    int idNumber;
    private static int count = 0;
    private static int countSMS = 0;
    boolean finishedReading = false;

    JFrame pframe = new JFrame("Start Passage");
    JFrame qframe = new JFrame("answerQuiz");
    JFrame passframe = new JFrame("Passage");

    JScrollPane scroll;


    JTextField tf = new JTextField("Text Here");

    JPanel passagePanel = new JPanel();
    JPanel quizPanel = new JPanel();
    JButton submit = new JButton("Submit");
    JButton read = new JButton("Read");
    JButton takeQuiz = new JButton("Take quiz");
    ButtonGroup radioButtons;
    JRadioButton[] buttons;

    Quiz q = new Quiz();
    ArrayList<Question> questions = q.getQuestions();
    ArrayList<SMS> sms;
    ArrayList<Answer> answers;
    int numQuestions = questions.size();
    int curr = 0;

    int numPass;


    //creates a new student entry in the database
    public Student(int id, int passageNum) {
        this.idNumber = id;
        this.passage = passageNum;
        numPass = passageNum;

        Connection conn = null;
        Statement stmt = null;
        try {
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            //Quiz Table
            sql = "INSERT INTO Student_Table (StudentID, PassageNum)  VALUES (" + id + ", " + passageNum + ")";
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
            viewPassage();
        } catch (SQLException se) {
            //Handle errors for JDBC
            //se.printStackTrace();
            JOptionPane.showMessageDialog(null, "Duplicate ID!!! \nPlease restart the program and insert a different ID.", "Error", JOptionPane.ERROR_MESSAGE);

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

    //Timer!
    ActionListener actListner = new ActionListener() {



        @Override



        public void actionPerformed(ActionEvent event) {count += 1;
        }


    };
    Timer timer = new Timer(1000, actListner);

    //allows the student to view the passage
    public void viewPassage() {

        pframe.setVisible(true);
        pframe.setSize(750, 500);
        pframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JLabel instructions = new JLabel("<html>You will read a passage on personality disorders. <br><br>Please read carefully as you will be tested on the material after you are finished. <br><br>You might receive some text messages from the experimenter before reading, while you are reading, or not at all.<br> If you do, please respond to those messages immediately and continue reading.<br> You may use the arrow keys on the key board to move from line to line.<br><br>When the passage is complete, you will respond to some questions about the reading.<br> Please try to do the best that you can.<br><br>After completing these questions, there will be some additional questions asking you about your behaviors.<br><br>When you are ready to begin, please enter the number the experimenter has provided.<br><br>Thank you for your participation.<html>");
        pframe.add(panel);
        panel.add(instructions);
        read = new JButton("Read Passage");
        panel.add(read);
        read.addActionListener(this);

    }

    //does nothing, but replaced by answerSMS(int)
    public void answerSMS() {
    }

    //displays specific sms message
    public void answerSMS(int line) {
        passframe.setVisible(false);
        SMS s = null;
        for (int i = 0; i < sms.size(); i++) {
            int l = sms.get(i).getLine();
            if (l == line) {
                s = sms.get(i);
                break;
            }
        }
        ActionListener actListner = new ActionListener() {



            @Override



            public void actionPerformed(ActionEvent event) {countSMS += 1;
            }


        };
        Timer timerSMS = new Timer(1000, actListner);

        timerSMS.start();
        if (!(s == null)) {
            String input = JOptionPane.showInputDialog(null, "" + s.getMessage());
            Time t = new Time(countSMS); //has to be in milliseconds
            s.submit(this.idNumber, t, input);
            timerSMS.stop();



        }
        passframe.setVisible(true);
        //passage1.setCaretPosition(220);




    }

    //updates student entry with Time.
    public void updateStudent()
    {
        Connection conn = null;
        Statement stmt = null;
        try {
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            Time t = new Time(count);
            System.out.println(t);
            sql = "Update Student_Table SET TimePassage = '"+t+"' WHERE StudentID = " + this.idNumber;
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
            viewPassage();
        } catch (SQLException se) {

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

    //displays questions from the quiz using "curr" to keep track of which question to display.
    public void answerQuiz() {
        qframe.setLocationRelativeTo(null);
        qframe.pack();
        qframe.setSize(500, 500);
        qframe.add(quizPanel);
        quizPanel.removeAll();
        quizPanel.setLayout(new BoxLayout(quizPanel, BoxLayout.Y_AXIS));

        JLabel currQues = new JLabel();
        Question currentQuestion = questions.get(curr);
        currQues.setText(currentQuestion.getQuestion());
        quizPanel.add(currQues);

        answers = currentQuestion.getAnswers();
        int size = answers.size();

        buttons = new JRadioButton[size];
        radioButtons = new ButtonGroup();

        for (int i = 0; i < size; i++) {
            //1 = radio button, 0 = text field.
            if (answers.get(i).getType() == 1) {
                buttons[i] = new JRadioButton(answers.get(i).getAnswer());
                radioButtons.add(buttons[i]);

            } else {
                buttons[i] = new JRadioButton("Other");
                radioButtons.add(buttons[i]);
                quizPanel.add(tf);

            }

            quizPanel.add(buttons[i]);
        }


        submit.setSize(20, 20);
        quizPanel.add(submit);
        submit.addActionListener(this);
        qframe.setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {

        //displays passage
        if (e.getActionCommand().equals("Read Passage")) {
            Passage p = new Passage(this.passage);
            passagePanel.setLayout(new BoxLayout(passagePanel, BoxLayout.Y_AXIS));
            sms = p.getSMSList();
            pframe.dispose();
            passframe.setVisible(true);
            passframe.add(passagePanel);
            passframe.setSize(500, 500);
            takeQuiz.addActionListener(this);
            passagePanel.add(takeQuiz);
            takeQuiz.setVisible(false);
            JTextArea passage = new JTextArea(5, 20);


            passage.setEditable(false);
            passage.setText(p.viewPassage());

            passage.setLineWrap(true);
            passage.setWrapStyleWord(true);
            passagePanel.setLayout(new BoxLayout(passagePanel, BoxLayout.Y_AXIS));
            JLabel title = new JLabel("An Overview of Personality Disorders");
            passagePanel.add(title);

            timer.start();

            scroll = new JScrollPane(passage);

            passage.setCaretPosition(0);
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            passagePanel.add(scroll);
            scroll.setVisible(true);
            scroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {


                @Override
                public void adjustmentValueChanged(AdjustmentEvent e) {

                    if (scroll.getVerticalScrollBar().getValue() >= 208 && scroll.getVerticalScrollBar().getValue() <= 218)
                        answerSMS(10);
                    else if (scroll.getVerticalScrollBar().getValue() >= 740 && scroll.getVerticalScrollBar().getValue() <= 750)
                        answerSMS(20);
                   else if (scroll.getVerticalScrollBar().getValue() >= 1250 && scroll.getVerticalScrollBar().getValue() <= 1260)
                        answerSMS(30);
                   else if (scroll.getVerticalScrollBar().getValue() >= 1710 && scroll.getVerticalScrollBar().getValue() <= 1720)
                        answerSMS(40);
                    else if (scroll.getVerticalScrollBar().getValue() >= 2110 && scroll.getVerticalScrollBar().getValue() <= 2120) {
                        answerSMS(50);
                    }
                    else
                    {
                        if(scroll.getVerticalScrollBar().getValue() > 2120)
                            takeQuiz.setVisible(true);
                    }


                }
            });
            passagePanel.add(scroll);


        }
        //submits student answers to questions
        else if (e.getActionCommand().equals("Submit")) {
            qframe.dispose();
            for (int i = 0; i < answers.size(); i++) {
                if (buttons[i].isSelected()) {
                    if (answers.get(i).getType() != 0)
                        questions.get(curr).submit(this.idNumber, questions.get(curr).getQuestionID(), answers.get(i).getAnswer(),answers.get(i).getAnswerID());
                    else
                        questions.get(curr).submit(this.idNumber, questions.get(curr).getQuestionID(), tf.getText(),answers.get(i).getAnswerID());


                    break;
                }
            }
            curr++;
            tf.setText("");

            if (curr < numQuestions)
                answerQuiz();
            else {
                JOptionPane.showMessageDialog(qframe, "Quiz Completed!");
                System.exit(0);
            }
        }
        //student can view the quiz.
        else if (e.getActionCommand().equals("Take quiz")) {

            this.finishedReading = true;
            //update time here//
            passframe.dispose();
            timer.stop();
            updateStudent();
            answerQuiz();

        }


    }


}



