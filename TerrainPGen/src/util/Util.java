package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Util {
    private Util()
    {    
    }
    
	public static void sleep(long ms){
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int mod(int value, int modulus)
    {
        // (a % b + b) % b
        return (value % modulus + modulus) % modulus;
    }
	
	public static void writeMap(Integer[][] map, int maxValue, String fileName) throws IOException
    {
        // BufferedImage img = new BufferedImage(map.get(0).size()*3,
        // map.size()*3, BufferedImage.TYPE_BYTE_GRAY);
        // Raster.createBandedRaster
        // img.setData();

        File f = new File(fileName);
        FileOutputStream fos = new FileOutputStream(f);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        bw.write("P3");
        bw.newLine();
        bw.write(map[0].length + " " + map.length);
        bw.newLine();
        bw.write("" + maxValue);
        bw.newLine();
        for (int i = 0; i < map.length; i++)
        {
            for (int j = 0; j < map[i].length; j++)
            {
                int value = 0;
                if (map[i][j] != null)
                {
                    value = map[i][j];
                }
                bw.write("" + value);
                bw.newLine();
                bw.write("" + value);
                bw.newLine();
                bw.write("" + value);
                bw.newLine();
            }
        }
        bw.close();
        fos.close();
    }
}
