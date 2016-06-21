package graphic.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import graphic.PositionManager;

public class MouseListenerImpl implements MouseListener
{
    private PositionManager pm;
    
    public MouseListenerImpl(PositionManager positionManager)
    {
        pm = positionManager;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        pm.updatePreviousPosition(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        // TODO Auto-generated method stub
        
    }

}
