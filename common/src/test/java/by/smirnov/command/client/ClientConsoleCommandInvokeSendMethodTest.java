package by.smirnov.command.client;

import by.smirnov.command.ClientConsoleCommandsFactory;
import by.smirnov.command.Command;
import by.smirnov.command.console.ContentConsoleCommand;
import by.smirnov.connection.OutboundConnection;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import by.smirnov.messenger.Messenger;
import by.smirnov.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.io.IOException;
import java.util.HashMap;

import static org.mockito.Mockito.*;

public class ClientConsoleCommandInvokeSendMethodTest {

    private static HashMap<Type, Command> commands;
    private static Messenger messenger;
    private static OutboundConnection connection;

    @BeforeAll
    static void beforAll() {
        User current = mock(User.class);
        User interlocutor = mock(User.class);

        messenger = mock(Messenger.class);
        doReturn(current).when(messenger).getCurrent();
        doReturn(interlocutor).when(messenger).getInterlocutor();
        connection = mock(OutboundConnection.class);
        commands = new ClientConsoleCommandsFactory(messenger, connection).createCommands();
    }

    @DisplayName("Test commands for they invoke send command of OutboundConnection")
    @ParameterizedTest
    @EnumSource(value = Type.class, names = {"CONTENT", "EXIT", "LEAVE", "REGISTER"})

    void commandExecuteSendMethod(Type type) {
        Message message = mock(Message.class);
        Command command = commands.get(type);

        try {
            command.execute(message);
            verify(connection).send(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
