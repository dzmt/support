package by.smirnov.hander;

import by.smirnov.facade.User;
import by.smirnov.hander.builder.UserChainBuilder;
import by.smirnov.hander.link.*;
import by.smirnov.message.Message;

import javax.websocket.MessageHandler;

public class DirectorChainHandler {

    public static MessageHandler.Whole<Message> buildClientChain(User person) {
        UserChainBuilder builder = new UserChainBuilder();

        builder.setEntryPoint(new UserEntryPointUserLinkHandler(person))
                .setNext(new EndPointUserLinkHandler());
        return builder.getEntryPoint();
    }

    public static MessageHandler.Whole<Message> buildAgentChain(User person) {
        UserChainBuilder builder = new UserChainBuilder();

        builder.setEntryPoint(new UserEntryPointUserLinkHandler(person));

        return builder.getEntryPoint();
    }
}
