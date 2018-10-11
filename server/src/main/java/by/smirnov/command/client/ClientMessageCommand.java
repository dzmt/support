package by.smirnov.command.client;

import by.smirnov.facade.Client;
import by.smirnov.message.Message;

import javax.websocket.EncodeException;
import java.io.IOException;

public interface ClientMessageCommand {


    void handle(Message message, Client person) throws IOException, EncodeException;
}
