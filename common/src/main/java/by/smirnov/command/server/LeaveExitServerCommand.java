package by.smirnov.command.server;

import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import by.smirnov.messenger.Messenger;
import by.smirnov.messenger.Status;
import by.smirnov.view.View;

import java.io.IOException;

public class LeaveExitServerCommand extends AbstractrServerCommand {
    private static final String leaveMessage ="%s has left chat.";

    public LeaveExitServerCommand(Messenger messenger, View view) {
        super(messenger, view);
    }

    @Override
    public void execute(Message message) throws IOException {
        messenger.setStatus(Status.READY);
        view.show(new Message(Type.CONTENT, String.format(leaveMessage, messenger.getInterlocutor().getName())));
        messenger.getInterlocutor().setId(null);
        messenger.getInterlocutor().setName(null);
    }
}
