package by.smirnov.command.agent;

import by.smirnov.command.MessageCommand;
import by.smirnov.facade.User;
import by.smirnov.message.Message;
import by.smirnov.message.registry.MessageRegistry;

import javax.websocket.EncodeException;
import java.io.IOException;

public class AgentUnregistedLeaveContentCommand implements MessageCommand {
    @Override
    public void handle(Message message, User person) throws IOException, EncodeException {
        person.send(MessageRegistry.getMessage("unregistered.notregister"));
    }
}
