import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;
import java.util.Scanner;

abstract class User {
    abstract void viewPassage() throws IOException;
    abstract void answerSMS();
    abstract void answerQuiz();
}

class Student extends User implements ActionListener {
    ArrayList timeSMS = new ArrayList();
    int timePassage;
    int idNumber;
    boolean finishedReading = false;
    boolean answeredQuestion = false;
    boolean answeredSMS = false;

    JFrame frame = new JFrame("answerQuiz");
    JFrame frame1 = new JFrame("answerSMS");
    JFrame frame2 = new JFrame("viewPassage");

    JPanel panel = new JPanel();
    JPanel panel1 = new JPanel();

    JRadioButton j = new JRadioButton();

    JButton button = new JButton("Submit");
    JButton button1 = new JButton("Read");

    @Override
    public void viewPassage() throws IOException {
        JFrame frame = new JFrame("Test");
        frame.setVisible(true);
        frame.setSize(500,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        JButton button = new JButton("Read Passage");
        panel.add(button);
        button.addActionListener (new Action1());
    }

    public void answerSMS()
    {
        frame1.setLocationRelativeTo(null);
        frame1.pack();
        frame1.setSize(500,500);
        String answer = JOptionPane.showInputDialog("Text here ");
        Scanner scan = new Scanner(answer);
        answer = scan.nextLine();
        frame1.setVisible(true);
    }

    public void answerQuiz()
    {
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setSize(500,500);
        frame.add(panel);
        panel.add(j);
        button.setSize(20,20);
        panel.add(button);
        button1.addActionListener(this);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        JOptionPane.showMessageDialog(frame,"Changs Submitted!","Confirmation",JOptionPane.PLAIN_MESSAGE);
    }
}

 class Action1 implements ActionListener {
    public void actionPerformed (ActionEvent e) {
        JFrame frame2 = new JFrame("Passage");
        frame2.setVisible(true);
        frame2.setSize(300,300);

        BufferedReader br;
        // Assuming the below line of code to be connected to the database.
        try {
            br = new BufferedReader(new FileReader("src/viewText.txt"));
            String read;
            while ((read = br.readLine()) != null){
                JLabel label = new JLabel(read);
                JPanel panel = new JPanel();
                frame2.add(panel);
                panel.add(label);
                JButton button = new JButton("Take quiz");
                panel.add(button);
                button.addActionListener(new Action2());
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}

class Action2 implements ActionListener{
    public void actionPerformed (ActionEvent e) {
        JFrame frame3 = new JFrame("Quiz");
        frame3.setVisible(true);
        frame3.setSize(500,200);
        JPanel panel2 = new JPanel();
        frame3.add(panel2);

        JButton button3 = new JButton("Submit");
        panel2.add(button3);
    }
}

class Runner{
    public static void main(String[] args) throws IOException {
        Student s = new Student();
        s.viewPassage();
        s.answerSMS();
        s.answerQuiz();
    }
}