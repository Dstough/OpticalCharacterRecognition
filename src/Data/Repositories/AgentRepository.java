package Data.Repositories;

import Data.Objects.Agent;
import java.sql.DriverManager;

public class AgentRepository
{
    private final String _connectionUrl;
    private final String _userName;
    private final String _passPhrase;

    public AgentRepository(String connectionUrl, String userName, String passPhrase) throws Exception
    {
        _connectionUrl = connectionUrl;
        _userName = userName;
        _passPhrase = passPhrase;
    }

    public void createAgent(Agent value) throws Exception
    {
        var sql = """
            insert into Agent (Name, Description, InputSize, OutputSize, HiddenLayerSize, HiddenLayerCount)
            values(?, ?, ?, ?, ?, ?);
        """;
        var connection = DriverManager.getConnection(_connectionUrl, _userName, _passPhrase);
        var statement = connection.prepareStatement(sql);

        statement.setString(1, value.getName());
        statement.setString(2, value.getDescription());
        statement.setInt(3, value.getInputSize());
        statement.setInt(4, value.getOutputSize());
        statement.setInt(5, value.getHiddenLayerSize());
        statement.setInt(6, value.getHiddenLayerCount());
        statement.execute();

        statement.close();
        connection.close();
    }

    public Agent readAgent(String name) throws Exception
    {
        var sql = """
            select
                Id, 
                Name, 
                Description, 
                InputSize, 
                OutputSize, 
                HiddenLayerSize, 
                HiddenLayerCount
            from Agent
            where Name = ?
            limit 1
        ;""";
        var connection = DriverManager.getConnection(_connectionUrl, _userName, _passPhrase);
        var statement = connection.prepareStatement(sql);

        statement.setString(1, name);

        var reader = statement.executeQuery();
        var agent = new Agent();

        while(reader.next())
        {
            agent.setId(reader.getInt("Id"));
            agent.setName(reader.getString("Name"));
            agent.setDescription(reader.getString("Description"));
            agent.setInputSize(reader.getInt("InputSize"));
            agent.setOutputSize(reader.getInt("OutputSize"));
            agent.setHiddenLayerSize(reader.getInt("HiddenLayerSize"));
            agent.setHiddenLayerCount(reader.getInt("HiddenLayerCount"));
        }

        statement.close();
        connection.close();

        return agent;
    }

    public void updateAgent(Agent value) throws Exception
    {
        var sql = """
            update Agent 
            set 
                Name = ?, 
                Description = ?, 
                InputSize = ?, 
                OutputSize = ?, 
                HiddenLayerSize = ?, 
                HiddenLayerCount= ?
            where Id = ?;
        """;
        var connection = DriverManager.getConnection(_connectionUrl, _userName, _passPhrase);
        var statement = connection.prepareStatement(sql);

        statement.setString(1, value.getName());
        statement.setString(2, value.getDescription());
        statement.setInt(3, value.getInputSize());
        statement.setInt(4, value.getOutputSize());
        statement.setInt(5, value.getHiddenLayerSize());
        statement.setInt(6, value.getHiddenLayerCount());
        statement.setInt(7, value.getId());
        statement.execute();

        statement.close();
        connection.close();
    }

    public void deleteAgent(Agent value) throws Exception
    {
        var sql = """
            delete from Agent
            where Id = ?;
        """;
        var connection = DriverManager.getConnection(_connectionUrl, _userName, _passPhrase);
        var statement = connection.prepareStatement(sql);

        statement.setInt(7, value.getId());
        statement.execute();

        statement.close();
        connection.close();
    }
}