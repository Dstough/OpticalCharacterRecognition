package Data.Repositories;

import Data.Objects.Agent;

import java.sql.DriverManager;

public class AgentRepository
{
    private String _connectionUrl;
    private String _userName;
    private String _passPhrase;

    public AgentRepository(String connectionUrl, String userName, String passPhrase)
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
        connection.close();
    }

    public Agent readAgent(String name)
    {
        return null;
    }
}