package by.smirnov.command.client;

import by.smirnov.command.MessageCommand;
import by.smirnov.facade.User;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import by.smirnov.message.registry.MessageRegistry;

import javax.websocket.EncodeException;
import java.io.IOException;

public class ClientUnregisteredLeaveContentCommand implements MessageCommand {
    private String template = "Please, register in the system. Type command '/register <your_name>'.";
    @Override
    public void handle(Message message, User person) throws IOException, EncodeException {
        person.send(MessageRegistry.getMessage("unregistered.notregister"));
    }
}
