package by.smirnov.hander;

import by.smirnov.message.Message;
import by.smirnov.facade.Client;

public interface ClientLinkHandler {

    void setNext(ClientLinkHandler next);

    void handle(Message message, Client person);
}
