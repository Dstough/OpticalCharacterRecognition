package Data.Objects;

import java.sql.Blob;

public class TrainingData
{
    private int _id;
    public int getId(){return _id;}
    public void setId(int value){_id=value;}

    private int _agentId;
    public int getAgentId(){return _agentId;}
    public void setAgentId(int value){_agentId=value;}

    private Blob _inputData;
    public Blob getInputData() throws Exception {return _inputData;}
    public void setInputData(byte[] value) throws Exception {_inputData.setBytes(1,value);}
    public void setInputData(Blob value) throws Exception {_inputData=value;}

    private Blob _outputData;
    public Blob getOutputData() throws Exception {return _outputData;}
    public void setOutputData(byte[] value) throws Exception {_outputData.setBytes(1,value);}
    public void setOutputData(Blob value) throws Exception {_outputData=value;}
}
