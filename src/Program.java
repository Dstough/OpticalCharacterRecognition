import NeuralNetwork.Agent;
import NeuralNetwork.Matrix;
import NeuralNetwork.TrainingData;

public class Program
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("hello world\n");
        var connectionString = System.getenv("NeuralNetworkConnectionString");
        var agent = new Agent(4,2,3,2);

        agent.setInput(new Matrix(agent.getInputSize(), 1));
        System.out.println(agent.getOutput());

        var batch = new TrainingData[1];

        batch[0] = new TrainingData(agent.getInputSize(), agent.getOutputSize());
        agent.train(batch);
    }
}