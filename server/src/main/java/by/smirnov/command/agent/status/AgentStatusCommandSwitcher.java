package by.smirnov.command.agent.status;

import by.smirnov.command.agent.AgentMessageCommand;
import by.smirnov.command.agent.AgentStatusCommand;
import by.smirnov.facade.Agent;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.HashMap;

public class AgentStatusCommandSwitcher implements AgentStatusCommand {
    private static final Logger logger = LogManager.getLogger(AgentStatusCommandSwitcher.class);

    protected HashMap<Type, AgentMessageCommand> messageCommand = new HashMap<>();

    @Override
    public void setCommand(Type type, AgentMessageCommand command) {
        messageCommand.put(type, command);
    }

    @Override
    public void handle(Message message, Agent person) throws IOException, EncodeException {

        logger.info(String.format("agent[%s] status[%s] before handling - message: %s", person.getId(), person.getStatus(), message));

        messageCommand.get(message.getType()).handle(message, person);

        logger.info(String.format("agent[%s] status[%s] after handling - message: %s", person.getId(), person.getStatus(), message));
    }
}
