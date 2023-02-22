package Data.Repositories;

import Data.Objects.AgentData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AgentDataRepository
{
    private final String _connectionUrl;
    private final String _userName;
    private final String _passPhrase;

    public AgentDataRepository(String connectionUrl, String userName, String passPhrase)
    {
        _connectionUrl = connectionUrl;
        _userName = userName;
        _passPhrase = passPhrase;
    }

    public void createAgentData(AgentData value) throws Exception
    {
        var sql = """
            insert into AgentData (AgentId, Data)
            values(?, ?);
        """;
        var connection = DriverManager.getConnection(_connectionUrl, _userName, _passPhrase);
        var statement = connection.prepareStatement(sql);
        var blob = connection.createBlob();

        blob.setBytes(1, value.getData());

        statement.setInt(1, value.getAgentId());
        statement.setBlob(2, blob);
        statement.execute();

        statement.close();
        connection.close();
    }

    public AgentData readAgentData(int agentId) throws Exception
    {
        var sql = """
            select 
                Id,
                AgentId,
                Data
            from AgentData
            where AgentId = ?
            limit 1;
        """;
        var connection = DriverManager.getConnection(_connectionUrl, _userName, _passPhrase);
        var statement = connection.prepareStatement(sql);

        statement.setInt(1, agentId);

        var reader = statement.executeQuery();
        var value = new AgentData();

        if(reader.next())
        {
            value.setId(reader.getInt("Id"));
            value.setAgentId(reader.getInt("AgentId"));

            var blob = reader.getBlob("Data");

            value.setData(blob.getBytes(1, (int)blob.length()));
        }

        reader.close();
        statement.close();
        connection.close();

        return value;
    }

    public void updateAgentData(AgentData value) throws Exception
    {
        var sql = """
            update AgentData
            set Data = ?
            where Id = ?;
        """;
        var connection = DriverManager.getConnection(_connectionUrl, _userName, _passPhrase);
        var statement = connection.prepareStatement(sql);
        var blob = connection.createBlob();

        blob.setBytes(1, value.getData());

        statement.setBlob(1, blob);
        statement.setInt(2, value.getId());
        statement.execute();

        statement.close();
        connection.close();
    }

    public void deleteAgentData(AgentData value) throws Exception
    {
        var sql = """
            delete from AgentData
            where Id = ?;
        """;
        var connection = DriverManager.getConnection(_connectionUrl, _userName, _passPhrase);
        var statement = connection.prepareStatement(sql);

        statement.setInt(1, value.getId());
        statement.execute();

        statement.close();
        connection.close();
    }
}
