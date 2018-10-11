package by.smirnov.command.console;

import by.smirnov.connection.OutboundConnection;
import by.smirnov.message.Message;
import by.smirnov.messenger.Messenger;

import java.io.IOException;


public class ContentConsoleCommand extends AbstractConsoleCommand {
    public ContentConsoleCommand(Messenger messenger, OutboundConnection sender) {
        super(messenger, sender);
    }
}
