package NeuralNetwork;

import java.io.*;
import java.util.Random;

public class Agent implements Serializable
{
    private int _hiddenLayerCount;

    private Matrix[] _weight;
    private Matrix[] _bias;
    private Matrix _inputActivation;

    public Agent(int inputSize, int outputSize, int hiddenLayerSize,  int hiddenLayerCount) throws Exception
    {
        if(inputSize < 1 || hiddenLayerCount < 1 || inputSize <= hiddenLayerSize || hiddenLayerSize <= outputSize)
            throw new Exception("Sizes are invalid.");

        _hiddenLayerCount = hiddenLayerCount;
        _inputActivation = new Matrix(inputSize, 1);

        _weight = new Matrix[hiddenLayerCount + 1];
        _bias = new Matrix[hiddenLayerCount + 1];

        _weight[0] = new Matrix(hiddenLayerSize, inputSize);
        _bias[0] = new Matrix(hiddenLayerSize, 1);

        for(var index = 1; index < hiddenLayerCount - 1; index++)
        {
            _weight[index] = new Matrix(hiddenLayerSize, hiddenLayerSize);
            _bias[index] = new Matrix(hiddenLayerSize, 1);
        }

        _weight[hiddenLayerCount - 1] = new Matrix(outputSize, hiddenLayerSize);
        _bias[hiddenLayerCount - 1] = new Matrix(outputSize, 1);

        var random = new Random(System.currentTimeMillis());

        for(var index = 0; index < hiddenLayerCount; index++)
        {
            _weight[index].applyFunction(x -> random.nextDouble());
            _bias[index].applyFunction(x -> random.nextDouble());
        }
    }

    public void setInput(Matrix data) throws Exception
    {
        if(_inputActivation.getHeight() != data.getHeight() || data.getWidth() != 1)
            throw new Exception("Matrix size is invalid.");

        _inputActivation = data;
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

        for(var index = 0; index < _hiddenLayerCount; index++)
        {
            value = _weight[index].multiply(value).add(_bias[index]);
            value.applyFunction(x -> 1 / (1 + Math.pow(Math.E, (-1 * x))));
        }

        return value;
    }

    public byte[] toByteArray() throws Exception
    {
        var stream = new ByteArrayOutputStream();
        var output = new ObjectOutputStream(stream);

        output.writeObject(this);
        output.flush();

        var value = stream.toByteArray();

        stream.close();

        return value;
    }

    public static Agent fromByteArray(byte[] data) throws Exception
    {
        var stream = new ByteArrayInputStream(data);
        var input = new ObjectInputStream(stream);

        return (Agent)input.readObject();
    }

    public void train(TrainingData trainingData) throws Exception
    {
        this.setInput(trainingData.getInputData());
        var cost = this.getOutput().subtract(trainingData.getExpectedOutputData());

        //TODO: finish the training for this set here.
    }
}
