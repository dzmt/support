package by.smirnov.hander.link;

import by.smirnov.message.Message;
import by.smirnov.facade.Client;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class EndPointClientLinkHandler extends AbstractClientLinkHandler {
    private static final Logger logger = LogManager.getLogger(EndPointClientLinkHandler.class);

    @Override
    public void handle(Message message, Client person) {
        logger.info("end point: " + message + " " + person.getStatus());
    }
}
