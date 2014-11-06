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

abstract class User {
    abstract void viewPassage() throws IOException;
    abstract void answerSMS();
    abstract void answerQuiz();
    abstract void frame();
}

class Student extends User {
    @Override
    public void viewPassage() throws IOException {
        BufferedReader br;
        // Assuming the below line of code to be connected to the database.
        br = new BufferedReader(new FileReader("src\\viewText.txt"));
        String read;
        while ((read = br.readLine()) != null){
            System.out.println(read);
        }

    }

    @Override
    void frame() {
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

    @Override
    void answerSMS() {

    }

    @Override
    void answerQuiz() {

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
            br = new BufferedReader(new FileReader("C:\\Users\\Mike\\Documents\\viewText.txt"));
            String read;
            while ((read = br.readLine()) != null){
                System.out.println(read);
                JLabel label = new JLabel(read);
                JPanel panel = new JPanel();
                frame2.add(panel);
                panel.add(label);
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}

class Runner{
    public static void main(String[] args) throws IOException {
        Student s = new Student();
        s.viewPassage();
        s.frame();
    }
}