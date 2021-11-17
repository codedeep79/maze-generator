package com.nth.maze;

import java.awt.Graphics;
import java.util.Random;

/**
 *
 * Created by HAU TRUNG NGUYEN <haunt.hcm2015@gmail.com> on Nov 13, 2021
 */
public class Maze {

    private Maze() {
    }

    static DisjointSet generate(Graphics g, int rows, int columns, int size) {
        DisjointSet disjointSet = new DisjointSet(rows * columns);
        createBorders(g, rows, columns, size, disjointSet);
        generateMaze(g, rows, columns, size, disjointSet);
        return disjointSet;
    }

    private static void createBorders(Graphics g, int rows, int cols, int size, DisjointSet ds) {
        createBorderAtTop(g, rows, cols, size, ds);
        createBorderAtBottom(g, rows, cols, size, ds);
        createBorderAtLeft(g, rows, cols, size, ds);
        createBorderAtRight(g, rows, cols, size, ds);
    }

    private static void createBorderAtTop(Graphics g, int rows, int cols, int size, DisjointSet ds) {
        for (int i = 1; i < rows; i++) {
            ds.union(i, i - 1);
            drawLine(g, i, i - 1, cols, size);
        }
    }

    private static void createBorderAtBottom(Graphics g, int rows, int cols, int size, DisjointSet ds) {
        for (int i = rows * cols - 1; i > (rows * cols) - rows; i--) {
            ds.union(i, i - 1);
            drawLine(g, i - 1, i, cols, size);
        }
    }

    private static void createBorderAtLeft(Graphics g, int rows, int cols, int size, DisjointSet ds) {
        for (int i = 1; i < rows; i++) {
            ds.union((i - 1) * cols, i * cols);
            drawLine(g, (i - 1) * cols, i * cols, cols, size);
        }
    }

    private static void createBorderAtRight(Graphics g, int rows, int cols, int size, DisjointSet ds) {
        for (int i = 1; i < rows; i++) {
            ds.union((i - 1) * cols + rows - 1, i * cols + rows - 1);
            drawLine(g, (i - 1) * cols + rows - 1, i * cols + rows - 1, cols, size);
        }
    }

    private static void drawLine(Graphics g, int idx1, int idx2, int cols, int size) {
        int x1, x2, y1, y2;
        x1 = (idx1 % cols) * size + size;
        y1 = (idx1 / cols) * size + size;
        x2 = (idx2 % cols) * size + size;
        y2 = (idx2 / cols) * size + size;
        g.drawLine(x1, y1, x2, y2);
    }

    private static void generateMaze(Graphics g, int rows, int cols, int size, DisjointSet ds) {
        Random random = new Random();
        int randomElement;
        int randomNeighbour;

        while (ds.size() != 1) {

            randomElement = random.nextInt(rows * cols);
            randomNeighbour = randomNeighbour(randomElement, rows, cols);

            if (!ds.isConnected(randomElement, randomNeighbour)) {
                ds.union(randomElement, randomNeighbour);
                drawLine(g, randomElement, randomNeighbour, cols, size);
            }
        }
    }

    private static int randomNeighbour(int idx, int rows, int cols) {

        Random random = new Random();
        int r;
        int neighbour = -1;

        boolean inBounds = false;

        while (!inBounds) {

            r = random.nextInt(4);

            switch (r) {
                case 0:
                    neighbour = idx - rows;
                    break;
                case 1:
                    neighbour = idx + 1;
                    break;
                case 2:
                    neighbour = idx + rows;
                    break;
                case 3:
                    neighbour = idx - 1;
                    break;
                default:
                    break;
            }

            inBounds = inBounds(idx, neighbour, rows, cols);
        }

        return neighbour;
    }

    private static boolean inBounds(int idx, int neighbour, int rows, int cols) {

        boolean firstRow = (idx < rows);
        boolean lastRow = (idx >= (rows * (cols - 1)));
        boolean firstColumn = (idx % cols == 0);
        boolean lastColumn = (idx % cols == cols - 1);

        boolean neighbourIsAbove = (neighbour == idx - rows);
        boolean neighbourIsBelow = (neighbour == idx + rows);
        boolean neighbourIsLeft = (neighbour == idx - 1);
        boolean neighbourIsRight = (neighbour == idx + 1);

        if (firstRow && neighbourIsAbove) {
            return false;
        } else if (lastRow && neighbourIsBelow) {
            return false;
        } else if (firstColumn && neighbourIsLeft) {
            return false;
        } else return !(lastColumn && neighbourIsRight);

    }
}
