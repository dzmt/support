package by.smirnov.command.client.type;

import by.smirnov.command.client.ClientMessageCommand;
import by.smirnov.facade.Client;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;

import javax.websocket.EncodeException;
import java.io.IOException;

public class ClientUnregisteredLeaveContentCommand implements ClientMessageCommand {
    private String template = "Please, register in the system. Type command '/register <your_name>'.";
    @Override
    public void handle(Message message, Client person) throws IOException, EncodeException {
        person.send(new Message(Type.CONTENT, template));
    }
}
