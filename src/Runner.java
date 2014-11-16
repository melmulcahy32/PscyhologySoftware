import javax.swing.*;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Created by melmulcahy on 11/9/14.
 */
public class Runner{
    static JFrame mainFrame = new JFrame("Welcome");
    public static void startup()
    {
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if(e.getActionCommand().equals("Login"))
                {
                    Professor p = new Professor();
                }
                else if(e.getActionCommand().equals("Take Survey"))
                {
                    Student s = new Student();
                    s.answerSMS();
                }
                mainFrame.setVisible(false);
            }
        };

        mainFrame.setLocationRelativeTo(null);
        mainFrame.pack();
        mainFrame.setSize(500,500);
        JPanel mainPanel = new JPanel();
        JButton takeSurvey = new JButton("Take Survey");
        JButton login = new JButton("Login");
        takeSurvey.addActionListener(listener);
        login.addActionListener(listener);
        mainPanel.add(takeSurvey);
        mainPanel.add(login);
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }
    public static void main(String[] args) throws IOException {

        startup();

    }

}