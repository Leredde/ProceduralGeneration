package util;
import java.util.Random;


public class RandInt255 {
	Random rand;
	
	public RandInt255(long seed)
	{
		rand = new Random(seed);
	}
	
	public int getNext() {
		return rand.nextInt(255);
	}
}
