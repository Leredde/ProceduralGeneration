package algo;

public abstract class AlgoAdapter implements Algo
{
    protected int max;
    protected long seed;
    
    public AlgoAdapter(int maxValue, long randSeed)
    {
	max = maxValue;
	seed = randSeed;
    }
    
    @Override
    public abstract Integer[][] run(int size);
}
