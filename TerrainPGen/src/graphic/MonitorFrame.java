package graphic;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class MonitorFrame extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = -4014719919995335982L;
    private int mapSize;
    private int maxValue;
    private int[][] map;

    private void init()
    {
	map = new int[mapSize][mapSize];
	this.setUndecorated(true);
    }

    public MonitorFrame(int mapSize, int maxValue)
    {
	super();
	this.mapSize = mapSize;
	this.maxValue = maxValue;
	init();
    }

    public MonitorFrame(int mapSize, int maxValue, String string)
    {
	super(string);
	this.mapSize = mapSize;
	this.maxValue = maxValue;
	init();
    }

    public void updateData(Integer[][] data)
    {
	for (int i = 0; i < mapSize; i++)
	{
	    for (int j = 0; j < mapSize; j++)
	    {
		this.map[i][j] = data[i][j];
	    }
	}
    }

    @Override
    public void paint(Graphics g)
    {
	// super.paint(g);
	// g.setColor(Color.black);
	// g.clearRect(0, 0, this.getWidth(), this.getHeight());
	for (int i = 0; i < mapSize; i++)
	{
	    for (int j = 0; j < mapSize; j++)
	    {
		int greyLvl = (int) (255.0 * map[i][j] / maxValue);
		g.setColor(new Color(greyLvl, greyLvl, greyLvl));
		int ratioX = this.getWidth() / mapSize;
		int ratioY = this.getHeight() / mapSize;
		// g.drawRect(i*ratioX, j*ratioY, ratioX, ratioY);
		g.fillRect(i * ratioX, j * ratioY, ratioX, ratioY);
	    }
	}
    }
}
