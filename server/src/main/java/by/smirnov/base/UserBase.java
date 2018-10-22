package by.smirnov.base;

import by.smirnov.facade.User;
import by.smirnov.model.Room;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class UserBase implements AgentBase, ClientBase, RoomBase {
    private static final Logger logger = LogManager.getLogger(UserBase.class);

    private static ConcurrentHashMap<String, User> clients = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, User> agents = new ConcurrentHashMap<>();
    private static ConcurrentLinkedQueue<User> waitingRoom = new ConcurrentLinkedQueue<>();

    public void addClient(User person) {
        clients.put(person.getId(), person);

        logger.info("client[" + person.getId() + "] status[" + person.getStatus() +
                "] add to client base - count of agents in base: " + agents.size() +
                " - count of waiting clients: " + waitingRoom.size());
    }

    public void removeClient(User person) {
        waitingRoom.remove(person);
        clients.remove(person.getId(), person);

        logger.info("client[" + person.getId() + "] status[" + person.getStatus() + "] remove from client base - count of agents in base: " + agents.size() +
                " - count of waiting clients: " + waitingRoom.size());

    }

    public User getClientById(String id) {
        return clients.get(id);
    }

    public void addAgent(User person) {
        agents.put(person.getId(), person);
        logger.info("agent[" + person.getId() + "] status[" + person.getStatus() + "] add to agent base - count of agents in base: " + agents.size() +
                " - count of waiting clients: " + waitingRoom.size());

    }

    public void removeAgent(User person) {
        if (agents.contains(person)) {
            agents.remove(person.getId(), person);
        }
        logger.info("agent[" + person.getId() + "] status[" + person.getStatus() + "] remove from agent base - - count of agents in base: " + agents.size() +
                " - count of waiting clients: " + waitingRoom.size());
    }

    public boolean hasReadyAgent() {
        return agents.values().stream()
                .anyMatch(agent -> agent.ready());
    }

    public User getReadyAgent() {
        User person = null;
        if (hasReadyAgent()) {
            person = agents.values().stream()
                    .filter(agent -> agent.ready())
                    .findAny()
                    .get();

        logger.info("agent[" + person.getId() + "] status[" + person.getStatus() + "] get ready agent from agent base.");
        }
        return person;
    }

    @Override
    public List<User> getReadyAgents() {
        return agents.values().stream()
                .filter(agent -> agent.ready())
                .collect(Collectors.toList());
    }

    public void removeFromWaitingRoom(User person) {
         if (waitingRoom.contains(person)) {
            waitingRoom.remove(person);
             logger.info("client[" + person.getId() + "] status[" + person.getStatus() + "] remove from waiting room - - count of agents in base: " + agents.size() +
                     " - count of waiting clients: " + waitingRoom.size());
         }
    }

    public boolean hasWaitingClients() {
        boolean hasClients = waitingRoom.size() > 0;
        return hasClients;
    }

    public User getWaitingClient() {
        User person = waitingRoom.poll();
        if (person != null) {
            logger.info("client[" + person.getId() + "] status[" + person.getStatus() + "] get client from waiting room");
            return person;

        }
        return person;
    }

    public void addToWaitingRoom(User person) {
        logger.info("client[" + person.getId() + "] status[" + person.getStatus() + "] add to waiting room");
        waitingRoom.add(person);
    }

    public Collection<User> getClients() {
        return clients.values();
    }

    @Override
    public List<User> getWaitingRoom() {
        return new LinkedList<>(waitingRoom);
    }

    @Override
    public List<User> getAgents() {
        return agents.values().stream().collect(Collectors.toList());
    }

    @Override
    public User getAgentById(String id) {
        return agents.get(id);
    }

    @Override
    public List<Room> getRooms() {
        List<Room> clientList = clients.values().stream()
                .filter(user -> !user.ready())
                .map(user -> {
                    User agent = getAgentById(user.getListInterlocutors().stream().findFirst().get().getId());
                    Room room = new Room();
                    room.setAgent(agent);
                    room.setClient(user);
                    return room;
                })
                .collect(Collectors.toList());

        return clientList;
    }

    @Override
    public Room getRoomById(String clientId, String agentId) {
        Room room = new Room();
        room.setClient(clients.get(clientId));
        room.setClient(agents.get(agentId));
        return room;
    }
}
