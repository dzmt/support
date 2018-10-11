package by.smirnov.observer;

import by.smirnov.message.Message;

public interface MessageHandler {

    void handle(Message message);
}
