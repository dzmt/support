package by.smirnov.command.server;

import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import by.smirnov.messenger.Messenger;
import by.smirnov.messenger.Status;
import by.smirnov.user.User;
import by.smirnov.view.View;

import java.io.IOException;

public class InterlocutorServerCommand extends AbstractrServerCommand {
    private static final String template = "You was connected to the %s.";

    public InterlocutorServerCommand(Messenger messenger, View view) {
        super(messenger, view);
    }

    @Override
    public void execute(Message message) throws IOException {
        String[] tokens = message.getText().split(" ");
        String id = tokens[0];
        String name = message.getText().substring(id.length() + 1);

        User interlocutor = messenger.getInterlocutor();
        interlocutor.setName(name);
        interlocutor.setId(id);

        view.show(new Message(Type.CONTENT, String.format(template, name)));

        messenger.setStatus(Status.TALKING);

    }
}
