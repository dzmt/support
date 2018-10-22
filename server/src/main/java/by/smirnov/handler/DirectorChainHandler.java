package by.smirnov.handler;

import by.smirnov.command.StatusCommandDirector;
import by.smirnov.facade.User;
import by.smirnov.handler.builder.UserChainBuilder;
import by.smirnov.handler.link.*;
import by.smirnov.message.Message;

import javax.websocket.MessageHandler;

public class DirectorChainHandler {

    public static MessageHandler.Whole<Message> buildClientChain(User person) {
        UserChainBuilder builder = new UserChainBuilder();

        builder.setEntryPoint(new UserEntryPointUserLinkHandler(person, StatusCommandDirector.getDefaultClientCommands()))
                .setNext(new EndPointUserLinkHandler());
        return builder.getEntryPoint();
    }

    public static MessageHandler.Whole<Message> buildAgentChain(User person) {
        UserChainBuilder builder = new UserChainBuilder();

        builder.setEntryPoint(new UserEntryPointUserLinkHandler(person, StatusCommandDirector.getDefaultAgentCommands()));

        return builder.getEntryPoint();
    }
}
