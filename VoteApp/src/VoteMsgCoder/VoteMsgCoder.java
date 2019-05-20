package VoteMsgCoder;

import java.io.IOException;

import VoteMsg.VoteMsg;

public interface VoteMsgCoder {
    public byte[] encode(VoteMsg msg) throws IOException;
    public VoteMsg decode(byte[] data) throws IOException;
}
