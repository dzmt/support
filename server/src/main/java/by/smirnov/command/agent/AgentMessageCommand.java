package by.smirnov.command.agent;

import by.smirnov.facade.Agent;
import by.smirnov.facade.Client;
import by.smirnov.message.Message;

import javax.websocket.EncodeException;
import java.io.IOException;

public interface AgentMessageCommand {

    void handle(Message message, Agent person) throws IOException, EncodeException;
}
