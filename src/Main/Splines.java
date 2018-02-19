package Main;

import gui.MainFrame;

import javax.swing.*;
import java.util.Scanner;

public class Splines implements Runnable{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Thread(new Splines()));
    }

    @Override
    public void run() {
        new MainFrame();
    }
}