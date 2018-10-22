package by.smirnov.hander.link;

import by.smirnov.command.StatusCommand;
import by.smirnov.command.StatusCommandDirector;
import by.smirnov.enumeration.Status;
import by.smirnov.facade.User;
import by.smirnov.hander.UserLinkHandler;
import by.smirnov.message.Message;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.websocket.EncodeException;
import javax.websocket.MessageHandler;
import java.io.IOException;
import java.util.HashMap;

public class UserEntryPointUserLinkHandler implements MessageHandler.Whole<Message>, UserLinkHandler {
    private static final Logger logger = LogManager.getLogger(UserEntryPointUserLinkHandler.class);
    private User person;
    private UserLinkHandler next;


    private HashMap<Status, StatusCommand> commands = StatusCommandDirector.getDefaultClientCommands();


    public UserEntryPointUserLinkHandler(User person) {
        this.person = person;
    }

    @Override
    public void setNext(UserLinkHandler next) {
        this.next = next;
    }

    @Override
    public void handle(Message message, User person) {
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
