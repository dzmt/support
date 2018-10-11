package by.smirnov.command.client;

import by.smirnov.facade.Client;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;

import javax.websocket.EncodeException;
import java.io.IOException;

public interface ClientStatusCommand {

    void setCommand(Type type, ClientMessageCommand command);

    void handle(Message message, Client person) throws IOException, EncodeException;
}
