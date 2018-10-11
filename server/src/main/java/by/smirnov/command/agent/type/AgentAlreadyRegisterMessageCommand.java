package by.smirnov.command.agent.type;

import by.smirnov.command.agent.AgentMessageCommand;
import by.smirnov.facade.Agent;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;

import javax.websocket.EncodeException;
import java.io.IOException;

public class AgentAlreadyRegisterMessageCommand implements AgentMessageCommand {
    private String template = "You are already registered. You can ask a question or type '/exit' for exit from system";

    @Override
    public void handle(Message message, Agent person) throws IOException, EncodeException {
        person.send(new Message(Type.CONTENT, template));
    }
}
