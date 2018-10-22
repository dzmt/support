package by.smirnov.service;

public interface RoomService {

    String getRooms(int parts, int count);

    String getRoomById(String clientId, String agentId);
}
