package by.smirnov.hander.link;

import by.smirnov.facade.User;
import by.smirnov.hander.UserLinkHandler;
import by.smirnov.message.Message;

public class AbstractUserLinkHandler implements UserLinkHandler {
    protected UserLinkHandler next;

    @Override
    public void setNext(UserLinkHandler next) {
        this.next = next;
    }

    @Override
    public void handle(Message message, User person) {
        next.handle(message, person);
    }
}
