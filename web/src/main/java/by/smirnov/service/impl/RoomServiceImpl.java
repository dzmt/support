package by.smirnov.service.impl;

import by.smirnov.base.RoomBase;
import by.smirnov.base.UserBase;
import by.smirnov.facade.User;
import by.smirnov.model.Room;
import by.smirnov.service.RoomService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class RoomServiceImpl implements RoomService {
    private Gson gson = new GsonBuilder().create();
    private RoomBase base = new UserBase();

    @Override
    public String getRooms(int parts, int count) {
        return gson.toJson(getRooms(base.getRooms(), parts, count));
    }

    @Override
    public String getRoomById(String clientId, String agentId) {
        return gson.toJson(base.getRoomById(clientId, agentId));
    }

    private List<Room> getRooms(List<Room> rooms, int page, int count) {
        int skip = page != 0 ? (page - 1) * count : page;
        int end = count != 0 ? count : rooms.size();
        return rooms.stream()
                .skip(skip)
                .limit(end)
                .collect(Collectors.toList());
    }
}
