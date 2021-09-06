package Client;

import Drawing.DrawLine;
import Drawing.DrawOval;
import Drawing.DrawRect;
import XML.ReadFromXML;
import XML.WriteToXML;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.lang.System.exit;

public class DrawingFrame extends JFrame {
    private Color currentColorLine;
    private Color currentColorFill;

    DrawingFrame(){
        super("Drawing");
        try{
            ReadFromXML readFromXML = new ReadFromXML();
            currentColorLine = readFromXML.getColors().get(0);
            currentColorFill = readFromXML.getColors().get(1);
        }
        catch(Exception exception){
            JOptionPane.showMessageDialog(this,"Файл Settings.xml не найден!\nЗаписаны значения по умолчанию", "Ошибка",JOptionPane.WARNING_MESSAGE);
            currentColorLine = Color.BLACK;
            currentColorFill = Color.BLACK;
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800,600);
        JMenuBar menuBar = createViewMenu();
        setJMenuBar(menuBar);
        setLocationRelativeTo(null);
        setContentPane(new DrawLine(currentColorLine));
        setVisible(true);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent we) {
                new WriteToXML(currentColorLine, currentColorFill);
            }
        });
    }
    class ActionRectangle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setContentPane(new DrawRect(currentColorLine, currentColorFill));
            revalidate();
            repaint();
        }
    }
        class ActionOval implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(new DrawOval(currentColorLine, currentColorFill));
                revalidate();
                repaint();
            }
    }
    class ActionLine implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            setContentPane(new DrawLine(currentColorLine));
            revalidate();
            repaint();
        }
    }
    class ActionSetColor implements ActionListener{
        private final Color colorLine;
        private final Color colorFill;
        ActionSetColor(Color colorLine, Color colorFill){
            this.colorLine = colorLine;
            this.colorFill = colorFill;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(colorLine != null && colorFill == null){
                currentColorLine = colorLine;
                changingColor();
            }
            else if(colorLine == null && colorFill != null){
                currentColorFill = colorFill;
                changingColor();
            }
            else{
                currentColorFill = null;
                changingColor();
            }
        }
    }
    private void changingColor(){
        if(getContentPane() instanceof DrawRect){
            setContentPane(new DrawRect(currentColorLine, currentColorFill));
        }
        else if(getContentPane() instanceof  DrawOval) {
            setContentPane(new DrawOval(currentColorLine, currentColorFill));
        }
        else {
            setContentPane(new DrawLine(currentColorLine));
        }
        revalidate();
        repaint();
    }
    class ActionSave implements ActionListener{
        private final String format;
        ActionSave(String format){
            this.format = format;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            new Sender(format, getContentPane()).start();
        }
    }
    class ActionExit implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new WriteToXML(currentColorLine, currentColorFill);
            exit(0);
        }
    }
    public static void main(String[] args) {
        new DrawingFrame();
    }

    private JMenuBar createViewMenu()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");

        JMenu save  = new JMenu("Save and send  ");
        JMenuItem exit  = new JMenuItem("Exit");
        exit.addActionListener(new ActionExit());

        JMenuItem bmp  = new JMenuItem("Save as BMP");
        bmp.addActionListener(new ActionSave("bmp"));
        JMenuItem png  = new JMenuItem("Save as PNG");
        png.addActionListener(new ActionSave("png"));
        JMenuItem jpg  = new JMenuItem("Save as JPG");
        jpg.addActionListener(new ActionSave("jpg"));

        save.add(bmp);
        save.add(png);
        save.add(jpg);

        file.add(save);
        file.add( new JSeparator());
        file.add(exit);

        menuBar.add(file);
        /////////////////////////////
        JMenu figure = new JMenu("Figure");
        JMenuItem drawLine = new JMenuItem("Draw Line");
        drawLine.addActionListener(new ActionLine());
        JMenuItem drawRectangle = new JMenuItem("Draw Rectangle");
        drawRectangle.addActionListener(new ActionRectangle());
        JMenuItem drawOval = new JMenuItem("Draw Oval");
        drawOval.addActionListener(new ActionOval());

        figure.add(drawLine);
        figure.add(drawRectangle);
        figure.add(drawOval);

        menuBar.add(figure);
        ////////////////////////////
        JMenu drawing = new JMenu("Drawing");

        JMenu colorLine = new JMenu("Line Color  ");
        JMenu colorFill = new JMenu("Fill Color");

        JMenuItem colorLineRed = new JMenuItem("Red");
        colorLineRed.addActionListener(new ActionSetColor(Color.RED, null));
        JMenuItem colorLineGreen = new JMenuItem("Green");
        colorLineGreen.addActionListener(new ActionSetColor(Color.GREEN, null));
        JMenuItem colorLineBlue = new JMenuItem("Blue");
        colorLineBlue.addActionListener(new ActionSetColor(Color.BLUE, null));

        JMenuItem colorFillRed = new JMenuItem("Red");
        colorFillRed.addActionListener(new ActionSetColor(null, Color.RED));
        JMenuItem colorFillGreen = new JMenuItem("Green");
        colorFillGreen.addActionListener(new ActionSetColor(null, Color.GREEN));
        JMenuItem colorFillBlue = new JMenuItem("Blue");
        colorFillBlue.addActionListener(new ActionSetColor(null, Color.BLUE));
        JMenuItem noFilling = new JMenuItem("Do not fill");
        noFilling.addActionListener(new ActionSetColor(null, null));

        colorLine.add(colorLineBlue);
        colorLine.add(colorLineGreen);
        colorLine.add(colorLineRed);

        colorFill.add(colorFillBlue);
        colorFill.add(colorFillGreen);
        colorFill.add(colorFillRed);
        colorFill.add(noFilling);

        drawing.add(colorLine);
        drawing.add(colorFill);

        menuBar.add(drawing);
        return menuBar;
    }

}
