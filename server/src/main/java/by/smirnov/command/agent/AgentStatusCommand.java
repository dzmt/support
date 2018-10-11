package by.smirnov.command.agent;

import by.smirnov.facade.Agent;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;

import javax.websocket.EncodeException;
import java.io.IOException;

public interface AgentStatusCommand {
    void setCommand(Type type, AgentMessageCommand command);

    void handle(Message message, Agent person) throws IOException, EncodeException;
}
