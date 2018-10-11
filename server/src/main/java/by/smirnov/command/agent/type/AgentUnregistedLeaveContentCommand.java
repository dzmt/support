package by.smirnov.command.agent.type;

import by.smirnov.command.agent.AgentMessageCommand;
import by.smirnov.facade.Agent;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;

import javax.websocket.EncodeException;
import java.io.IOException;

public class AgentUnregistedLeaveContentCommand implements AgentMessageCommand {
    private String template = "Please, register in the system. Type command '/register <your_name>' or type '/exit' for exit";
    @Override
    public void handle(Message message, Agent person) throws IOException, EncodeException {
        person.send(new Message(Type.CONTENT, template));
    }
}
