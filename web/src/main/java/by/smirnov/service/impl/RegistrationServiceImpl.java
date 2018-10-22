package by.smirnov.service.impl;

import by.smirnov.builder.UserBuilder;
import by.smirnov.facade.User;
import by.smirnov.service.RegistrationService;

public class RegistrationServiceImpl implements RegistrationService {


    @Override
    public User registerClient(String name) {
        if (name != null) {
            User client = UserBuilder.createClient(name);
            return client;
        }

        return null;
    }

    @Override
    public User registerAgent(String name, int countActiveChat) {
        if (name != null) {
            User agent = UserBuilder.createAgent(name, countActiveChat);
            return agent;
        }
        return null;
    }
}
