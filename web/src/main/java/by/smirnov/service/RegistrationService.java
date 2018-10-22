package by.smirnov.service;

import by.smirnov.facade.User;

public interface RegistrationService {

    User registerClient(String name);
    User registerAgent(String name, int countActiveChat);
}
