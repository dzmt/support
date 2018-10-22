package by.smirnov.command.agent;

import by.smirnov.command.MessageCommand;
import by.smirnov.enumeration.Status;
import by.smirnov.facade.User;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import by.smirnov.message.registry.MessageRegistry;

import javax.websocket.EncodeException;
import java.io.IOException;

public class AgentLeaveMessageCommand implements MessageCommand {
    private String leftMessage = "You have left chat with %s.";

    @Override
    public void handle(Message message, User person) throws IOException, EncodeException {
        if (person.getStatus().equals(Status.SLEEPING)) {
            person.send(MessageRegistry.getMessage("agent.sleeping.leave"));
        } else {
            User client = person.getListInterlocutors().stream()
                            .filter(interlocuter -> interlocuter.getId().equals(message.getTo()))
                            .findAny()
                            .get();

            client.send(message);
            person.unsubcribe(client);
            client.unsubcribe(person);
            client.setStatus(Status.SLEEPING);
            person.send(new Message(Type.CONTENT, String.format(leftMessage, client.getName())));
            person.subscribeReady();
        }
    }
}
