package by.smirnov.service.impl;

import by.smirnov.base.ClientBase;
import by.smirnov.base.UserBase;
import by.smirnov.service.ClientService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ClientServiceImpl implements ClientService {
    private Gson gson = new GsonBuilder().create();
    private ClientBase clients = new UserBase();


    @Override
    public String getWaitingClients() {
        return gson.toJson(clients.getWaitingRoom());
    }

    @Override
    public String getClientById(String id) {
        return gson.toJson(clients.getClientById(id));
    }
}
