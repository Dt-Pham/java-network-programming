package app;

import java.net.*;
import java.io.*;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("Parameter: <Port>");
        }

        int port = Integer.parseInt(args[0]);

        // create server socket
        ServerSocket serverSocket = new ServerSocket(port);

        int BUF_SIZE = 32;
        byte[] buf = new byte[BUF_SIZE];
        int numRead;

        while (true) {
            Socket clientSocket = serverSocket.accept();

            System.out.println("Handling request at " + clientSocket.getInetAddress());

            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();

            while ((numRead = in.read(buf)) != -1) {
                System.out.println(numRead);
                out.write(buf, 0, numRead);   
            }
            clientSocket.close();
        }
    }
}