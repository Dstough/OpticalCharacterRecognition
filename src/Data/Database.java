package Data;

import Data.Repositories.AgentDataRepository;
import Data.Repositories.AgentRepository;
import Data.Repositories.TrainingDataRepository;

public class Database
{
    private String _connectionString;
    private AgentRepository _agents;
    private AgentDataRepository _agentData;
    private TrainingDataRepository _trainingData;

    public AgentRepository getAgents(){ return _agents; }
    public AgentDataRepository getAgentData(){return _agentData;}
    public TrainingDataRepository getTrainingData(){return _trainingData;}

    public Database(String connectionString) throws Exception
    {
        _connectionString = connectionString;

        var keys = connectionString.split(";");
        var connectionUrl ="jdbc:mysql://" + keys[0].split("=")[1] + ":" + keys[1].split("=")[1] + "/" + keys[2].split("=")[1];
        var userName = keys[3].split("=")[1];
        var passPhrase = keys[4].split("=")[1];

        _agents = new AgentRepository(connectionUrl, userName, passPhrase);
        _agentData = new AgentDataRepository(connectionUrl, userName, passPhrase);
        _trainingData = new TrainingDataRepository(connectionUrl, userName, passPhrase);
    }
}
