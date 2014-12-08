import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.*;
import java.util.ArrayList;

import java.awt.BorderLayout;  
import java.awt.event.*;

/**
 * Created by melmulcahy on 11/9/14.
 */
public class Student extends User implements ActionListener {



            static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
            static final String DB_URL = "jdbc:mysql://sql2.freemysqlhosting.net/sql260287";
            static final String USER = "sql260287";
            static final String PASS = "uE6!gF6*";

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


        public Student( int id, int passageNum)
            {
                this.idNumber = id;
                this.passage = passageNum;

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

        public void viewPassage () {

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

        public void answerSMS ()
        {
        }
        public void answerSMS ( int line)
        {
            passframe.setVisible(false);
            SMS s = null;
            for (int i = 0; i < sms.size(); i++) {
                int l = sms.get(i).getLine();
                if (l == line) {
                    s = sms.get(i);

                }
                break;
            }
            if (!(s == null)) {
                String input = JOptionPane.showInputDialog(null, "" + s.getMessage());
                Time t = new Time(0); //has to be in milliseconds
                s.submit(this.idNumber, t, input);

            }
            passframe.setVisible(true);

        }


        public void answerQuiz ()
        {
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
                }
                else {
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

        public void actionPerformed (ActionEvent e)
        {

            //displays passage
            if (e.getActionCommand().equals("Read Passage")) {
                Passage p = new Passage(this.passage);
                sms = p.getSMSList();
                pframe.dispose();
                passframe.setVisible(true);
                passframe.setSize(1000, 1000);
                passframe.add(passagePanel);
                takeQuiz.addActionListener(this);
                passagePanel.add(takeQuiz);
                JTextArea passage = new JTextArea(5, 20);

                passage.setEditable(false);
                passage.setText(p.viewPassage());

                passage.setLineWrap(true);
                passage.setWrapStyleWord(true);
                passagePanel.setLayout(new BoxLayout(passagePanel, BoxLayout.Y_AXIS));
                JLabel title = new JLabel("An Overview of Psychological Disorders");
                passagePanel.add(title);
                passagePanel.add(passage);

                scroll = new JScrollPane(passage);
                passage.setCaretPosition(0);


                scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                passagePanel.add(scroll);

                scroll.setVisible(true);


               scroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
                    @Override
                    public void adjustmentValueChanged(AdjustmentEvent e) {
                        if (scroll.getVerticalScrollBar().getValue() >=208  && scroll.getVerticalScrollBar().getValue()<=218)

                            answerSMS(10);




                        if(scroll.getVerticalScrollBar().getValue() >=370  &&  scroll.getVerticalScrollBar().getValue() <=380)
                           answerSMS(10);

                         if(scroll.getVerticalScrollBar().getValue() >=560  && scroll.getVerticalScrollBar().getValue()<=570)
                             answerSMS(10);


                        if(scroll.getVerticalScrollBar().getValue() >=815  && scroll.getVerticalScrollBar().getValue() <=825)
                            answerSMS(10);

                        if(scroll.getVerticalScrollBar().getValue() >=1040  && scroll.getVerticalScrollBar().getValue() <=1050)
                            answerSMS(10);

                        if(scroll.getVerticalScrollBar().getValue() >=1250  && scroll.getVerticalScrollBar().getValue() <=1260)
                             answerSMS(10);

                        if(scroll.getVerticalScrollBar().getValue() >=1470  && scroll.getVerticalScrollBar().getValue() <=1480)
                            answerSMS(10);

                         if(scroll.getVerticalScrollBar().getValue() >=1710  && scroll.getVerticalScrollBar().getValue()<=1720)
                             answerSMS(10);

                         if(scroll.getVerticalScrollBar().getValue() >=1900  && scroll.getVerticalScrollBar().getValue()<=1910)
                             answerSMS(10);

                        if(scroll.getVerticalScrollBar().getValue() >=2110  && scroll.getVerticalScrollBar().getValue() <=2120)
                            answerSMS(10);

                         if(scroll.getVerticalScrollBar().getValue() >=2270  && scroll.getVerticalScrollBar().getValue() <=2280)
                             answerSMS(10);

                         if(scroll.getVerticalScrollBar().getValue() >=2450  && scroll.getVerticalScrollBar().getValue() <=2460)
                             answerSMS(10);



                    }
                });



        }else if (e.getActionCommand().equals("Submit")) {
            qframe.dispose();
                for(int i = 0; i < answers.size(); i++) {
                    if (buttons[i].isSelected()) {
                        if (answers.get(i).getType() != 0)
                            questions.get(curr).submit(this.idNumber, questions.get(curr).getQuestionID(), answers.get(i).getAnswer());
                        else
                            questions.get(curr).submit(this.idNumber, questions.get(curr).getQuestionID(), tf.getText());


                        break;
                    }
                }
            curr++;
            JOptionPane.showMessageDialog(qframe, "Answers Submitted!", "Confirmation", JOptionPane.PLAIN_MESSAGE);
            tf.setText("");

            if (curr < numQuestions)
                answerQuiz();
            else {
                JOptionPane.showMessageDialog(qframe, "Quiz Completed!");
                System.exit(0);
            }
        } else if (e.getActionCommand().equals("Take quiz")) {
            this.finishedReading = true;
            //update time here//
            passframe.dispose();
            answerQuiz();
        }


        }
    }



