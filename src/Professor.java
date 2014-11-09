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
    JFrame frame6 = new JFrame("Dashboard");
    JFrame frame7 = new JFrame("User Settings");

    JPanel panel = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();
    JPanel panel5 = new JPanel();

    JRadioButton j = new JRadioButton();
    JButton button = new JButton("Submit");
    JButton button1 = new JButton("Save");
    JButton button2 = new JButton("Preview Passage");
    JButton button3 = new JButton("OK");
    JButton button4 = new JButton("Preview Quiz");
    JButton button5 = new JButton("Preview SMS");
    JButton eQuiz = new JButton("Edit Quiz");
    JButton ePassage = new JButton("Edit Passage");
    JButton eSMS = new JButton("Edit SMS");
    JButton eUser = new JButton("Edit User Settings");
    JButton logout = new JButton("Log Out");

    JTextField tf = new JTextField("Edited Text Here");
    JTextField tf1 = new JTextField("Edited Text Here");
    JTextField tf2 = new JTextField("Edited Text Here");

    private String username;
    private String password;
    boolean login = false;
    boolean savedSettings = false;

    public Professor ()
    {
        login();
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
        button.addActionListener(this);
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);
        button5.addActionListener(this);
    }

    public void login()
    {
        viewDashboard();
    }
    public void logout()
    {
        Runner.mainFrame.setVisible(true);
    }
    public void editQuiz()
    {
        frame2.setLocationRelativeTo(null);
        frame2.pack();
        frame2.setSize(500,500);  
        frame2.add(panel2);      
        tf.setLocation(200, 90);
        tf.setSize(100, 25);
        button.setSize(20,20);
        panel2.add(tf);
        panel2.add(button);
        panel2.add(button4);
        frame2.setVisible(true);
    }

    public void editPassage()
    {
        frame3.setLocationRelativeTo(null);
        frame3.pack();
        frame3.setSize(500, 500);
        tf1.setLocation(200, 90);
        tf1.setSize(100, 25);
        button.setSize(20,20);
        button2.setSize(20,20);
        panel3.add(tf1);
        panel3.add(button);
        panel3.add(button2);
        frame3.add(panel3);
        frame3.setVisible(true);
    }

    public void editSMS()
    {
        frame4.setLocationRelativeTo(null);
        frame4.pack();
        frame4.setSize(500, 500);
        tf2.setLocation(200, 90);
        tf2.setSize(100, 25);
        button.setSize(20,20);
        panel4.add(tf2);
        panel4.add(button);
        panel4.add(button5);
        frame4.add(panel4);
        frame4.setVisible(true);
    }

    public void editUserSettings()
    {
        frame7.setLocationRelativeTo(null);
        frame7.pack();
        frame7.setSize(500,500);
        frame7.add(panel);
        button.setSize(20,20);
        panel.add(button);
        frame7.setVisible(true);
    }

    public void viewPassage()
    {
        frame5.setLocationRelativeTo(null);
        frame5.pack();
        frame5.setSize(500,500);
        panel1.add(button3);
        frame5.add(panel1);
        frame5.setVisible(true);
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
        panel.add(button3);
        frame.setVisible(true); 
    }

    public void save()
    {

    }

    public void actionPerformed(ActionEvent e) 
    {
        if(e.getActionCommand().equals("Edit Passage"))
        {
            editPassage();
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
        else if(e.getActionCommand().equals("Submit") || e.getActionCommand().equals("Save"))
        {
            JOptionPane.showMessageDialog(frame,"Changes Submitted!","Confirmation",JOptionPane.PLAIN_MESSAGE);
        }
        else if(e.getActionCommand().equals("Preview Passage"))
        {
            viewPassage();
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
    }
}