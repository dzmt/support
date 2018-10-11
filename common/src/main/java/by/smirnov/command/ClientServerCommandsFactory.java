package by.smirnov.command;

import by.smirnov.command.server.ContentServerCommand;
import by.smirnov.command.server.InterlocutorServerCommand;
import by.smirnov.command.server.LeaveExitServerCommand;
import by.smirnov.command.server.UserServerCommand;
import by.smirnov.message.enumeration.Type;
import by.smirnov.messenger.Messenger;
import by.smirnov.view.View;

import java.util.HashMap;

public class ClientServerCommandsFactory  {
   private Messenger messenger;
   private View view;

    public ClientServerCommandsFactory(Messenger messenger, View view) {
        this.messenger = messenger;
        this.view = view;
    }

    public HashMap<Type, Command> createCommands() {
        HashMap<Type, Command> commands = new HashMap<>();

        commands.put(Type.CONTENT, new ContentServerCommand(messenger, view));
        commands.put(Type.USER, new UserServerCommand(messenger, view));
        commands.put(Type.INTERLOCUTOR, new InterlocutorServerCommand(messenger, view));
        commands.put(Type.LEAVE, new LeaveExitServerCommand(messenger, view));
        commands.put(Type.EXIT, new LeaveExitServerCommand(messenger, view));

        return commands;
    }
}
