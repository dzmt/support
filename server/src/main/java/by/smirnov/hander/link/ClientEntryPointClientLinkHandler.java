package by.smirnov.hander.link;

import by.smirnov.command.client.ClientStatusCommand;
import by.smirnov.command.StatusCommandDirector;
import by.smirnov.enumeration.Status;
import by.smirnov.hander.ClientLinkHandler;
import by.smirnov.message.Message;
import by.smirnov.facade.Client;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.websocket.EncodeException;
import javax.websocket.MessageHandler;
import java.io.IOException;
import java.util.HashMap;

public class ClientEntryPointClientLinkHandler implements MessageHandler.Whole<Message>, ClientLinkHandler {
    private static final Logger logger = LogManager.getLogger(ClientEntryPointClientLinkHandler.class);
    private Client person;
    private ClientLinkHandler next;


    private HashMap<Status, ClientStatusCommand> commands = StatusCommandDirector.getDefaultClientCommands();


    public ClientEntryPointClientLinkHandler(Client person) {
        this.person = person;
    }

    @Override
    public void setNext(ClientLinkHandler next) {
        this.next = next;
    }

    @Override
    public void handle(Message message, Client person) {
        next.handle(message, person);
    }

    @Override
    public void onMessage(Message message) {
        try {
            commands.get(person.getStatus()).handle(message, person);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }

    }
}
