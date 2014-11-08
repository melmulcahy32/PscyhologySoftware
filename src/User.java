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
import java.util.Formatter;
import java.util.Locale;

abstract class User {
    abstract void viewPassage() throws IOException;
    //abstract void answerSMS();
    //abstract void answerQuiz();
    //abstract void frame();
}

class Student extends User {
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
        //this.dispose();
    }
}

class Runner{
    public static void main(String[] args) throws IOException {
        Student s = new Student();
        s.viewPassage();
    }
}