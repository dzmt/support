package by.smirnov.command.agent.type;

import by.smirnov.command.agent.AgentMessageCommand;
import by.smirnov.enumeration.Status;
import by.smirnov.facade.Agent;
import by.smirnov.facade.Client;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;

import javax.websocket.EncodeException;
import java.io.IOException;

public class AgentLeaveMessageCommand implements AgentMessageCommand {
    private String template = "You are not talking somebody. Enter '/exit' command for exit or wait question from client.";
    private String leftMessage = "You have left chat with %s.";

    @Override
    public void handle(Message message, Agent person) throws IOException, EncodeException {
        if (person.getStatus().equals(Status.SLEEPING)) {
            person.send(new Message(Type.CONTENT, template));
        } else {
            Client client = person.unsubscribeById(message.getTo());
            client.send(message);
            client.unsubcribe();
            person.subscribeFromWaitingRoom();
            person.send(new Message(Type.CONTENT, String.format(leftMessage, client.getName())));
        }
    }
}
