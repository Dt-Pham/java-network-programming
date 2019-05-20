package VoteServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import Framer.*;
import VoteMsg.VoteMsg;
import VoteMsgCoder.*;

public class VoteServer {
    private static final int MAX_CANDIDATE_ID = 1000;
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Parameter: <port>");
        }

        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);

        int[] voteCount = new int[MAX_CANDIDATE_ID + 1];
        VoteMsgCoder coder = new VoteMsgCoderBinary();

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Handling client " + clientSocket.getRemoteSocketAddress());
            
            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();

            LengthFramer framer = new LengthFramer(in);
            byte[] data;

            while((data = framer.nextMsg()) != null) {
                VoteMsg msg = coder.decode(data);
                System.out.println("Receive message: " + msg);
                // Handle message
                if (!msg.isReponse()) { // only handle request message.
                    msg.setResponse(true);
                    
                    if (msg.isInquiry()) {
                        msg.setVoteCount(voteCount[msg.getCandidateID()]);
                        framer.frameMsg(coder.encode(msg), out);
                    }
                    else {
                        voteCount[msg.getCandidateID()]++;
                    }
                }
            }
        }
    }
}