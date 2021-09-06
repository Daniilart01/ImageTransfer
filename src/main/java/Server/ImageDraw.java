package Server;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ImageDraw extends JPanel {
    private final Image img;

    @SneakyThrows
    ImageDraw(File file) {
        this.img = ImageIO.read(file);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img,0,0,null);
    }
}
