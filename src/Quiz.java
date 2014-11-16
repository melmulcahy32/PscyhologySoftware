import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Quiz {
    private ArrayList<Answer> answerkey;
    private ArrayList<Question> questions;
    private boolean submitted = false;
    private boolean saved = false;

    //creates a Quiz object
    public Quiz()
    {
        //retrieve answerkey from database
        //create new question objects from information in the database
        //will also create the answer objects
    }

    //selects a question from the Arraylist to be edited.
    public void editQuestion(int choice, String question)
    {
        Question q = questions.get(choice);
        q.editQuestion(question);
        saved = false;
    }

    //update/change the answerkey.
    public void editAnswerKey(int answer)
    {

    }

    // REPLACED view()
    // returns the questions in order to be displayed on the screen.
    public ArrayList<Question> getQuestions()
    {
        return questions;
    }

    //returns the answer key to be used to check for correctness.
    public ArrayList<Answer> getAnswerkey()
    {
        return answerkey;
    }

    //For the student to submit their solutions to be stored in the database.
    public void submit()
    {
        submitted = true;
    }

    //For the professor to save edits to answer key.
    public void save()
    {
        saved = true;
    }



}
