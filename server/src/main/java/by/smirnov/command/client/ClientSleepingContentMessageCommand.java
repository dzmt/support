package by.smirnov.command.client;

import by.smirnov.command.MessageCommand;
import by.smirnov.enumeration.Status;
import by.smirnov.facade.User;
import by.smirnov.message.Message;
import by.smirnov.message.registry.MessageRegistry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.websocket.EncodeException;
import java.io.IOException;

public class ClientSleepingContentMessageCommand implements MessageCommand{
    private static final Logger logger = LogManager.getLogger(ClientSleepingContentMessageCommand.class);

    @Override
    public void handle(Message message, User person) throws IOException, EncodeException {
        boolean isSubscribed = person.subscribeReady();

        if (isSubscribed) {
            person.getListInterlocutors().get(0).send(message);
        } else {
            person.setStatus(Status.PENDING);
            person.addToWaitingRoom();
            person.send(MessageRegistry.getMessage("client.sleeping.content"));
            person.writeBuffer(message);
        }
    }
}
