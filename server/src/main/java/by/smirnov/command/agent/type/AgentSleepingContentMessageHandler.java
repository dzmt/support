package by.smirnov.command.agent.type;

import by.smirnov.command.agent.AgentMessageCommand;
import by.smirnov.facade.Agent;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import by.smirnov.message.registry.MessageRegistry;

import javax.websocket.EncodeException;
import java.io.IOException;

public class AgentSleepingContentMessageHandler implements AgentMessageCommand {
    @Override
    public void handle(Message message, Agent person) throws IOException, EncodeException {
        person.send(MessageRegistry.getMessage("agent.sleeping.content"));
    }
}
