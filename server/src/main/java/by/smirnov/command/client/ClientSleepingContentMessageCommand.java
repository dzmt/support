package by.smirnov.command.client.type;

import by.smirnov.command.client.ClientMessageCommand;
import by.smirnov.facade.Client;
import by.smirnov.facade.User;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.websocket.EncodeException;
import java.io.IOException;

public class ClientSleepingContentMessageCommand implements ClientMessageCommand{
    private static final Logger logger = LogManager.getLogger(ClientSleepingContentMessageCommand.class);

    @Override
    public void handle(Message message, User person) throws IOException, EncodeException {
        if (person.subscribe()) {
            person.notifySubscriber(message);
        } else {
            person.send(new Message(Type.CONTENT, "There is no free agents now. " +
                    "You are queued up and our agents will answer soon."));
            person.writeBuffer(message);
        }
    }
}
