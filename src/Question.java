/**
 * Created by Mike on 11/16/14.
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

public class Question {
    String question;
    int questionNum;
    ArrayList<Answer> answers;
    boolean saved;

    //creates new Question object.
    public Question (String question)
    {
        this.question = question;
        //questionNum = next number in database;
    }

    //returns the question's text.
    public String getQuestion()
    {
        return question;
    }

    //returns an arraylist of the possible answers.
    public ArrayList<Answer> getAnswers()
    {
        return answers;
    }

    //for the professor to save changes.
    public void saved()
    {
        saved = true;
    }

    //updates the question content.
    public void editQuestion(String question)
    {
        this.question = question;
        saved = false;
    }

    //adds a new answer to the arraylist.
    public void addAnswer(String answer, int type)
    {
        Answer a = new Answer(answer,type,questionNum);
        answers.add(a);
        saved = false;
    }

    //removes an answer from the arraylist.
    public void deleteAnswer(int choice)
    {
        answers.remove(choice);
        saved = false;
    }

    //edits a particular answer.
    public void editAnswer(int choice, String answer, int type) {
        Answer a = answers.get(choice);
        a.editAnswer(answer, type);
    }
}
