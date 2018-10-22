package by.smirnov.base;

import by.smirnov.model.Room;

import java.util.List;

public interface RoomBase {

    List<Room> getRooms();

    Room getRoomById(String clientId, String agentId);
}
