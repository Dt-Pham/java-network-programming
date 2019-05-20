package Framer;

import java.io.IOException;
import java.io.OutputStream;

public interface Framer {
    public void frameMsg(byte[] msg, OutputStream out) throws IOException;
    public byte[] nextMsg() throws IOException;
}