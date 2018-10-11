package by.smirnov.connection;

import by.smirnov.message.Message;

import javax.websocket.DeploymentException;
import java.io.IOException;

public interface OutboundConnection {

    void send(Message message) throws IOException;

    void connectToServer() throws DeploymentException, IOException;

    boolean isOpen() throws IOException;

    void close() throws IOException;
}
