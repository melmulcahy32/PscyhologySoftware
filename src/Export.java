import java.util.Scanner;
import java.io.*;
public class Export
{
    public Export()
    {
    
    }
    
    public void export()
    {
        //to be changed
      try {
             PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("data.xls", true)));
             out.println("Student Number Here" + "\t" + "Answer to sms goes here");
             out.close();
        }catch (IOException e) {
 
        }
    }
}
