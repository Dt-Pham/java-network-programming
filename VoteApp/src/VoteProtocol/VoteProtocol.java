package VoteProtocol;

import java.io.*;
import java.net.*;

import Framer.LengthFramer;
import VoteMsg.VoteMsg;
import VoteMsgCoder.*;

public class VoteProtocol implements Runnable {
    private static final int MAX_CANDIDATE_ID = 1000;
    private static int[] voteCount = new int[MAX_CANDIDATE_ID + 1];

    private Socket clientSocket;

    public VoteProtocol(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public static void handleVoteClient(Socket clientSocket) throws IOException {
        System.out.println("Handling client " + clientSocket.getRemoteSocketAddress());
        InputStream in = clientSocket.getInputStream();
        OutputStream out = clientSocket.getOutputStream();

        LengthFramer framer = new LengthFramer(in);
        byte[] data;

        VoteMsgCoder coder = new VoteMsgCoderBinary();

        while ((data = framer.nextMsg()) != null) {
            VoteMsg msg = coder.decode(data);
            System.out.println("Receive message: " + msg);
            // Handle message
            if (!msg.isReponse()) { // only handle request message.
                msg.setResponse(true);

                if (msg.isInquiry()) {
                    msg.setVoteCount(voteCount[msg.getCandidateID()]);
                    framer.frameMsg(coder.encode(msg), out);
                } else {
                    voteCount[msg.getCandidateID()]++;
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            handleVoteClient(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}