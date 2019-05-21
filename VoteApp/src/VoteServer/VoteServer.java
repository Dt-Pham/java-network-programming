package VoteServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import VoteProtocol.VoteProtocol;

public class VoteServer {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Parameter: <port>");
        }

        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);
        Executor service = Executors.newCachedThreadPool();

        while (true) {
            Socket clientSocket = serverSocket.accept();
            service.execute(new VoteProtocol(clientSocket));
        }
    }
}