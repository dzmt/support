package by.smirnov.command.console;

import by.smirnov.connection.OutboundConnection;
import by.smirnov.message.Message;
import by.smirnov.messenger.Messenger;

import java.io.IOException;

public class ExitConsoleCommand extends AbstractConsoleCommand {

    public ExitConsoleCommand(Messenger messenger, OutboundConnection sender) {
        super(messenger, sender);
    }

    @Override
    public void execute(Message message) throws IOException {
        addressing(message);
        sender.send(message);
        messenger.stop();
    }
}
