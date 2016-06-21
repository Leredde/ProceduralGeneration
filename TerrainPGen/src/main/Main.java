package main;
import java.io.IOException;

import algo.Algo2;
import graphic.MapViewFrame;

public class Main
{
    private static void createAndShowGUI(Algo2 algo, double size, int lod) throws IOException
    {        
        // Create and set up the window.
        final MapViewFrame mvf = new MapViewFrame(algo, size, lod);
        mvf.setVisible(true);
        mvf.setDefaultCloseOperation(MapViewFrame.EXIT_ON_CLOSE);

        // Display the window.
        mvf.pack();
        mvf.setVisible(true);
        mvf.setBounds(0, 0, 1024, 512);
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
	double size = 2.56;
	int lod = 100;
        int max = 1023;
        long seed = 666;

        if (args.length > 0)
        {
            size = Double.parseDouble(args[0]);
        }
        if (args.length > 1)
        {
            lod = computeLod(size, Integer.parseInt(args[1]));
        }
        if (args.length > 2)
        {
            seed = Long.parseLong(args[2]);
        }
        
        try
        {
            Algo2 algo = new Algo2(max, seed);
            createAndShowGUI(algo, size, lod);
            //Util.writeMap(map, max, "sqrdmd.ppm");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static int computeLod(double size, int rawLod)
    {
	System.err.println("rawLod = " + rawLod);
	int tempPxSize = (int) Math.floor(size * (double) rawLod);
	System.err.println("tempPxSize = " + tempPxSize);
	int newPxSize = computeClosestPowerOfTwo(tempPxSize);
	System.err.println("newPxSize = " + newPxSize);
	int newLod = (int) Math.floor(newPxSize / size);
	System.err.println("newLod = " + newLod);
	return newLod;
    }

    private static int computeClosestPowerOfTwo(int integer)
    {
	int closestPowerOfTwo = (int) Math.pow(2, Math.ceil(Math.log(integer)/Math.log(2)));
	return closestPowerOfTwo;
    }



}
