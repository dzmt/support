package by.smirnov.command.client;

import by.smirnov.command.MessageCommand;
import by.smirnov.facade.User;
import by.smirnov.message.Message;
import by.smirnov.message.registry.MessageRegistry;

import javax.websocket.EncodeException;
import java.io.IOException;

public class ClientAlreadyRegisterMessageCommand implements MessageCommand {

    @Override
    public void handle(Message message, User person) throws IOException, EncodeException {
        person.send(MessageRegistry.getMessage("client.registered.register"));
    }
}
