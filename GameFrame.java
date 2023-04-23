// this will contain the UI of the game

// TITLE: DREAM TEAM (by JV :D)

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class GameFrame {

    public int width, height;
    public JFrame frame;

    public GameCanvas canvas;

    public GameFrame(int w, int h) {
        width = w;
        height = h;

        frame = new JFrame();
        canvas = new GameCanvas(width, height);
    }

    public void setUpFrame() {

        Container contentPane = frame.getContentPane();

        contentPane.add(canvas, BorderLayout.CENTER);

        frame.setSize(width, height);
        frame.pack();
        frame.setTitle("test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}