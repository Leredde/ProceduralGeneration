package graphic.listeners;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import graphic.PositionManager;

public class MouseWheelListenerImpl implements MouseWheelListener
{
    private PositionManager pm;

    public MouseWheelListenerImpl(PositionManager positionManager)
    {
	pm = positionManager;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
	pm.zoom(e.getWheelRotation());
    }

}
