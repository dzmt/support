package by.smirnov.observer.impl;

import by.smirnov.command.Command;

import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import by.smirnov.observer.MessageHandler;

import java.util.HashMap;


public class MessageHandlerImpl implements MessageHandler {
    private HashMap<Type, Command> commands;

    public MessageHandlerImpl(HashMap<Type, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void handle(Message message) {
        try {
            commands.get(message.getType()).execute(message);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
