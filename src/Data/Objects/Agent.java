package Data.Objects;

public class Agent
{
    private int _id;
    public int getId(){ return _id;}
    public void setId(int value){_id = value;}

    private String _name;
    public String getName() {return _name;}
    public void setName(String value) {_name=value;}

    private String _description;
    public String getDescription() { return _description;}
    public void setDescription(String value){_description=value;}

    private int _inputSize;
    public int getInputSize() {return _inputSize;}
    public void setInputSize(int value) {_inputSize=value;}

    private int _outputSize;
    public int getOutputSize(){return _outputSize;}
    public void setOutputSize(int value){_outputSize=value;}

    private int _hiddenLayerSize;
    public int getHiddenLayerSize(){return _hiddenLayerSize;}
    public void setHiddenLayerSize(int value) {_hiddenLayerSize=value;}

    private int _hiddenLayerCount;
    public int getHiddenLayerCount(){return _hiddenLayerCount;}
    public void setHiddenLayerCount(int value){_hiddenLayerCount=value;}
}
