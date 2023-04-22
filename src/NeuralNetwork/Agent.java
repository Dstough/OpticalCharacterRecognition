package NeuralNetwork;

import java.io.*;
import java.util.Random;

public class Agent implements Serializable
{
    private final int _hiddenLayerCount;

    private final Matrix[] _weight;
    private final Matrix[] _bias;
    private final Matrix[] _activation;

    public Agent(int inputSize, int outputSize, int hiddenLayerSize,  int hiddenLayerCount) throws Exception
    {
        if(inputSize < 1 || hiddenLayerCount < 1 || inputSize <= hiddenLayerSize || hiddenLayerSize <= outputSize)
            throw new Exception("Sizes are invalid.");

        _hiddenLayerCount = hiddenLayerCount;
        _weight = new Matrix[hiddenLayerCount + 1];
        _bias = new Matrix[hiddenLayerCount + 1];
        _activation = new Matrix[hiddenLayerCount + 2];

        _activation[0] = new Matrix(inputSize, 1);
        _weight[0] = new Matrix(hiddenLayerSize, inputSize);
        _bias[0] = new Matrix(hiddenLayerSize, 1);

        for(var index = 1; index < hiddenLayerCount; index++)
        {
            _activation[index] = new Matrix(hiddenLayerSize, 1);
            _weight[index] = new Matrix(hiddenLayerSize, hiddenLayerSize);
            _bias[index] = new Matrix(hiddenLayerSize, 1);
        }

        _activation[hiddenLayerCount] = new Matrix(hiddenLayerSize, 1);
        _weight[hiddenLayerCount] = new Matrix(outputSize, hiddenLayerSize);
        _bias[hiddenLayerCount] = new Matrix(outputSize, 1);
        _activation[hiddenLayerCount + 1] = new Matrix(outputSize, 1);

        var random = new Random(System.currentTimeMillis());

        for(var index = 0; index <= hiddenLayerCount; index++)
        {
            _weight[index].applyFunction(x -> random.nextDouble());
            _bias[index].applyFunction(x -> random.nextDouble());
        }
    }

    public void setInput(Matrix data) throws Exception
    {
        if(_activation[0].getHeight() != data.getHeight() || data.getWidth() != 1)
            throw new Exception("Matrix size is invalid.");

        _activation[0] = data;
    }
    public void setInput(double[] data) throws Exception
    {
        if(data.length != _activation[0].getHeight())
            throw new Exception("Data size is invalid.");

        for(var index = 0; index < _activation[0].getHeight(); index++)
            _activation[0].setElementAt(index, 0, data[index]);
    }

    public int getInputSize()
    {
        return _activation[0].getHeight();
    }

    public int getOutputSize()
    {
        return _activation[_activation.length - 1].getHeight();
    }

    public Matrix getOutput() throws Exception
    {
        var value = _activation[0];

        for(var index = 0; index <= _hiddenLayerCount; index++)
        {
            value = _weight[index].multiply(value).add(_bias[index]);
            value.applyFunction(this::sigmoid);
            _activation[index + 1] = value;
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

    public void train(TrainingData[] trainingBatch) throws Exception
    {
        var expectedOutput = new Matrix[trainingBatch.length];

        for(var index = 0; index < trainingBatch.length; index++)
        {
            expectedOutput[index] = new Matrix(trainingBatch[index].getExpectedOutputData().getHeight(), 1);

            for(var row = 0; row < expectedOutput[index].getHeight(); row++)
            {
                expectedOutput[index].setElementAt(row,0, trainingBatch[index].getExpectedOutputData().elementAt(row,0));
            }
        }

        for(int layer = _hiddenLayerCount; layer > -1; layer--)
        {
            var weightShift = new Matrix(_weight[layer].getHeight(), _weight[layer].getWidth());
            var biasShift = new Matrix[_activation[layer].getHeight()];

            for(int trainingIndex = 0; trainingIndex < trainingBatch.length; trainingIndex++)
            {
                this.setInput(trainingBatch[trainingIndex].getInputData());
                this.getOutput();

                for(var row = 0; row < weightShift.getHeight(); row++)
                {
                    for(int column = 0; column < weightShift.getWidth(); column++)
                    {
                        var z = _weight[layer].elementAt(row, column)
                                * _activation[layer].elementAt(column, 0)
                                + _bias[layer].elementAt(row, 0);
                        var s = _activation[layer].elementAt(column, 0)
                                * sigmoidPrime(z)
                                * 2d
                                * (_activation[layer + 1].elementAt(row,0) - expectedOutput[trainingIndex].elementAt(row,0));

                        weightShift.setElementAt(row, column, s);
                    }
                }

                for(var index = 0; index < biasShift.length; index++)
                {
                    biasShift[index] = new Matrix(_activation[layer+1].getHeight(),1);

                    for(var row = 0; row < biasShift[index].getHeight(); row++)
                    {
                        var z = _weight[layer].elementAt(row, index)
                                * _activation[layer].elementAt(index,0)
                                + _bias[layer].elementAt(row, 0);

                        var s = sigmoidPrime(z)
                                * 2d
                                * (_activation[layer + 1].elementAt(row, 0) - expectedOutput[trainingIndex].elementAt(row,0));

                        biasShift[index].setElementAt(row,0, s);
                    }
                }
            }

            for(int index = 0; index < expectedOutput.length; index++)
            {
                expectedOutput[index] = new Matrix(_activation[layer].getHeight(), 1);

                for(int row = 0; row < expectedOutput[index].getHeight(); row++)
                {
                    //TODO: Sum the weight influences here to calculate the shift.
                    

                    expectedOutput[index].setElementAt(row, 0, s);
                }
            }

            _weight[layer].add(weightShift);

            for(var index = 0; index < biasShift.length; index++)
            {
                _bias[layer].add(biasShift[index]);
            }
        }
    }

    private double sigmoid(double x)
    {
        return 1 / (1 + Math.pow(Math.E, (-1 * x)));
    }

    private double sigmoidPrime(double x)
    {
        return Math.pow(Math.E, -x) / Math.pow(Math.pow(Math.E, -x) + 1, 2);
    }
}
