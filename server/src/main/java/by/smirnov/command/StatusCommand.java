package by.smirnov.command;

import by.smirnov.facade.User;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;

import javax.websocket.EncodeException;
import java.io.IOException;

public interface StatusCommand {
    void setCommand(Type type, MessageCommand command);
    void handle(Message message, User person) throws IOException, EncodeException;
}
