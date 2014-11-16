import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by melmulcahy on 11/9/14.
 */
public class Student extends User implements ActionListener {
    ArrayList timeSMS = new ArrayList();
    int timePassage;
    int idNumber;
    boolean finishedReading = false;
    boolean answeredQuestion = false;
    boolean answeredSMS = false;

    JFrame pframe = new JFrame("Start Passage");
    JFrame qframe = new JFrame("answerQuiz");
    JFrame sframe = new JFrame("answerSMS");
    JFrame passframe = new JFrame("Passage");

    JPanel panel = new JPanel();

    JRadioButton j = new JRadioButton();

    JButton submit = new JButton("Submit");
    JButton read = new JButton("Read");
    JButton takeQuiz = new JButton("Take quiz");

    public Student()
    {
        //this will change to getting the last idnumber and adding 1 to it
        idNumber = 1;
        viewPassage();
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
        sframe.setLocationRelativeTo(null);
        sframe.pack();
        sframe.setSize(500,500);
        String answer = JOptionPane.showInputDialog(null, "Enter Answer:", "SMS", JOptionPane.QUESTION_MESSAGE);
        Scanner scan = new Scanner(answer);
        answer = scan.nextLine();
        sframe.setVisible(true);
    }

    public void answerQuiz()
    {
        qframe.setLocationRelativeTo(null);
        qframe.pack();
        qframe.setSize(500,500);
        qframe.add(panel);
        panel.add(j);
        submit.setSize(20,20);
        panel.add(submit);
        submit.addActionListener(this);
        qframe.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {

        //displays passage
        if(e.getActionCommand().equals("Read Passage"))
        {
            pframe.dispose();
            passframe.setVisible(true);
            passframe.setSize(300,300);

            BufferedReader br;
            // Assuming the below line of code to be connected to the database.
            try {
                br = new BufferedReader(new FileReader("src/viewText.txt"));
                String read;
                while ((read = br.readLine()) != null){
                    JLabel label = new JLabel(read);
                    JPanel panel = new JPanel();
                    passframe.add(panel);
                    panel.add(label);
                    panel.add(takeQuiz);
                    takeQuiz.addActionListener(this);
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else if(e.getActionCommand().equals("Submit"))
        {
            qframe.dispose();
            JOptionPane.showMessageDialog(qframe,"Answers Submitted!","Confirmation",JOptionPane.PLAIN_MESSAGE);
            Runner.mainFrame.setVisible(true);
        }
        else if(e.getActionCommand().equals("Take quiz"))
        {
            this.finishedReading = true;
            passframe.dispose();
            answerQuiz();
        }
    }
}
