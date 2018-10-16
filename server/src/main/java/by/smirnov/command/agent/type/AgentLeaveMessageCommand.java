package by.smirnov.command.agent.type;

import by.smirnov.command.agent.AgentMessageCommand;
import by.smirnov.enumeration.Status;
import by.smirnov.facade.Agent;
import by.smirnov.facade.Client;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import by.smirnov.message.registry.MessageRegistry;

import javax.websocket.EncodeException;
import java.io.IOException;

public class AgentLeaveMessageCommand implements AgentMessageCommand {
    private String leftMessage = "You have left chat with %s.";

    @Override
    public void handle(Message message, Agent person) throws IOException, EncodeException {
        if (person.getStatus().equals(Status.SLEEPING)) {
            person.send(MessageRegistry.getMessage("agent.sleeping.leave"));
        } else {
            Client client = person.unsubscribeById(message.getTo());
            client.send(message);
            client.unsubcribe();
            person.subscribeFromWaitingRoom();
            person.send(new Message(Type.CONTENT, String.format(leftMessage, client.getName())));
        }
    }
}
