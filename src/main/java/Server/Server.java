package Server;

import lombok.SneakyThrows;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
public static final int PORT = 8855;
    @SneakyThrows
    Server() {
        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket socket = serverSocket.accept();
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

        byte[] inputByte = new byte[10240];
        int length;
        FileOutputStream fileOutputStream = new FileOutputStream("temp.jpg");
        System.out.println("Начало приема");
        while ((length = dataInputStream.read(inputByte, 0, inputByte.length)) > 0) {
            fileOutputStream.write(inputByte, 0, length);
        }
        System.out.println("Принято");
        fileOutputStream.flush();
        dataInputStream.close();
        fileOutputStream.close();
        socket.close();
        new ServerFrame(new File("temp.jpg"));
    }


    public static void main(String[] args) {
        new Server();
    }
}
