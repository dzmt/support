package by.smirnov.command.client.status;

import by.smirnov.command.agent.status.AgentStatusCommandSwitcher;
import by.smirnov.command.client.ClientMessageCommand;
import by.smirnov.command.client.ClientStatusCommand;
import by.smirnov.facade.Client;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.HashMap;

public class ClientStatusCommandSwitcher implements ClientStatusCommand {
    private static final Logger logger = LogManager.getLogger(ClientStatusCommandSwitcher.class);

    protected HashMap<Type, ClientMessageCommand> messageCommand = new HashMap<>();

    @Override
    public void setCommand(Type type, ClientMessageCommand command) {
        messageCommand.put(type, command);
    }

    @Override
    public void handle(Message message, Client person) throws IOException, EncodeException {

        logger.info(String.format("client[%s] status[%s] before handling - message: %s", person.getId(), person.getStatus(), message));

        messageCommand.get(message.getType()).handle(message, person);

        logger.info(String.format("client[%s] status[%s] after handling - message: %s", person.getId(), person.getStatus(), message));
    }
}
