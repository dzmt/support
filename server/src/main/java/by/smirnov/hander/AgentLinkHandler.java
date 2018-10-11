package by.smirnov.hander;

import by.smirnov.facade.Agent;
import by.smirnov.facade.Client;
import by.smirnov.message.Message;

public interface AgentLinkHandler {
    void setNext(AgentLinkHandler next);

    void handle(Message message, Agent person);
}
