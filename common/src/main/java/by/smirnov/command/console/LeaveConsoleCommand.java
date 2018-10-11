package by.smirnov.command.console;

import by.smirnov.connection.OutboundConnection;
import by.smirnov.message.Message;
import by.smirnov.messenger.Messenger;
import by.smirnov.messenger.Status;

import java.io.IOException;

public class LeaveConsoleCommand extends AbstractConsoleCommand {
    public LeaveConsoleCommand(Messenger messenger, OutboundConnection sender) {
        super(messenger, sender);
    }

    @Override
    public void execute(Message message) throws IOException {
        messenger.setStatus(Status.READY);
        addressing(message);
        sender.send(message);
    }
}