package by.smirnov.messenger;

import by.smirnov.command.ClientConsoleCommandsFactory;
import by.smirnov.command.ClientServerCommandsFactory;
import by.smirnov.connection.IncomingConnection;
import by.smirnov.connection.impl.IncomingConnectiontImpl;
import by.smirnov.connection.impl.WebsocketConnection;
import by.smirnov.enumeration.Role;
import by.smirnov.io.StringInputData;
import by.smirnov.message.converter.StringMessageConverter;
import by.smirnov.message.converter.impl.StringMessageConverterImpl;
import by.smirnov.message.util.StringMessageParser;
import by.smirnov.message.util.impl.StringMessageParserImpl;
import by.smirnov.observer.MessageHandler;
import by.smirnov.observer.MessageListener;
import by.smirnov.observer.impl.MessageHandlerImpl;
import by.smirnov.observer.impl.MessageListenerImpl;
import by.smirnov.user.User;
import by.smirnov.view.View;
import by.smirnov.view.impl.PrintStreamView;
import org.apache.logging.log4j.core.net.MimeMessageBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class MessengerBuilder {
    private Messenger messenger;
    private String url;
    private Role role;

    public Messenger create(Role role, String url) throws URISyntaxException {
        messenger = new Messenger();

        messenger.setRole(role);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringInputData in = new StringInputData(new BufferedReader(reader));

        StringMessageParser parser = new StringMessageParserImpl();
        StringMessageConverter converter = new StringMessageConverterImpl(parser);

        IncomingConnection incomingConnection = new IncomingConnectiontImpl(in, converter);

        URI uri = new URI(url);
        WebsocketConnection client = new WebsocketConnection(uri);
        messenger.setConnection(client);

        //create view
        View view = new PrintStreamView(messenger, System.out);

        //client message cor
        ClientConsoleCommandsFactory factory = new ClientConsoleCommandsFactory(messenger, client);
        MessageHandler consoleHandler = new MessageHandlerImpl(factory.createCommands());

        ClientServerCommandsFactory serverCommandsFactory = new ClientServerCommandsFactory(messenger, view);
        MessageHandler serverHandler = new MessageHandlerImpl(serverCommandsFactory.createCommands());

        //client message listener
        MessageListener consoleListener = new MessageListenerImpl(incomingConnection);
        MessageListener socketListener = new MessageListenerImpl(client);

        consoleListener.subscribe(consoleHandler);
        socketListener.subscribe(serverHandler);

        messenger.setUserListener(consoleListener);
        messenger.setServerListener(socketListener);

        return messenger;
    }
}
