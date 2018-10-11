package by.smirnov.base;

import by.smirnov.facade.Agent;
import by.smirnov.facade.Client;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UserBase {
    private static final Logger logger = LogManager.getLogger(UserBase.class);

    private static ConcurrentHashMap<String, Client> clients = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Agent> agents = new ConcurrentHashMap<>();
    private static ConcurrentLinkedQueue<Client> waitingRoom = new ConcurrentLinkedQueue<>();

    public void addClient(Client person) {
        clients.put(person.getId(), person);

        logger.info("client[" + person.getId() + "] status[" + person.getStatus() + "] add to client base");
    }

    public void removeClient(Client person) {
        waitingRoom.remove(person);
        clients.remove(person.getId(), person);

        logger.info("client[" + person.getId() + "] status[" + person.getStatus() + "] remove from client base");

    }

    public void addAgent(Agent person) {
        agents.put(person.getId(), person);
        logger.info("agent[" + person.getId() + "] status[" + person.getStatus() + "] add to agent base.");

    }

    public void removeAgent(Agent person) {
        if (agents.contains(person)) {
            agents.remove(person.getId(), person);
        }
        logger.info("agent[" + person.getId() + "] status[" + person.getStatus() + "] remove from agent base.");
    }

    public boolean hasReadyAgent() {
        return agents.values().stream()
                .anyMatch(agent -> agent.ready());
    }

    public Agent getReadyAgent() {
        Agent person = agents.values().stream()
                .filter(agent -> agent.ready())
                .findAny()
                .get();
        logger.info("agent[" + person.getId() + "] status[" + person.getStatus() + "] get ready agent from agent base.");
        return person;
    }

     public void removeFromWaitingRoom(Client person) {
         if (waitingRoom.contains(person)) {
            waitingRoom.remove();
             logger.info("client[" + person.getId() + "] status[" + person.getStatus() + "] remove from waiting room");
         }
    }

    public boolean hasWaitingClients() {
        boolean hasClients = waitingRoom.size() > 0;
        return hasClients;
    }

    public Client getWaitingClient() {
        Client person = waitingRoom.poll();
        logger.info("client[" + person.getId() + "] status[" + person.getStatus() + "] get client from waiting room");
        return person;
    }

    public void addToWaitingRoom(Client person) {
        logger.info("client[" + person.getId() + "] status[" + person.getStatus() + "] add to waiting room");
        waitingRoom.add(person);
    }

}
