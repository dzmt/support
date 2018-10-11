package by.smirnov.command.console;

import by.smirnov.connection.OutboundConnection;
import by.smirnov.messenger.Messenger;

public class RegisterConsoleCommand extends AbstractConsoleCommand {
    public RegisterConsoleCommand(Messenger messenger, OutboundConnection sender) {
        super(messenger, sender);
    }
}
