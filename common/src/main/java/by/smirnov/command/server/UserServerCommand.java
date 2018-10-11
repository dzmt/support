package by.smirnov.command.server;

import by.smirnov.message.Message;
import by.smirnov.message.util.MessageUtil;
import by.smirnov.messenger.Messenger;
import by.smirnov.messenger.Status;
import by.smirnov.user.User;
import by.smirnov.view.View;

import java.io.IOException;

public class UserServerCommand extends AbstractrServerCommand {

    public UserServerCommand(Messenger messenger, View view) {
        super(messenger, view);
    }

    @Override
    public void execute(Message message) throws IOException {
        String[] tokens = message.getText().split(" ");
        String id = tokens[0];
        String name = tokens[1];

        messenger.setStatus(Status.READY);

        User current = messenger.getCurrent();
        current.setId(id);
        current.setName(name);

        view.show(MessageUtil.createGreetingMessage(name, messenger.getRole()));
    }
}
