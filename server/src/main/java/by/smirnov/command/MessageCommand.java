package by.smirnov.command;

import by.smirnov.facade.User;
import by.smirnov.message.Message;

import javax.websocket.EncodeException;
import java.io.IOException;

public interface MessageCommand {
    void handle(Message message, User person) throws IOException, EncodeException;
}
