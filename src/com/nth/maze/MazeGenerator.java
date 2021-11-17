package com.nth.maze;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * Created by HAU TRUNG NGUYEN <haunt.hcm2015@gmail.com> on Nov 13, 2021
 */
public class MazeGenerator extends JFrame {

    private static int CANVAS_WIDTH = 640;
    private static int CANVAS_HEIGHT = 640;

    private static int sizeOfWindow;
    private static int sizeOfBlock;
    private static int sizeOfMaze;

    private DrawCanvas canvas;

    private MazeGenerator() {
        CANVAS_WIDTH = sizeOfWindow;
        CANVAS_HEIGHT = sizeOfWindow;
        canvas = createCanvas();
        setWindowProperties();
    }

    private DrawCanvas createCanvas() {
        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        Container cp = getContentPane();
        cp.add(canvas);
        return canvas;
    }

    private void setWindowProperties() {
        setResizable(false);
        pack();
        setTitle("Maze Generator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class DrawCanvas extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.BLACK);

            g.setColor(Color.RED);

            int rows = sizeOfMaze;
            int columns = sizeOfMaze;
            int size = sizeOfBlock;

            Maze.generate(g, rows, columns, size);
        }
    }

    private static void set(int w, int b, int m) {
        sizeOfWindow = w;
        sizeOfBlock = b;
        sizeOfMaze = m;
    }

    public static void main(String[] args) {

        set(600, 20, 29);

        if (args.length == 1 && args[0].compareTo("-tiny") == 0) {
            set(600, 20, 29);
        } else if (args.length == 1 && args[0].compareTo("-small") == 0) {
            set(600, 10, 59);
        } else if (args.length == 1 && args[0].compareTo("-medium") == 0) {
            set(600, 5, 119);
        } else if (args.length == 1 && args[0].compareTo("-large") == 0) {
            set(600, 2, 299);
        } else if (args.length == 3) {
            set(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        }

        SwingUtilities.invokeLater(() -> new MazeGenerator());
    }
}
