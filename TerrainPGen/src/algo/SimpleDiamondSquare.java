package algo;

import java.util.Random;

public class SimpleDiamondSquare extends AlgoAdapter
{
    public SimpleDiamondSquare(int maxValue, long randSeed)
    {
	super(maxValue, randSeed);
    }

    public Integer[][] run(int size)
    {
        int mapSize = size + 1;
        int stride = size;
        int range = max;

        Random rand = new Random(seed);
        // Random rand = new Random(System.currentTimeMillis());
        Integer[][] map = new Integer[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++)
        {
            for (int j = 0; j < mapSize; j++)
            {
                map[i][j] = -1;
            }
        }

        // map[0][0] = rand.nextInt(maxValue);
        // map[0][mapSize - 1] = rand.nextInt(maxValue);
        // map[mapSize - 1][0] = rand.nextInt(maxValue);
        // map[mapSize - 1][mapSize - 1] = rand.nextInt(maxValue);

        // map[0][0] = maxValue - maxValue / 3;
        // map[0][mapSize - 1] = 2 * maxValue / 3;
        // map[mapSize - 1][0] = maxValue / 5;
        // map[mapSize - 1][mapSize - 1] = 0;

        // map[0][0] = maxValue;
        // map[0][mapSize - 1] = 2 * maxValue / 3;
        // map[mapSize - 1][0] = maxValue / 5;
        // map[mapSize - 1][mapSize - 1] = 0;

        map[0][0] = 0;
        map[0][mapSize - 1] = 0;
        map[mapSize - 1][0] = 0;
        map[mapSize - 1][mapSize - 1] = 0;

        // map[0][0] = rand.nextInt(maxValue / 2);
        // map[0][mapSize - 1] = rand.nextInt(maxValue / 2);
        // map[mapSize - 1][0] = rand.nextInt(maxValue / 2);
        // map[mapSize - 1][mapSize - 1] = rand.nextInt(maxValue / 2);

        while (stride > 1)
        {
            System.err.println(stride);

            for (int i = 0; i < (mapSize - 1) / stride; i++)
            {
                for (int j = 0; j < (mapSize - 1) / stride; j++)
                {
                    square(map, rand, i * stride, j * stride, stride, range);
                    diamond(map, rand, i * stride, j * stride, stride, range);
                }
            }
            if (range > 2)
            {
                range = range / 2;
            }

            stride = stride / 2;
        }
        return map;
    }

    private static void square(Integer[][] map, Random r, int startX, int startY, int stride, int range)
    {
        // int granularity = 8;
        double value = 0.0;
        // System.err.println(startX + " " + startY + " " + stride);
        double ratio = r.nextInt(100) / 100.0;
        double ratioCum = ratio;
        value += map[startX][startY] * ratio;
        ratio = r.nextInt(new Double((1.0 - ratioCum) * 100).intValue()) / 100.0;
        ratioCum += ratio;
        value += map[startX][startY + stride] * ratio;
        ratio = r.nextInt(new Double((1.0 - ratioCum) * 100).intValue()) / 100.0;
        ratioCum += ratio;
        value += map[startX + stride][startY] * ratio;
        ratio = 1 - ratioCum;
        value += map[startX + stride][startY + stride] * ratio;

        value += r.nextInt(range) / 6.0;

        map[startX + stride / 2][startY + stride / 2] = new Double(value).intValue();
    }

    private static boolean isOnMapBorder(int pointX, int pointY, int mapLengthX, int mapLengthY)
    {
        return pointX == 0 || pointX == mapLengthX - 1 || pointY == 0 || pointY == mapLengthY - 1;
    }

    private static void diamondPart(Integer[][] map, int x1, int y1, int x2, int y2, int midX, int midY, Random r,
            int range)
    {
        double divider = 3.0;
        double sDivider = 3.0;
        //int granularity = 8;

        // If the pixel to compute is not on the border of the map
        if (!isOnMapBorder(x1, y1, map.length, map[0].length) || !isOnMapBorder(x2, y2, map.length, map[0].length))
        {
            divider = 4.0;
            sDivider = divider * 2.0;
        }
        double value = 0.0;
        value += map[x1][y1] / sDivider;
        value += map[x2][y2] / sDivider;
        value += map[midX][midY] / divider;

        int destX = x1;
        int destY = y1;

        if (x1 != x2)
        {
            destX = midX;
        }

        if (y1 != y2)
        {
            destY = midY;
        }

        if (map[destX][destY] >= 0)
        {
            value += map[destX][destY];
            value += r.nextInt(range) / 8.0;
        }
        if (divider == 3.0)
        {
            value += r.nextInt(range) / 8.0;
        }
        map[destX][destY] = new Double(value).intValue();
    }

    private static void diamond(Integer[][] map, Random r, int startX, int startY, int stride, int range)
    {
        diamondPart(map, startX, startY, startX, startY + stride, startX + stride / 2, startY + stride / 2, r, range);
        diamondPart(map, startX + stride, startY, startX + stride, startY + stride, startX + stride / 2,
                startY + stride / 2, r, range);
        diamondPart(map, startX, startY, startX + stride, startY, startX + stride / 2, startY + stride / 2, r, range);
        diamondPart(map, startX, startY + stride, startX + stride, startY + stride, startX + stride / 2,
                startY + stride / 2, r, range);

    }
}
