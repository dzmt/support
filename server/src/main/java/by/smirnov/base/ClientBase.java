package by.smirnov.base;

import by.smirnov.facade.User;

import java.util.List;

public interface ClientBase {

    User getClientById(String id);

    List<User> getWaitingRoom();
}
