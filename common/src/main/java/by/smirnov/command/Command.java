package by.smirnov.command;

import by.smirnov.message.Message;

import java.io.IOException;

public interface Command {
    void execute(Message message) throws IOException;
}
