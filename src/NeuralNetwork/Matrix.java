package NeuralNetwork;

public class Matrix
{
    private double[][] _data;
    private int _height;
    private int _width;

    public Matrix(int height, int width) throws Exception { this(height, width, 0.0); }
    public Matrix(int height, int width, double defaultValue) throws Exception
    {
        if( width < 1 || height < 1)
            throw new Exception("Dimensions must be greater than 0.");

        _height = height;
        _width = width;
        _data = new double[height][];

        for(var row = 0; row < height; row++)
        {
            _data[row] = new double[width];
            for(var column = 0; column < width; column++)
            {
                _data[row][column] = defaultValue;
            }
        }
    }

    public int getWidth(){ return _width; }
    public int getHeight(){ return _height; }

    @Override
    public String toString()
    {
        var value = "";
        for(int row = 0; row < this.getHeight();  row++)
        {
            value += "[ ";
            for(int column = 0; column < this.getWidth(); column++)
            {
                try
                {
                    value += _data[row][column] + " ";
                }
                catch(Exception Ex)
                {
                    value += 0.0d + " ";
                }
            }
            value += "]\n";
        }
        return value;
    }

    public double elementAt(int row, int column) throws Exception
    {
        if(row < 0 || row > this.getHeight() - 1)
            throw new Exception("Index out of bounds.");

        if(column < 0 || column > this.getWidth() -1)
            throw new Exception("Index out of bounds.");

        return _data[row][column];
    }

    public void setElementAt(int row, int column, double value) throws Exception
    {
        if(row < 0 || row > this.getHeight() - 1)
            throw new Exception("Index out of bounds.");

        if(column < 0 || column > this.getWidth() -1)
            throw new Exception("Index out of bounds.");

        _data[row][column] = value;
    }

    public Matrix multiply(Matrix target) throws Exception
    {
        if(this.getWidth() != target.getHeight())
            throw new Exception("Matrices are incompatible for this operation.");

        var value = new Matrix(this.getHeight(), target.getWidth());

        for(var row = 0; row < value.getHeight(); row++)
        {
            for(var column = 0; column < value.getWidth(); column++)
            {
                var sum = 0d;

                for(int index = 0; index < this.getWidth(); index++)
                    sum += this.elementAt(row, index) * target.elementAt(index, column);

                value.setElementAt(row, column, sum);
            }
        }
        return value;
    }

    public Matrix add(Matrix target) throws Exception
    {
        if(this.getHeight() != target.getHeight() || this.getWidth() != target.getWidth())
            throw new Exception("Matrices are incompatible for this operation.");

        var value = new Matrix(this.getHeight(), this.getWidth());

        for(var row = 0; row < this.getHeight(); row++)
            for(var column = 0; column < this.getWidth(); column++)
                value.setElementAt(row, column, this.elementAt(row,column) + target.elementAt(row, column));

        return value;
    }

    public void applyFunction(MatrixFunction function) throws Exception
    {
        for(var row = 0; row < this.getHeight(); row++)
            for(var column = 0; column < this.getWidth(); column++)
                this.setElementAt(row, column, function.call(this.elementAt(row, column)));
    }
}