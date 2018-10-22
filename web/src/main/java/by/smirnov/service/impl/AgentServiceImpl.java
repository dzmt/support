package by.smirnov.service.impl;

import by.smirnov.base.AgentBase;
import by.smirnov.base.UserBase;
import by.smirnov.facade.User;
import by.smirnov.service.AgentService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class AgentServiceImpl implements AgentService {
    private Gson gson = new GsonBuilder().create();
    private AgentBase agents = new UserBase();

    @Override
    public String getAgents(int parts, int count) {
        return gson.toJson(getUsers(agents.getAgents(), parts, count));
    }

    @Override
    public String getAgentById(String id) {
        return gson.toJson(agents.getAgentById(id));
    }

    @Override
    public String getReadyAgents(int parts, int count) {
        return gson.toJson(getUsers(agents.getReadyAgents(), parts, count));
    }

    @Override
    public String getSizeReadyAgents() {
        return gson.toJson(agents.getReadyAgents().size());
    }

    private List<User> getUsers(List<User> users, int page, int count) {
        int skip = page != 0 ? (page - 1) * count : page;
        int end = count != 0 ? count : users.size();
        return users.stream()
                .skip(skip)
                .limit(end)
                .collect(Collectors.toList());
    }
}
