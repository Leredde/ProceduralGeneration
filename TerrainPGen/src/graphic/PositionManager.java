package graphic;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

public class PositionManager
{
    private int prevX;
    private int prevY;
    private double posX;
    private double posY;
    private double posZ;
    private Canvas3D canvas;
    private double xTransformRatio;
    private double yTransformRatio;
    private TransformGroup tg;
    private Transform3D translation;
    private Transform3D rotation;

    public PositionManager(TransformGroup transformGroup, Canvas3D canvas3D)
    {
        tg = transformGroup;
        canvas = canvas3D;

        posX = 0.0;
        posY = 0.0;
        posZ = 0.0;
        
        translation = new Transform3D();
        rotation = new Transform3D();
        xTransformRatio = 1.0 / 6.0;
        yTransformRatio = 1.0 / 6.0;
    }

    public void updatePreviousPosition(int x, int y)
    {
        prevX = x;
        prevY = y;
    }

    public int getPreviousX()
    {
        return prevX;
    }

    public int getPreviousY()
    {
        return prevY;
    }

    public void translate(int x, int y)
    {
        int deltaX = x - prevX;
        int deltaY = y - prevY;

        prevX = x;
        prevY = y;
        
        posX = (double) deltaX;
        posY = (double) deltaY;

        Transform3D temp_t = new Transform3D();
        temp_t.setTranslation(new Vector3d(posX, posY, posZ));
        applyTranslation(temp_t);
    }
    
    public void zoom(int delta)
    {
        double deltaZ = delta * 0.1;

        posZ += deltaZ;
        
        Transform3D temp_t = new Transform3D();
        temp_t.setTranslation(new Vector3d(posX, posY, posZ));
        applyTranslation(temp_t);
    }

    public void rotate(int x, int y)
    {
        int deltaX = x - prevX;
        int deltaY = y - prevY;

        prevX = x;
        prevY = y;
        xTransformRatio += (deltaY / (double) canvas.getHeight()) % 1.0;

        // Normalize (not used, matrices are multiplied instead)
        // Pythagorean
        /*
         * double u = Math.sqrt(xTransformRatio*xTransformRatio +
         * yTransformRatio*yTransformRatio); double uX = xTransformRatio/u;
         * double uY = yTransformRatio/u;
         */

        // TODO: all rotation according to axis + transform ratio in
        // separate function
        double angleX = Math.PI * 2.0 * xTransformRatio;
        angleX = angleX % (Math.PI * 2);
        if (angleX < 0.0)
        {
            angleX += Math.PI * 2;
        }

        // double c = Math.cos(angleX);
        // double s = Math.sin(angleX);

        Transform3D temp_t_x = new Transform3D();
        temp_t_x.rotX(angleX);

        // Not nice, fix this rotation system (upside down case)
        if (angleX > Math.PI / 2 && angleX < 3 * Math.PI / 2)
        {
            yTransformRatio -= (deltaX / (double) canvas.getWidth()) % 1.0;
        } 
        else
        {
            yTransformRatio += (deltaX / (double) canvas.getWidth()) % 1.0;
        }

        double angleY = Math.PI * 2.0 * yTransformRatio;
        angleY = angleY % (Math.PI * 2);
        if (angleY < 0.0)
        {
            angleY += Math.PI * 2;
        }

        Transform3D temp_t_y = new Transform3D();
        temp_t_y.rotZ(angleY);
        temp_t_x.mul(temp_t_y);
        applyRotation(temp_t_x);
    }
    
    private void applyRotation(Transform3D rotation)
    {
	this.rotation = rotation;
	applyTransform();
    }
    
    private void applyTranslation(Transform3D translation)
    {
	this.translation = translation;
	applyTransform();
    }

    private void applyTransform()
    {
	Transform3D transform = new Transform3D(translation);
	transform.mul(rotation);
        tg.setTransform(transform);
    }
}
