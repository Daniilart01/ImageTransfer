package Drawing;

import javax.swing.*;

import java.awt.*;

import java.awt.event.*;

public class DrawRect extends JPanel {
    private Color colorLine;
    private Color colorFill;
    private int xBeg, yBeg, xEnd, yEnd;

    public DrawRect(Color colorLine, Color colorFill) {
        this.colorFill = colorFill;
        this.colorLine = colorLine;
        xBeg = yBeg = xEnd = yEnd = 0; //
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
        g.setColor(colorLine);
        drawingRectangle(g, xBeg, yBeg, xEnd, yEnd);
    }
    public void setBegining(int x, int y) {
        this.xBeg = x;
        this.yBeg = y;
    }

    public void setEnding(int x, int y) {
        xEnd = (x);
        yEnd = (y);
    }

    public void drawingRectangle(Graphics g, int x, int y, int x2, int y2) {
        Graphics2D g2d = (Graphics2D) g;
        int px = Math.min(x,x2);
        int py = Math.min(y,y2);
        int pw=Math.abs(x-x2);
        int ph=Math.abs(y-y2);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(px, py, pw, ph);
        if(colorFill != null) {
            g2d.setColor(colorFill);
            g2d.fillRect(px, py, pw, ph);
        }
    }
}