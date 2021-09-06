package Server;

import lombok.SneakyThrows;

import javax.swing.*;
import java.io.File;

public class ServerFrame extends JFrame {
    @SneakyThrows
    ServerFrame(File file){
        super("Received Image");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,600);
        ImageDraw imageDraw = new ImageDraw(file);
        imageDraw.setBounds(0,0,800,600);
        add(imageDraw);
        setLocationRelativeTo(null);
        setVisible(true);
        if(file.delete()) {
            System.out.println("File deleted!");
        }
        else{
            System.out.println("Error deleting file ;(");
        }
    }

}
