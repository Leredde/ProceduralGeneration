package graphic;

import javax.media.j3d.Appearance;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.media.j3d.TriangleStripArray;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class ShapeMultiFrame2Manager {

	private static String FILE_PATH = "C:\\Users\\lleredde\\Documents\\LLE\\CEA\\DD\\AMP3D\\ComplexShapes\\multiFrameBlender.obj";
	
	private Transform3D transform;
	private TransformGroup transformGroup;
	private Shape3D shape3D;
	private Appearance appearance;
	private ShapeMultiFrame2Updater updater;
	
	private double width = 0.5;
	private double height = 2.0;
	private double length = 1.0;
	private double leftWidth = 0.1;
	private double rightWidth = 0.1;
	private double upperWidth = 0.1;
	private double lowerWidth = 0.1;
	private double centerWidth = 0.05;
		
	public ShapeMultiFrame2Manager()
	{
    	//Load Slab Frame Obj file
		
		shape3D = new Shape3D();
		
		shape3D.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);

		TriangleStripArray geom = (TriangleStripArray) shape3D.getGeometry();
		//geom.set
		
    	//TriangleStripArray geom = new TriangleStripArray(24, TriangleStripArray.COORDINATES, arg2)
		geom.setCapability(TriangleStripArray.ALLOW_COORDINATE_WRITE);
		geom.setCapability(TriangleStripArray.ALLOW_REF_DATA_WRITE);
    	
    	// Manage Appearance
    	//
    	//
    	appearance = new Appearance();
    	
    	// Appearance capabilities
		appearance.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_WRITE);
		appearance.setCapability(Appearance.ALLOW_POINT_ATTRIBUTES_WRITE);
		
		// Transparency attribute: 50%
		TransparencyAttributes transAtt = 
				new TransparencyAttributes( TransparencyAttributes.BLENDED,0.5f,
											TransparencyAttributes.BLEND_SRC_ALPHA,
											TransparencyAttributes.BLEND_ONE_MINUS_SRC_ALPHA );
		appearance.setTransparencyAttributes(transAtt);

		// Polygon attributes
		PolygonAttributes polyAttribs = new PolygonAttributes( PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_NONE, 0 );
		appearance.setPolygonAttributes(polyAttribs );
		
		// Line Attributes: anti-aliasing
		// TODO may be set as global parameter
		LineAttributes lineAtt = new LineAttributes();
		lineAtt.setLineAntialiasingEnable(true);
		//lineAtt.setLinePattern(LineAttributes.PATTERN_DASH);
		appearance.setLineAttributes(lineAtt);	
		
		// Point Attributes: anti-aliasing
		// TODO may be set as global parameter
		PointAttributes pointAtt = new PointAttributes();
		pointAtt.setPointAntialiasingEnable(true);	
		appearance.setPointAttributes(pointAtt);
		
		// Color
		Color3f a2AmbColor = new Color3f(85f, 0f, 0f);
		Color3f a2EmiColor = new Color3f(0f, 0f, 0f);
		Color3f a2DifColor = new Color3f(255f, 1f, 1f);
		appearance.setMaterial(new Material(a2AmbColor,a2EmiColor,a2DifColor,a2AmbColor,0.5f));
		
		// Set Appearance
		shape3D.setAppearance(appearance);
		
		// Transform Group
		transform = new Transform3D();
		transform.setIdentity();
		transformGroup = new TransformGroup();	
		transformGroup.setTransform(transform);
		transformGroup.addChild(shape3D);
		
		updater = new ShapeMultiFrame2Updater();		
	}
	
	/**
	 * @return
	 */
	public Node getShapeNode()
	{
		return transformGroup;
	}

	// Scale
	//
	//
	/**
	 * @param ratio
	 */
	public void rescale(double ratio)
	{
		Transform3D rescale = new Transform3D();
		rescale.setScale(ratio);
		transform.mul(rescale);
		transformGroup.setTransform(transform);
	}
	
	/**
	 * @param ratioX
	 * @param ratioY
	 * @param ratioZ
	 */
	public void rescaleAxis(double ratioX, double ratioY, double ratioZ)
	{
		Transform3D rescale = new Transform3D();
		rescale.setScale(new Vector3d(ratioX, ratioY, ratioZ));
		transform.mul(rescale);
		transformGroup.setTransform(transform);
	}
	
	// Position
	//
	//
	// TODO
	
	// Rotation
	//
	//
	// TODO
	
	// Custom Dimensions
	//
	//
	// TODO
	/**
	 * @param thickness
	 * 
	 */
	public void setThicknessX(double thickness)
	{
		TriangleStripArray geom = (TriangleStripArray) shape3D.getGeometry();
		//geom.setCoordinate(index, newCoord);
	}
	
	private void applyReshape()
	{
		TriangleStripArray geom = (TriangleStripArray) shape3D.getGeometry();

		double[] coords = {
				-height/2.0, width/2.0, length/2.0,
				-height/2.0, width/2.0, -length/2.0,
				-height/2.0, -width/2.0, -length/2.0, //3
				-height/2.0, -width/2.0, length/2.0, 
				height/2.0, width/2.0, length/2.0,
				height/2.0, -width/2.0, length/2.0, //6
				-height/2.0+lowerWidth, width/2.0, length/2.0-rightWidth,
				-height/2.0+lowerWidth, width/2.0, -length/2.0+leftWidth,
				-centerWidth/2.0, width/2.0, -length/2.0+leftWidth, //9
				height/2.0, width/2.0, -length/2.0,
				height/2.0, -width/2.0, -length/2.0,
				-centerWidth/2.0, -width/2.0, -length/2.0+rightWidth, //12
				-height/2.0+lowerWidth, -width/2.0, -length/2.0+leftWidth,
				-height/2.0+lowerWidth, -width/2.0, length/2.0-rightWidth,
				centerWidth/2.0, width/2.0, length/2.0-rightWidth, //15
				-centerWidth/2.0, width/2.0, length/2.0-rightWidth,
				height/2.0-upperWidth, width/2.0, -length/2.0+leftWidth, 
				height/2.0-upperWidth, width/2.0, length/2.0-rightWidth, //18
				centerWidth/2.0, -width/2.0, length/2.0-rightWidth,
				height/2.0-upperWidth, width/2.0, length/2.0-rightWidth,
				height/2.0-upperWidth, -width/2.0, -length/2.0+leftWidth, //21
				-centerWidth/2.0, -width/2.0, length/2.0-rightWidth,
				centerWidth/2.0, width/2.0, -length/2.0+leftWidth, 
				centerWidth/2.0, -width/2.0, -length/2.0+leftWidth//24
		};
		//geom.setCoordinates(0, coords);
		updater.setCoords(coords);
		//updater.updateData(geom);
		geom.updateData(updater);
		shape3D.setGeometry(geom);
	}
	
	// Overall "Box" Dimensions (scale-like) - needed?
	//
	//
	// TODO
	
	
	
	
	// Getter/setters
	//
	//	
	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getLeftWidth() {
		return leftWidth;
	}

	public void setLeftWidth(double leftWidth) {
		this.leftWidth = leftWidth;
	}

	public double getRightWidth() {
		return rightWidth;
	}

	public void setRightWidth(double rightWidth) {
		this.rightWidth = rightWidth;
	}

	public double getLowerWidth() {
		return lowerWidth;
	}

	public void setLowerWidth(double lowerWidth) {
		this.lowerWidth = lowerWidth;
	}

	public double getCenterWidth() {
		return centerWidth;
	}

	public void setCenterWidth(double centerWidth) {
		this.centerWidth = centerWidth;
		applyReshape();
	}

}
