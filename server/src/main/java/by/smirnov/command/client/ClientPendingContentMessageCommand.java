package by.smirnov.command.client.type;

import by.smirnov.command.client.ClientMessageCommand;
import by.smirnov.facade.Client;
import by.smirnov.facade.User;
import by.smirnov.message.Message;

import javax.websocket.EncodeException;
import java.io.IOException;

public class ClientPendingContentMessageCommand implements ClientMessageCommand {

    @Override
    public void handle(Message message, User person) throws IOException, EncodeException {
        person.writeBuffer(message);
    }
}
