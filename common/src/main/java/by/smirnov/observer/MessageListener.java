package by.smirnov.observer;

import by.smirnov.message.Message;

import java.io.IOException;

public interface MessageListener {

    void subscribe(MessageHandler messageHandler);
    void unsubscribe(MessageHandler messageHandler);

    void notify(Message message);

    void listen() throws IOException;
}
