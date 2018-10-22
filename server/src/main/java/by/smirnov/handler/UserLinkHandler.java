package by.smirnov.hander;

import by.smirnov.facade.User;
import by.smirnov.message.Message;

public interface UserLinkHandler {

    void setNext(UserLinkHandler next);

    void handle(Message message, User person);
}
