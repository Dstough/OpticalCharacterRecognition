package NeuralNetwork;

public class TrainingData
{
    private Matrix _inputData;
    public Matrix getInputData(){return _inputData;}
    public void setInputData(Matrix value) {_inputData = value;}

    private Matrix _expectedOutputData;
    public Matrix getExpectedOutputData(){return _expectedOutputData;}
    public void setExpectedOutputData(Matrix value){_expectedOutputData = value;}

    public TrainingData(int inputHeight, int outputHeight) throws Exception
    {
        _inputData = new Matrix(inputHeight, 1);
        _expectedOutputData = new Matrix(outputHeight, 1);
    }
}
