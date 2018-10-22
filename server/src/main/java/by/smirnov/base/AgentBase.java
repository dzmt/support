package by.smirnov.base;

import by.smirnov.facade.User;

import java.util.List;

public interface AgentBase {

    List<User> getAgents();

    List<User> getReadyAgents();

    User getAgentById(String id);
}
