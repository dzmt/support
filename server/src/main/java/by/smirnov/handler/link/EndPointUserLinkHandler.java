package by.smirnov.handler.link;

import by.smirnov.facade.User;
import by.smirnov.message.Message;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class EndPointUserLinkHandler extends AbstractUserLinkHandler {
    private static final Logger logger = LogManager.getLogger(EndPointUserLinkHandler.class);

    @Override
    public void handle(Message message, User person) {
        logger.info("end point: " + message + " " + person.getStatus());
    }
}
