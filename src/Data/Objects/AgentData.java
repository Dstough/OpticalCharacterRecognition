package Data.Objects;

public class AgentData
{
    private int _id;
    public int getId(){return _id;}
    public void setId(int value){_id=value;}

    private int _agentId;
    public int getAgentId(){return _agentId;}
    public void setAgentId(int value){_agentId=value;}

    private byte[] _data;
    public byte[] getData(){return _data;}
    public void setData(byte[] value){_data=value;}
}
