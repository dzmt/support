package by.smirnov.command.client.type;

import by.smirnov.command.client.ClientMessageCommand;
import by.smirnov.facade.Client;
import by.smirnov.facade.User;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;

import javax.websocket.EncodeException;
import java.io.IOException;

public class ClientSleepingLeaveMessageCommand implements ClientMessageCommand {

    @Override
    public void handle(Message message, User person) throws IOException, EncodeException {
        person.send(new Message(Type.CONTENT, "You are not talking with any agent."));
    }
}