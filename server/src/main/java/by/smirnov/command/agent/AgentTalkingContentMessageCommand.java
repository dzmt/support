package by.smirnov.command.agent.type;

import by.smirnov.command.agent.AgentMessageCommand;
import by.smirnov.facade.User;
import by.smirnov.message.Message;

import javax.websocket.EncodeException;
import java.io.IOException;

public class AgentTalkingContentMessageCommand implements AgentMessageCommand {
    @Override
    public void handle(Message message, User person) throws IOException, EncodeException {
        person.notifySubscriber(message);
    }
}
