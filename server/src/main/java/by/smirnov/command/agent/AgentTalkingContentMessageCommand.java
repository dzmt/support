package by.smirnov.command.agent;

import by.smirnov.command.MessageCommand;
import by.smirnov.facade.User;
import by.smirnov.message.Message;

import javax.websocket.EncodeException;
import java.io.IOException;

public class AgentTalkingContentMessageCommand implements MessageCommand {
    @Override
    public void handle(Message message, User person) throws IOException, EncodeException {
        person.notifySubscriber(message);
    }
}
