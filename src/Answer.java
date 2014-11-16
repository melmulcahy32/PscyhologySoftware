import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Answer {
    String answer;
    int type;
    int questionNum;
    boolean saved;

    public Answer (String answer, int type, int questionNum)
    {
        this.questionNum = questionNum;
        this.answer = answer;
        this.type = type;
        //will also create  new entry in database
    }

    //returns the answer text.
    public String getAnswer()
    {
        return answer;
    }

    //returns the answer type. 1 = fill in the blank, 0 = multiple choice.
    public int getType()
    {
        return type;
    }

    //edits the answer text and/or type.
    public void editAnswer(String answer, int type)
    {
        saved = false;
        this.answer = answer;
        this.type = type;
        //save to the database
    }

    public void save()
    {
        saved = true;
    }
}
