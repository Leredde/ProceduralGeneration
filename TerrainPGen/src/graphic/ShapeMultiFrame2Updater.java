package graphic;

import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryUpdater;
import javax.media.j3d.TriangleStripArray;

public class ShapeMultiFrame2Updater implements GeometryUpdater{
	
	private double[] coords;
	
	@Override
	public void updateData(Geometry geom) {
		if(geom instanceof TriangleStripArray)
		{
			TriangleStripArray t = ((TriangleStripArray)geom);
						
			float[] interleavedVertices = t.getInterleavedVertices();
			System.out.println(interleavedVertices.length);
			
			for(int i=0; i<interleavedVertices.length; i++)
			{
				if(i%6 > 3)
				{
					System.out.println(interleavedVertices[i]);
				}
			}
			//t.setCoordRefDouble(coords);	
			//((TriangleStripArray)geom).setCoordinates(0, coords);	
		}
	}
	
	public void setCoords(double[] coords)
	{
		this.coords = coords;
	}
	
}
