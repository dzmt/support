package by.smirnov.command.client;

import by.smirnov.command.MessageCommand;
import by.smirnov.facade.User;
import by.smirnov.message.Message;
import by.smirnov.message.util.MessageUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.websocket.EncodeException;
import java.io.IOException;

import static by.smirnov.enumeration.Status.SLEEPING;

public class RegisterClientMessageCommand implements MessageCommand {
    private static final Logger logger = LogManager.getLogger(RegisterClientMessageCommand.class);

    @Override
    public void handle(Message message, User person) throws IOException, EncodeException {
        person.setName(MessageUtil.getNameFromRegistrationMessage(message));
        person.setMaxCountActiveChat(MessageUtil.getMaxChatFromRegistrationMessage(message));
        person.setStatus(SLEEPING);
        person.addToBase();
        person.send(MessageUtil.createReportBackMessage(person.getId(), person.getName()));

        logger.info("client " + person.getId() + " " + person.getStatus() + " are registered");
    }
}
