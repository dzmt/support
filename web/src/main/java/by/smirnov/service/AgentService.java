package by.smirnov.service;

public interface AgentService {

    String getAgents(int parts, int count);

    String getAgentById(String id);

    String getReadyAgents(int parts, int count);

    String getSizeReadyAgents();
}
