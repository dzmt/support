package by.smirnov.command;

import by.smirnov.command.console.ContentConsoleCommand;
import by.smirnov.command.console.ExitConsoleCommand;
import by.smirnov.command.console.LeaveConsoleCommand;
import by.smirnov.command.console.RegisterConsoleCommand;
import by.smirnov.command.server.UserServerCommand;
import by.smirnov.connection.OutboundConnection;
import by.smirnov.message.enumeration.Type;
import by.smirnov.messenger.Messenger;

import java.util.HashMap;

import static by.smirnov.message.enumeration.Type.USER;

public class ClientConsoleCommandsFactory {
    private Messenger messenger;
    private OutboundConnection sender;

    public ClientConsoleCommandsFactory(Messenger messenger, OutboundConnection sender) {
        this.messenger = messenger;
        this.sender = sender;
    }

    public HashMap<Type, Command> createCommands() {
        HashMap<Type, Command> commands = new HashMap<>();

        commands.put(Type.CONTENT, new ContentConsoleCommand(messenger, sender));
        commands.put(Type.EXIT, new ExitConsoleCommand(messenger, sender));
        commands.put(Type.LEAVE, new LeaveConsoleCommand(messenger, sender));
        commands.put(Type.REGISTER, new RegisterConsoleCommand(messenger, sender));

        return commands;
    }



}
