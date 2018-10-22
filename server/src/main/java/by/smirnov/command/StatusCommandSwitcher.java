package by.smirnov.command;

import by.smirnov.facade.User;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.HashMap;

public class StatusCommandSwitcher implements StatusCommand {
    private static final Logger logger = LogManager.getLogger(StatusCommandSwitcher.class);

    protected HashMap<Type, MessageCommand> messageCommand = new HashMap<>();

    @Override
    public void setCommand(Type type, MessageCommand command) {
        messageCommand.put(type, command);
    }

    @Override
    public void handle(Message message, User person) throws IOException, EncodeException {

        logger.info(String.format("agent[%s] status[%s] before handling - message: %s", person.getId(), person.getStatus(), message));

        messageCommand.get(message.getType()).handle(message, person);

        logger.info(String.format("agent[%s] status[%s] after handling - message: %s", person.getId(), person.getStatus(), message));
    }
}
