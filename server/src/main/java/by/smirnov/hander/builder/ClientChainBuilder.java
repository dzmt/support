package by.smirnov.hander.builder;

import by.smirnov.hander.ClientLinkHandler;
import by.smirnov.message.Message;

import javax.websocket.MessageHandler;

public class ClientChainBuilder {
    private MessageHandler.Whole<Message> entryPoint;
    private ClientLinkHandler next;


    public ClientChainBuilder setEntryPoint(MessageHandler.Whole<Message> entryPoint) {
        this.entryPoint = entryPoint;
        next = (ClientLinkHandler) this.entryPoint;
        return this;
    }

    public ClientChainBuilder setNext(ClientLinkHandler next) {
            this.next.setNext(next);
            this.next = next;
        return this;
    }

    public MessageHandler.Whole<Message> getEntryPoint() {
        return entryPoint;
    }
}
