import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
public class Professor extends User implements ActionListener
{
    JFrame frame = new JFrame("answerQuiz");
    JFrame frame1 = new JFrame("answerSMS");
    JFrame frame2 = new JFrame("editQuiz");
    JFrame frame3 = new JFrame("editPassage");
    JFrame frame4 = new JFrame("editSMS");
    JFrame frame5 = new JFrame("viewPassage");

    JPanel panel = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();

    JRadioButton j = new JRadioButton();
    JButton button = new JButton("Submit");
    JButton button1 = new JButton("Save");
    JButton button2 = new JButton("Read");

    JTextField tf = new JTextField("Edited Text Here");
    JTextField tf1 = new JTextField("Edited Text Here");
    JTextField tf2 = new JTextField("Edited Text Here");

    private String username;
    private String password;
    boolean login = false;
    boolean savedSettings = false;
    public void login()
    {

    }

    public void editQuiz()
    {
        frame2.setLocationRelativeTo(null);
        frame2.pack();
        frame2.setSize(500,500);  
        frame2.add(panel2);      
        tf.setLocation(200,90);
        tf.setSize(100,25);
        panel2.add(tf);
        frame2.setVisible(true);
    }

    public void editPassage()
    {
        frame3.setLocationRelativeTo(null);
        frame3.pack();
        frame3.setSize(500,500);
        tf1.setLocation(200,90);
        tf1.setSize(100,25);
        panel3.add(tf1);
        frame3.setVisible(true);
    }

    public void editSMS()
    {
        frame4.setLocationRelativeTo(null);
        frame4.pack();
        frame4.setSize(500,500);
        tf2.setLocation(200,90);
        tf2.setSize(100,25);
        panel4.add(tf2);
        frame4.setVisible(true);
    }

    public void editUserSettings()
    {
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setSize(500,500);  
        frame.add(panel);      
        button.setSize(20,20);
        panel.add(button);
        button.addActionListener(this);
        frame.setVisible(true); 
    }

    public void viewPassage()
    {
        frame5.setLocationRelativeTo(null);
        frame5.pack();
        frame5.setSize(500,500);  
        frame5.add(panel1);       
        panel1.add(button2);
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
        button1.setSize(20,20);
        panel.add(button1);
        button1.addActionListener(this);
        frame.setVisible(true); 
    }

    public void save()
    {

    }

    public void actionPerformed(ActionEvent e) 
    {
        JOptionPane.showMessageDialog(frame,"Changes Submitted!","Confirmation",JOptionPane.PLAIN_MESSAGE);
    }
}