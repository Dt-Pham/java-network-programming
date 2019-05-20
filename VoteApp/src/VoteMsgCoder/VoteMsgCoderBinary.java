package VoteMsgCoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import VoteMsg.VoteMsg;

/*
 *  0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |      Magics     |Flags|                       | 
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |                Candidate ID                   |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |                 Vote Counts                   | 
 * |                                               | 
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * 
 */





public final class VoteMsgCoderBinary implements VoteMsgCoder {
    private static final int MSG_SIZE = 8;
    private static final int MAGIC = 0x5400;

    @Override
    public byte[] encode(VoteMsg msg) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buf);

        int isResponse = msg.isReponse() ? 1 : 0;
        int isInquiry = msg.isInquiry() ? 1 : 0;
        out.writeShort(MAGIC | isResponse << 9 | isInquiry << 8);
        out.writeShort(msg.getCandidateID());
        out.writeInt(msg.getVoteCount());
        out.flush();
        return buf.toByteArray();
    }

    @Override
    public VoteMsg decode(byte[] data) throws IOException {
        ByteArrayInputStream buf = new ByteArrayInputStream(data);
        DataInputStream in = new DataInputStream(buf);

        int firstByte = in.readByte();
        // check magic
        if (MAGIC != (firstByte >> 2) << 10) {
            throw new IOException("Wrong magic");
        }
        boolean isResponse = ((firstByte >> 1) & 1) == 1;
        boolean isInquiry = (firstByte & 1) == 1;
        in.readByte();
        int candidateID = in.readShort();
        int voteCount = in.readInt();
        return new VoteMsg(isResponse, isInquiry, candidateID, voteCount);
    }
}
