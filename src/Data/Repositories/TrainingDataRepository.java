package Data.Repositories;

import Data.Objects.Agent;
import Data.Objects.TrainingData;

import java.sql.DriverManager;
import java.util.ArrayList;

public class TrainingDataRepository
{
    private final String _connectionUrl;
    private final String _userName;
    private final String _passPhrase;

    public TrainingDataRepository(String connectionUrl, String userName, String passPhrase) throws Exception
    {
        _connectionUrl = connectionUrl;
        _userName = userName;
        _passPhrase = passPhrase;
    }

    public void createTrainingData(TrainingData value) throws Exception
    {
        var sql = """
            insert into TrainingData (AgentId, InputData, OutputData)
            values(?, ?, ?);
        """;
        var connection = DriverManager.getConnection(_connectionUrl, _userName, _passPhrase);
        var statement = connection.prepareStatement(sql);

        statement.setInt(1, value.getAgentId());
        statement.setBlob(2, value.getInputData());
        statement.setBlob(3, value.getOutputData());
        statement.execute();

        statement.close();
        connection.close();
    }

    public ArrayList<TrainingData> readTrainingData(int AgentId) throws Exception
    {
        var value = new ArrayList<TrainingData>();

        var sql = """
            select
                Id, 
                AgentId,
                InputData,
                OutputData
            from TrainingData
            where AgentId = ?
        ;""";
        var connection = DriverManager.getConnection(_connectionUrl, _userName, _passPhrase);
        var statement = connection.prepareStatement(sql);

        statement.setInt(1, AgentId);

        var reader = statement.executeQuery();

        while(reader.next())
        {
            var trainingData = new TrainingData();
            trainingData.setId(reader.getInt("Id"));
            trainingData.setAgentId(reader.getInt("AgentId"));
            trainingData.setInputData(reader.getBlob("InputData"));
            trainingData.setOutputData(reader.getBlob("OutputData"));
            value.add(trainingData);
        }

        reader.close();
        statement.close();
        connection.close();

        return value;
    }

    public void updateTrainingData(TrainingData value) throws Exception
    {
        var sql = """
            update TrainingData 
            set 
                InputData = ?,
                OutputData = ?
            where Id = ?;
        """;
        var connection = DriverManager.getConnection(_connectionUrl, _userName, _passPhrase);
        var statement = connection.prepareStatement(sql);

        statement.setBlob(1,value.getInputData());
        statement.setBlob(2,value.getOutputData());
        statement.setInt(3, value.getId());
        statement.execute();

        statement.close();
        connection.close();
    }

    public void deleteTrainingData(TrainingData value) throws Exception
    {
        var sql = """
            delete from TrainingData
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
