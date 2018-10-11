package by.smirnov.observer.impl;

import by.smirnov.connection.IncomingConnection;
import by.smirnov.message.Message;
import by.smirnov.observer.MessageHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageListenerImpl implements by.smirnov.observer.MessageListener {

    private IncomingConnection incomingConnection;
    private List<MessageHandler> messageHandlers = new ArrayList<>();

    public MessageListenerImpl(IncomingConnection incomingConnection) {
        this.incomingConnection = incomingConnection;
    }

    @Override
    public void subscribe(MessageHandler messageHandler) {
        messageHandlers.add(messageHandler);
    }

    @Override
    public void unsubscribe(MessageHandler messageHandler) {
        messageHandlers.remove(messageHandler);
    }

    @Override
    public void notify(Message message) {
        messageHandlers.forEach(messageHandler -> messageHandler.handle(message));
    }

    @Override
    public void listen() throws IOException {
        if (incomingConnection.available()) {
            Message message = incomingConnection.receive();
            notify(message);
        }
    }
}
