package by.smirnov.hander.link.client;

import by.smirnov.enumeration.Status;
import by.smirnov.facade.Client;
import by.smirnov.hander.link.AbstractClientLinkHandler;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;

public class ExitClientLinkHandler extends AbstractClientLinkHandler {

    @Override
    public void handle(Message message, Client person) {
        if (!message.getType().equals(Type.EXIT)) {
            next.handle(message, person);
        } else {
            person.clearBuffer();
            person.setStatus(Status.UNREGISTERED);
        }
    }
}
