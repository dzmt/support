package by.smirnov.command.client;

import by.smirnov.command.ClientServerCommandsFactory;
import by.smirnov.command.Command;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import by.smirnov.messenger.Messenger;
import by.smirnov.view.View;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.io.IOException;
import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ClientServerCommandInvokeShowMethodTest {
    private static HashMap<Type, Command> commands;
    private static Messenger messenger;
    private static View view;

    @BeforeAll
    static void beforAll() {
        messenger = mock(Messenger.class);
        view = mock(View.class);
        commands = new ClientServerCommandsFactory(messenger, view).createCommands();
    }

    @DisplayName("Test commands for they invoke show method of view")
    @ParameterizedTest
    @EnumSource(value = Type.class, names = {"CONTENT"})

    void commandExecuteSendMethod(Type type) {
        Message message = mock(Message.class);
        Command command = commands.get(type);

        try {
            command.execute(message);
            verify(view).show(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
