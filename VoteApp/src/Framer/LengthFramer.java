package Framer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LengthFramer implements Framer {
    private final int BYTESHIFT = 8;
    private final int BYTEMASK = 0xff;

    private DataInputStream in;

    public LengthFramer(InputStream in) {
        this.in = new DataInputStream(in);
    }

    @Override
    public void frameMsg(byte[] msg, OutputStream out) throws IOException {
        int length = msg.length;

        out.write((length >> BYTESHIFT) & BYTEMASK);
        out.write(length & BYTEMASK);
        out.write(msg);
        out.flush();
    }

    @Override
    public byte[] nextMsg() throws IOException {
        int length;
        try {
            length = in.readUnsignedShort();
        } catch (IOException e) {
            return null;
        }

        byte[] data = new byte[length];
        in.readFully(data);

        return data;
    }
}