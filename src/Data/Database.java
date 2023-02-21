package Data;

import Data.Repositories.AgentRepository;

public class Database
{
    private String _connectionString;

    private AgentRepository _agents;
    public AgentRepository getAgents(){ return _agents; }

    public Database(String connectionString)
    {
        _connectionString = connectionString;

        var keys = connectionString.split(";");
        var connectionUrl ="jdbc:mysql://" + keys[0].split("=")[1] + ":" + keys[1].split("=")[1] + "/" + keys[2].split("=")[1];
        var userName = keys[3].split("=")[1];
        var passPhrase = keys[4].split("=")[1];

        _agents = new AgentRepository(connectionUrl, userName, passPhrase);
    }
}
