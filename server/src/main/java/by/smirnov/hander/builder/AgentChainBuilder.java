package by.smirnov.hander.builder;

import by.smirnov.hander.AgentLinkHandler;
import by.smirnov.hander.ClientLinkHandler;
import by.smirnov.message.Message;

import javax.websocket.MessageHandler;

public class AgentChainBuilder {
    private MessageHandler.Whole<Message> entryPoint;
    private AgentLinkHandler next;


    public AgentChainBuilder setEntryPoint(MessageHandler.Whole<Message> entryPoint) {
        this.entryPoint = entryPoint;
        next = (AgentLinkHandler) this.entryPoint;
        return this;
    }

    public AgentChainBuilder setNext(AgentLinkHandler next) {
        this.next.setNext(next);
        this.next = next;
        return this;
    }

    public MessageHandler.Whole<Message> getEntryPoint() {
        return entryPoint;
    }
}
