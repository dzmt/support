package by.smirnov.connection;

import by.smirnov.message.Message;

import java.io.IOException;

public interface IncomingConnection {

    Message receive() throws IOException;

    boolean available() throws IOException;
}
