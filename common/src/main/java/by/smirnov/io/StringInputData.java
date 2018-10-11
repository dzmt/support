package by.smirnov.io;

import java.io.BufferedReader;
import java.io.IOException;

public class StringInputData {
    private BufferedReader reader;

    public StringInputData(BufferedReader reader) {
        this.reader = reader;
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    public boolean available() throws IOException {
        return reader.ready();
    }
}
