package NeuralNetwork;

public class Agent
{
    private int _layerCount;

    private Matrix[] _weight;
    private Matrix[] _bias;
    private Matrix _inputActivation;

    public Agent(int inputSize, int hiddenSize, int outputSize, int layerCount) throws Exception
    {
        if(inputSize < 1 || inputSize <= hiddenSize || hiddenSize <= outputSize)
            throw new Exception("Sizes are invalid.");

        if(layerCount < 3)
            throw new Exception("Network is too small.");

        _layerCount = layerCount;
        _inputActivation = new Matrix(inputSize, 1);

        _weight = new Matrix[layerCount];
        _bias = new Matrix[layerCount];

        _weight[0] = new Matrix(hiddenSize, inputSize);
        _bias[0] = new Matrix(hiddenSize, 1);

        for(var index = 1; index < layerCount - 1; index++)
        {
            _weight[index] = new Matrix(hiddenSize, hiddenSize);
            _bias[index] = new Matrix(hiddenSize, 1);
        }

        _weight[layerCount - 1] = new Matrix(outputSize, hiddenSize);
        _bias[layerCount - 1] = new Matrix(outputSize, 1);
    }

    public void setInput(double[] data) throws Exception
    {
        if(data.length != _inputActivation.getHeight())
            throw new Exception("Data size is invalid.");

        for(var index = 0; index < _inputActivation.getHeight(); index++)
            _inputActivation.setElementAt(index, 0, data[index]);
    }

    public Matrix getOutput() throws Exception
    {
        var value = _inputActivation;

        for(var index = 0; index < _layerCount; index++)
        {
            value = _weight[index].multiply(value).add(_bias[index]);
            value.applyFunction(x -> 1 / (1 + Math.pow(Math.E, (-1 * x))));
        }

        return value;
    }
}
