package by.smirnov.model;

import by.smirnov.facade.User;

public class Room {
    private User client;
    private User agent;

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public User getAgent() {
        return agent;
    }

    public void setAgent(User agent) {
        this.agent = agent;
    }
}