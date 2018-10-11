package by.smirnov.hander;

import by.smirnov.facade.Agent;
import by.smirnov.hander.builder.AgentChainBuilder;
import by.smirnov.hander.builder.ClientChainBuilder;
import by.smirnov.hander.link.*;
import by.smirnov.message.Message;
import by.smirnov.facade.Client;

import javax.websocket.MessageHandler;

public class DirectorChainHandler {

    public static MessageHandler.Whole<Message> buildClientChain(Client person) {
        ClientChainBuilder builder = new ClientChainBuilder();

        builder.setEntryPoint(new ClientEntryPointClientLinkHandler(person))
                .setNext(new EndPointClientLinkHandler());
        return builder.getEntryPoint();
    }

    public static MessageHandler.Whole<Message> buildAgentChain(Agent person) {
        AgentChainBuilder builder = new AgentChainBuilder();

        builder.setEntryPoint(new AgentEntryPointLinkHandler(person));

        return builder.getEntryPoint();
    }
}
