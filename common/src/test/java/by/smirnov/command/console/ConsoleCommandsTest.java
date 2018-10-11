package by.smirnov.command.console;

import by.smirnov.connection.OutboundConnection;
import by.smirnov.message.Message;
import by.smirnov.messenger.Messenger;
import by.smirnov.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.*;

class ConsoleCommandsTest {
    private static Messenger messenger;
    private static OutboundConnection connection;
    private static Message message;

    @BeforeAll
    static void beforeAll() {
        messenger = mock(Messenger.class);
        connection = mock(OutboundConnection.class);
        message = mock(Message.class);
        User from = mock(User.class);
        User to = mock(User.class);
        doReturn(from).when(messenger).getCurrent();
        doReturn(to).when(messenger).getInterlocutor();
    }

    @Test()
    void exitConsoleCommandInvokeStopMethod() {
        ExitConsoleCommand command = new ExitConsoleCommand(messenger, connection);

        try {
            command.execute(message);
            verify(messenger, Mockito.atLeastOnce()).stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void exitConsoleCommandInvokeCloseMethod() {
        ExitConsoleCommand command = new ExitConsoleCommand(messenger, connection);
        try {
            command.execute(message);
            verify(connection, Mockito.atLeastOnce()).send(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void registerConsoleCommandInvokeSendMethod() {
        RegisterConsoleCommand command = new RegisterConsoleCommand(messenger, connection);
        doReturn("Dzmitry").when(message).getText();

        try {
            command.execute(message);
            verify(connection, Mockito.atLeastOnce()).send(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}