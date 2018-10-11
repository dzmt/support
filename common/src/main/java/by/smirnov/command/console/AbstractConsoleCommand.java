package by.smirnov.command.console;

import by.smirnov.command.Command;
import by.smirnov.connection.OutboundConnection;
import by.smirnov.message.Message;
import by.smirnov.messenger.Messenger;

import java.io.IOException;


public class AbstractConsoleCommand implements Command {
    protected Messenger messenger;
    protected OutboundConnection sender;

    public AbstractConsoleCommand(Messenger messenger, OutboundConnection sender) {
        this.messenger = messenger;
        this.sender = sender;
    }

    @Override
    public void execute(Message message) throws IOException {
        addressing(message);
        sender.send(message);
    }

    protected void addressing(Message message) {
        message.setFrom(messenger.getCurrent().getId());
        message.setTo(messenger.getInterlocutor().getId());
    }
}
