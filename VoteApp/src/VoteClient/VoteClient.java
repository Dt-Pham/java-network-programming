package VoteClient;

import java.io.*;
import java.net.Socket;
import java.util.Random;

import Framer.Framer;
import Framer.LengthFramer;
import VoteMsg.VoteMsg;
import VoteMsgCoder.VoteMsgCoder;
import VoteMsgCoder.VoteMsgCoderBinary;

public class VoteClient {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new IOException("Parameter: <host> <port>");
        }
        
        String server = args[0];
        int port = Integer.parseInt(args[1]);

        Socket socket = new Socket(server, port);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        Random rand = new Random();
        VoteMsgCoder coder = new VoteMsgCoderBinary();
        Framer framer = new LengthFramer(in);

        for (int i = 0; i < 10000; i++) {
            VoteMsg msg = new VoteMsg(false, false, rand.nextInt(21), 0);
            framer.frameMsg(coder.encode(msg), out);
        }

        for (int i = 0; i < 21; i++) {
            VoteMsg msg = new VoteMsg(false, true, i, 0);
            framer.frameMsg(coder.encode(msg), out);
            
            msg = coder.decode(framer.nextMsg());

            System.out.println(msg);
        }
        socket.close();
    }
}