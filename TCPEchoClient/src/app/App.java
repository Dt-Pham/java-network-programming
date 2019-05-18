package app;

import java.net.*;
import java.io.*;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length < 2 || args.length > 3) {
            throw new IllegalArgumentException("Parameter: <host> <string> [port]");
        }

        String server = args[0];
        byte[] data = args[1].getBytes();
        int port = 8080;
        if (args.length == 3) {
            port = Integer.parseInt(args[2]);
        }

        // Create connection to the server
        Socket socket = new Socket(server, port);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        // write data to the server
        out.write(data);

        // receive data back
        int totalRecv = 0;
        int numRecv = 0;

        while (totalRecv < data.length) {
            numRecv = in.read(data, totalRecv, data.length - totalRecv);
            if (numRecv == -1) {
                break;
            }
            totalRecv += numRecv;
            //System.out.println(numRecv);
        }
        socket.close();
        
        System.out.println("Received: " + new String(data));
    }
}