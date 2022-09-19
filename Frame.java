import java.awt.*;
import javax.swing.*;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;

public class Frame extends JFrame { // a class to create the GUI

    public Node[][] graph;
    private int startX; // x coord of start vertex
    private int startY; // y coord of start vertex
    private int goalX; // x coord of goal vertex
    private int goalY; // y coord of goal vertex
    private int cols;
    private int rows;
    private int[][] blocked; // includes all blocked cells
    private int bkdCount;
    public ArrayList<Node> path; // shortest path found from start to goal
    public minHeap fringe;

    public Frame(int sx, int sy, int gx, int gy, int col, int row, int[][] bkd, int count, ArrayList<Node> Apth) { // creates a pop up window

        startX = sx; // initializing private variables, need to offset by *50 to fit scale of grid
        startY = sy;
        goalX = gx;
        goalY = gy;
        cols = col;
        rows = row;
        blocked = bkd;
        bkdCount = count;
        path = Apth;

        setSize(100 * cols, 100 * rows);
        setTitle("GRID PATH");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // calculate heuristic values and construct
        graph = new Node[rows + 1][cols + 1];
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= cols; j++) {
                double xdiff = Math.abs(j + 1 - goalX);
                double ydiff = Math.abs(i + 1 - goalY);
                graph[i][j] = new Node(i, j,
                        Math.sqrt(2) * Math.min(xdiff, ydiff) + Math.max(xdiff, ydiff) - Math.min(xdiff, ydiff));
            }
        }

    }

    // creates grid with components
    public void paint(Graphics g) {
        // draws grid rectangles
        for (int x = 50; x <= 50 * cols; x += 50) {
            for (int y = 50; y <= 50 * rows; y += 50) {
                g.drawRect(x, y, 50, 50);
            }
        }
        
        // adding numbers to grid 
        int num1 = 1;
        for(int x = 50; x <= 50*(cols+1); x += 50){
            String number1 = Integer.toString(num1);
            g.drawString(number1, x, 40);
            num1++;
        }
        int num2 = 1;
        for(int y = 50; y <= 50*(rows+1); y += 50){
            String number2 = Integer.toString(num2);
            g.drawString(number2, 40, y);
            num2++;
        }

        // color in blocked cells
        g.setColor(Color.gray);
        int pt1 = 0;
        int pt2 = 0;
        int blockedX = 0;
        int blockedY = 0;
        for (int count = bkdCount; count > 0; count--) {
            blockedX = blocked[pt1][pt2] * 50;
            blockedY = blocked[pt1][pt2 + 1] * 50;
            g.fillRect(blockedX, blockedY, 50, 50);
            pt1++;
        }

        // circles start and goal vertex
        int dia = 25; // diameter of circle
        g.setColor(Color.blue);
        g.drawOval(startX * 50 - (dia / 2), startY * 50 - (dia / 2), dia, dia);
        g.setColor(Color.green);
        g.drawOval(goalX * 50 - (dia / 2), goalY * 50 - (dia / 2), dia, dia);

        // labels start and goal vertex
        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
        g.drawString("Start Vertex", startX * 50 - (dia / 2), startY * 50 - (dia / 2));
        g.drawString("Goal Vertex", goalX * 50 - (dia / 2), goalY * 50 - (dia / 2));

        // add the final path
        if(path!=null){
            g.setColor(Color.red);
            int[] xcoords = new int[path.size()];
            int[] ycoords = new int[path.size()];
            for(int i = 0; i < path.size(); i++){
                xcoords[i] = (path.get(i).col + 1) * 50;
                ycoords[i] = (path.get(i).row + 1) * 50;
            }
            g.drawPolyline(xcoords,ycoords,path.size());

        }

       
    }

}
