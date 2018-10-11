package by.smirnov.hander.link;

import by.smirnov.command.agent.AgentStatusCommand;
import by.smirnov.command.client.ClientStatusCommand;
import by.smirnov.command.StatusCommandDirector;
import by.smirnov.enumeration.Status;
import by.smirnov.facade.Agent;
import by.smirnov.hander.AgentLinkHandler;
import by.smirnov.message.Message;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.websocket.EncodeException;
import javax.websocket.MessageHandler;
import java.io.IOException;
import java.util.HashMap;

public class AgentEntryPointLinkHandler  implements MessageHandler.Whole<Message>, AgentLinkHandler {

    private static final Logger logger = LogManager.getLogger(ClientEntryPointClientLinkHandler.class);
    private Agent person;
    private AgentLinkHandler next;


    private HashMap<Status, AgentStatusCommand> commands = StatusCommandDirector.getDefaultAgentCommands();


    public AgentEntryPointLinkHandler(Agent person) {
        this.person = person;
    }

    @Override
    public void setNext(AgentLinkHandler next) {
        this.next = next;
    }

    @Override
    public void handle(Message message, Agent person) {
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
