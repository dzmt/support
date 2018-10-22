package by.smirnov.command.agent;

import by.smirnov.command.MessageCommand;
import by.smirnov.facade.User;
import by.smirnov.message.Message;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.Collection;

public class AgentExitMessageCommand implements MessageCommand {

    @Override
    public void handle(Message message, User person) throws IOException, EncodeException {
        person.close();
    }
}
