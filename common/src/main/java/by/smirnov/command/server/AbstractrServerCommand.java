package by.smirnov.command.server;

import by.smirnov.command.Command;
import by.smirnov.message.Message;
import by.smirnov.messenger.Messenger;
import by.smirnov.view.View;

import java.io.IOException;

public abstract class AbstractrServerCommand implements Command {
    protected Messenger messenger;
    protected View view;

    public AbstractrServerCommand(Messenger messenger, View view) {
        this.messenger = messenger;
        this.view = view;
    }

    @Override
    public abstract void execute(Message message) throws IOException;
}
