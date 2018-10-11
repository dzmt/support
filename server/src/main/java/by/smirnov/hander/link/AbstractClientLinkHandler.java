package by.smirnov.hander.link;

import by.smirnov.hander.ClientLinkHandler;
import by.smirnov.message.Message;
import by.smirnov.facade.Client;

public class AbstractClientLinkHandler implements ClientLinkHandler {
    protected ClientLinkHandler next;

    @Override
    public void setNext(ClientLinkHandler next) {
        this.next = next;
    }

    @Override
    public void handle(Message message, Client person) {
        next.handle(message, person);
    }
}
