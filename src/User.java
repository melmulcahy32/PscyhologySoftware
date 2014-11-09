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

public abstract class User {
    abstract void viewPassage() throws IOException;
    abstract void answerSMS();
    abstract void answerQuiz();
}

