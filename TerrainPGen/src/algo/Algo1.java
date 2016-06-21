package algo;

import java.util.ArrayList;
import java.util.List;

import util.RandInt255;
import util.Util;

public class Algo1
{
    private Algo1()
    {
    }

    public static Integer[][] run(int size, long seed)
    {
        RandInt255 rand = new RandInt255(seed);
        Integer initMap[][] = { { rand.getNext(), rand.getNext(), rand.getNext() },
                { rand.getNext(), rand.getNext(), rand.getNext() },
                { rand.getNext(), rand.getNext(), rand.getNext() } };

        List<List<Integer[][]>> map = new ArrayList<List<Integer[][]>>();
        List<Integer[][]> line = new ArrayList<Integer[][]>();
        line.add(initMap);
        map.add(line);

        while (map.size() < size)
        {
            List<List<Integer[][]>> map2 = new ArrayList<List<Integer[][]>>();
            for (List<Integer[][]> l : map)
            {
                for (Integer[][] s : l)
                {
                    // Create boundaries for s.
                    Integer[][] full = new Integer[5][5];

                    // North boundary
                    Integer[][] tmpSquare = map.get(Util.mod(map.indexOf(l) - 1, map.size())).get(l.indexOf(s));
                    for (int i = 0; i < 3; i++)
                    {
                        full[0][i + 1] = tmpSquare[2][i];
                    }
                    // South boundary
                    tmpSquare = map.get(Util.mod(map.indexOf(l) + 1, map.size())).get(l.indexOf(s));
                    for (int i = 0; i < 3; i++)
                    {
                        full[4][i + 1] = tmpSquare[0][i];
                    }
                    // West boundary
                    tmpSquare = l.get(Util.mod(l.indexOf(s) - 1, l.size()));
                    for (int i = 0; i < 3; i++)
                    {
                        full[i + 1][0] = tmpSquare[i][2];
                    }
                    // East boundary
                    tmpSquare = l.get(Util.mod(l.indexOf(s) + 1, l.size()));
                    for (int i = 0; i < 3; i++)
                    {
                        full[i + 1][4] = tmpSquare[i][0];
                    }
                    // Corners
                    tmpSquare = map.get(Util.mod(map.indexOf(l) - 1, map.size())).get(Util.mod(l.indexOf(s) - 1, l.size()));
                    full[0][0] = tmpSquare[2][2];
                    tmpSquare = map.get(Util.mod(map.indexOf(l) + 1, map.size())).get(Util.mod(l.indexOf(s) + 1, l.size()));
                    full[4][4] = tmpSquare[0][0];
                    tmpSquare = map.get(Util.mod(map.indexOf(l) - 1, map.size())).get(Util.mod(l.indexOf(s) + 1, l.size()));
                    full[0][4] = tmpSquare[2][0];
                    tmpSquare = map.get(Util.mod(map.indexOf(l) + 1, map.size())).get(Util.mod(l.indexOf(s) - 1, l.size()));
                    full[4][0] = tmpSquare[0][2];

                    for (int i = 0; i < 3; i++)
                    {
                        for (int j = 0; j < 3; j++)
                        {
                            full[i + 1][j + 1] = s[i][j];
                        }
                    }

                    Integer[][][][] res = square(full);

                    // Insertion of results
                    for (int i = 0; i < 3; i++)
                    {
                        List<Integer[][]> line2;
                        if (i + map.indexOf(l) < map2.size())
                        {
                            line2 = map2.get(i + map.indexOf(l));
                        } else
                        {
                            line2 = new ArrayList<Integer[][]>();
                        }
                        for (int j = 0; j < 3; j++)
                        {
                            line2.add(res[i][j]);
                        }
                        map2.add(line2);
                    }
                }
            }
            map = map2;
        }

        // TODO
        return null;
    }

    private static Integer[][][][] square(Integer[][] square)
    {
        Integer[][][][] ret = new Integer[3][3][3][3];

        for (int i = 1; i < 4; i++)
        {
            for (int j = 1; j < 4; j++)
            {
                Double value = square[i][j] * 0.20;
                value += square[i - 1][j] * 0.18;
                value += square[i + 1][j] * 0.18;
                value += square[i][j - 1] * 0.18;
                value += square[i][j + 1] * 0.18;
                value += square[i - 1][j - 1] * 0.02;
                value += square[i - 1][j + 1] * 0.02;
                value += square[i + 1][j - 1] * 0.02;
                value += square[i + 1][j + 1] * 0.02;
                for (int k = 0; k < 3; k++)
                {
                    for (int p = 0; p < 3; p++)
                    {
                        ret[i - 1][j - 1][k][p] = value.intValue();
                    }
                }
            }
        }

        return ret;
    }

    private static Integer[][][][] square2(Integer[][] square)
    {
        Integer[][][][] ret = new Integer[2][2][3][3];

        for (int i = 1; i < 4; i++)
        {
            for (int j = 1; j < 4; j++)
            {

                Double value = square[i][j] * 0.20;
                value += square[i - 1][j] * 0.18;
                value += square[i + 1][j] * 0.18;
                value += square[i][j - 1] * 0.18;
                value += square[i][j + 1] * 0.18;
                value += square[i - 1][j - 1] * 0.02;
                value += square[i - 1][j + 1] * 0.02;
                value += square[i + 1][j - 1] * 0.02;
                value += square[i + 1][j + 1] * 0.02;
                for (int k = 0; k < 3; k++)
                {
                    for (int p = 0; p < 3; p++)
                    {
                        ret[i - 1][j - 1][k][p] = value.intValue();
                    }
                }
            }
        }

        return ret;
    }
}
