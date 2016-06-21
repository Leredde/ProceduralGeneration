package graphic.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import graphic.PositionManager;

public class MouseMotionListenerImpl implements MouseMotionListener
{
    private PositionManager pm;

    public MouseMotionListenerImpl(PositionManager positionManager)
    {
	pm = positionManager;
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
	if (e.getButton() == MouseEvent.BUTTON2)
	{
	    pm.translate(e.getX(), e.getY());
	}
	else
	{
	    pm.rotate(e.getX(), e.getY());
	}
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
	// TODO Auto-generated method stub

    }
}
