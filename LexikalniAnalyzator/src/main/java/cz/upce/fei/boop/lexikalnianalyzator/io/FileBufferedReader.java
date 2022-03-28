package cz.upce.fei.boop.lexikalnianalyzator.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author karel@simerda.cz
 */
public class FileBufferedReader {

    private static final FileBufferedReader instance = null;

    public static FileBufferedReader create(String path) throws FileNotFoundException {

        return new FileBufferedReader(path);

    }
    private final BufferedReader bufferedReader;
    private int znak;

    private FileBufferedReader(String path) throws FileNotFoundException {
        Objects.requireNonNull(path);
        bufferedReader = new BufferedReader(new FileReader(path));
    }

    public boolean isEOF() {
        return znak == -1;
    }

    public int read() throws IOException {
        znak = bufferedReader.read();
        return znak;
    }

}
