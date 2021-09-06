package Client;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.sound.sampled.Port;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class Sender extends Thread {
    private String format;
    public static final int PORT = 8855;
    public static String filePath;

    @SneakyThrows
    Sender(String format, Container container) {
        this.format = format;
        filePath = "pick." + format;
        BufferedImage image = new BufferedImage(container.getWidth(), container.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        container.paint(g);

        if(new File("pick.bmp").exists()){
            if(!new File("pick.bmp").delete()) {
                System.err.println("Error has been occurred via deleting old pick file ;(");
            }
        }
        if(new File("pick.png").exists()){
            if(!new File("pick.png").delete()) {
                System.err.println("Error has been occurred via deleting old pick file ;(");
            }
        }
        if(new File("pick.jpg").exists()){
            if(!new File("pick.jpg").delete()) {
                System.err.println("Error has been occurred via deleting old pick file ;(");
            }
        }
        ImageIO.write(image, format, new File(filePath));
    }

    @SneakyThrows
    @Override
    public void run() {
        Socket socket = new Socket(InetAddress.getLocalHost(), PORT);
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        FileInputStream fileInputStream = new FileInputStream("pick."+format);
        byte[] sendBytes = new byte[1024];
        int length;
        while ((length = fileInputStream.read(sendBytes, 0, sendBytes.length)) > 0) {
            dataOutputStream.write(sendBytes, 0, length);
        }
        dataOutputStream.flush();
        dataOutputStream.close();
    }
}
