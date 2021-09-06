package Drawing;

import javax.swing.*;

import java.awt.*;

import java.awt.event.*;

public class DrawLine extends JPanel {
    private Color color;
    private int xBeg, yBeg, xEnd, yEnd;

    public DrawLine(Color color) {
        this.color = color;
        xBeg = yBeg = xEnd = yEnd = 0;
        MyMouseListener listener = new MyMouseListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    class MyMouseListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            setBegining(e.getX(), e.getY());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            setEnding(e.getX(), e.getY());
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            setEnding(e.getX(), e.getY());
            repaint();
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        drawingLine(g, xBeg, yBeg, xEnd, yEnd);

    }
    public void setBegining(int x, int y) {
        this.xBeg = x;
        this.yBeg = y;
    }

    public void setEnding(int x, int y) {
        xEnd = (x);
        yEnd = (y);
    }

    public void drawingLine(Graphics g, int x, int y, int x2, int y2) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(color);
        g2d.drawLine(x,y,x2,y2);
    }
}