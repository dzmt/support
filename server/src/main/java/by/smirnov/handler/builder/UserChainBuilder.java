package by.smirnov.hander.builder;

import by.smirnov.hander.UserLinkHandler;
import by.smirnov.message.Message;

import javax.websocket.MessageHandler;

public class UserChainBuilder {
    private MessageHandler.Whole<Message> entryPoint;
    private UserLinkHandler next;


    public UserChainBuilder setEntryPoint(MessageHandler.Whole<Message> entryPoint) {
        this.entryPoint = entryPoint;
        next = (UserLinkHandler) this.entryPoint;
        return this;
    }

    public UserChainBuilder setNext(UserLinkHandler next) {
            this.next.setNext(next);
            this.next = next;
        return this;
    }

    public MessageHandler.Whole<Message> getEntryPoint() {
        return entryPoint;
    }
}
