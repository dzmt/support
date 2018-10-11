package by.smirnov.command.agent.type;

import by.smirnov.command.agent.AgentMessageCommand;
import by.smirnov.facade.Agent;
import by.smirnov.message.Message;
import by.smirnov.message.util.MessageUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.websocket.EncodeException;
import java.io.IOException;

import static by.smirnov.enumeration.Status.SLEEPING;

public class RegisterAgentMessageCommand implements AgentMessageCommand {
    private static final Logger logger = LogManager.getLogger(RegisterAgentMessageCommand.class);

    @Override
    public void handle(Message message, Agent person) throws IOException, EncodeException {

        person.setName(MessageUtil.getNameFromRegistrationMessage(message));
        person.setMaxCountActiveChat(MessageUtil.getMaxChatFromRegistrationMessage(message));
        person.setStatus(SLEEPING);
        person.addToBase();
        person.send(MessageUtil.createReportBackeMessage(person.getId(), person.getName()));
        person.subscribeFromWaitingRoom();

        logger.info("agent " + person.getId() + " " + person.getStatus() + " are registered");
    }
}
