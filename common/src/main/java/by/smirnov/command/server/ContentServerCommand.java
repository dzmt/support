package by.smirnov.command.server;

import by.smirnov.message.Message;
import by.smirnov.messenger.Messenger;
import by.smirnov.view.View;

import java.io.IOException;

public class ContentServerCommand extends AbstractrServerCommand {
    public ContentServerCommand(Messenger messenger, View view) {
        super(messenger, view);
    }

    @Override
    public void execute(Message message) throws IOException {
        view.show(message);
    }
}
