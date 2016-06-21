package graphic;

import java.awt.GraphicsConfiguration;
import java.io.IOException;
import java.util.Arrays;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TriangleArray;
import javax.media.j3d.TriangleStripArray;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.universe.SimpleUniverse;

import algo.Algo2;
import graphic.listeners.MouseListenerImpl;
import graphic.listeners.MouseMotionListenerImpl;
import graphic.listeners.MouseWheelListenerImpl;

public class MapViewFrame extends JFrame
{

    /**
     * 
     */
    private static final long serialVersionUID = -5905825569152819637L;
    private SimpleUniverse u;
    private TransformGroup tg;
    private Shape3D mapShape;
    private Transform3D t;
    private Canvas3D canvas;
    private JSlider slider;
    private PositionManager pm;
    private int lod;
    private double size;
    // private Scale scale;

    public MapViewFrame(Algo2 algo,  double realSize, int levelOfDetail) throws IOException
    {
	size = realSize;
	lod = levelOfDetail;

	canvas = new Canvas3D(initGraphicsConf());

	tg = new TransformGroup();
	pm = new PositionManager(tg, canvas);

	canvas.addMouseListener(new MouseListenerImpl(pm));
	canvas.addMouseWheelListener(new MouseWheelListenerImpl(pm));
	canvas.addMouseMotionListener(new MouseMotionListenerImpl(pm));

	slider = new JSlider(JSlider.VERTICAL, 1, 9, 4);
	slider.setSnapToTicks(true);
	slider.setPaintTicks(true);
	slider.setMajorTickSpacing(1);
	slider.addChangeListener(new ChangeListener()
	{

	    @Override
	    public void stateChanged(ChangeEvent e)
	    {
		JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting())
		{
		    int powerOfTwo = (int) source.getValue();
		    int newLod = 1 << powerOfTwo;
		    lod = newLod;	
		    
		    int sizePx = (int) (lod * size);
		    try
		    {
			final Integer[][] map = algo.run(sizePx);
			// TODO : replace the displayed map by the new one
			replaceMap(map);
		    }
		    catch (IOException e1)
		    {
			e1.printStackTrace();
		    }
		}
	    }
	});

	JPanel panel = new JPanel();
	BoxLayout layout = new BoxLayout(panel, BoxLayout.LINE_AXIS);
	panel.setLayout(layout);
	panel.add(canvas);
	panel.add(slider);

	this.add(panel);


        int sizePx = (int)(lod * size);
        final Integer[][] map = algo.run(sizePx);
	initUniverse(map);
    }

    protected void replaceMap(Integer[][] map)
    {
	mapShape.setGeometry(createGeometryArray(map));
    }

    private void initUniverse(Integer[][] map)
    {
	// createUniverse(canvas);
	u = new SimpleUniverse(canvas);

	BranchGroup group = new BranchGroup();

	tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	tg.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
	t = new Transform3D();

	addMap(map);

	AxisAngle4d rot = new AxisAngle4d(1.0, 1.0, 1.0, Math.PI / 3.0);
	t.setRotation(rot);
	tg.setTransform(t);

	group.addChild(tg);

	// reference frame
	LineArray refLines = new LineArray(6, LineArray.COORDINATES | LineArray.COLOR_4);
	refLines.setCoordinate(0, new Point3d(0.0, 0.0, 0.0));
	refLines.setCoordinate(1, new Point3d(0.1, 0.0, 0.0));
	refLines.setColor(0, new Color4f(255, 0, 0, 200));
	refLines.setColor(1, new Color4f(255, 0, 0, 200));
	refLines.setCoordinate(2, new Point3d(0.0, 0.0, 0.0));
	refLines.setCoordinate(3, new Point3d(0.0, 0.1, 0.0));
	refLines.setColor(2, new Color4f(0, 255, 0, 200));
	refLines.setColor(3, new Color4f(0, 255, 0, 200));
	refLines.setCoordinate(4, new Point3d(0.0, 0.0, 0.0));
	refLines.setCoordinate(5, new Point3d(0.0, 0.0, 0.1));
	refLines.setColor(4, new Color4f(0, 0, 255, 200));
	refLines.setColor(5, new Color4f(0, 0, 255, 200));

	tg.addChild(new Shape3D(refLines));

	// Background
	Background background = new Background(new Color3f(1f, 1f, 1f));
	BoundingSphere sphere = new BoundingSphere(new Point3d(0, 0, 0), 100000);
	background.setApplicationBounds(sphere);
	group.addChild(background);

	// Ambient light
	Color3f ambientColor = new Color3f(0.045f, 0.043f, 0.04f);
	AmbientLight ambientLightNode = new AmbientLight(ambientColor);
	ambientLightNode.setInfluencingBounds(sphere);
	// group.addChild(ambientLightNode);

	// Directional light
	Color3f directionalColor = new Color3f(0.065f, 0.063f, 0.06f);
	Vector3f light1Direction = new Vector3f(14.0f, -10.0f, -1.0f);
	DirectionalLight light1 = new DirectionalLight(directionalColor, light1Direction);
	light1.setInfluencingBounds(sphere);

	// Position Light
	TransformGroup tgLight = new TransformGroup();
	tgLight.addChild(light1);
	tgLight.addChild(ambientLightNode);
	Transform3D transLight = new Transform3D();
	transLight.setTranslation(new Vector3d(10.0, 30.0, 1.0));
	tgLight.setTransform(transLight);

	tg.addChild(tgLight);

	u.getViewingPlatform().setNominalViewingTransform();
	u.addBranchGraph(group);

	u.getViewer().getView().setSceneAntialiasingEnable(false);
    }

    private GeometryArray createGeometryArray(Integer[][] map)
    {
	/*
	 * TriangleArray ta = new TriangleArray(map.length * map[0].length,
	 * TriangleArray.COORDINATES | TriangleArray.COLOR_3);
	 */

	int nbVertex = (map.length) * (map[0].length) * 3; // /!\ * 2 n'est pas
	// correct!
	int[] nbVertexPerStrip = new int[1];
	Arrays.fill(nbVertexPerStrip, nbVertex);
	int nbTriangle = map.length - 1 * map[0].length - 1 * 2;
	TriangleStripArray tsa = new TriangleStripArray(nbVertex, TriangleArray.COORDINATES | TriangleArray.COLOR_3,
		nbVertexPerStrip);

	double space = 1 / (double) lod;
	double hDivider = 1200.0; // 8 / space; // maybe link to actual real size
	int k = 0;
	Color3f groundColor = new Color3f(0.6f, 0.2f, 0.1f);
	Color3f flashyColor = new Color3f(0.8f, 0.6f, 0.1f);
	for (int i = 0; i < map.length - 1; i++)
	{
	    for (int j = 0; j < map[0].length; j++)
	    {
		int m;
		// changing direction every other time to build a nice and
		// consistent mesh
		if (i % 2 == 0)
		{
		    m = j;
		}
		else
		{
		    m = map[0].length - 1 - j;
		}

		// bypass direction changing
		m = j;

		Color3f color = groundColor;
		if (j == 0 || j == map[0].length - 1)
		{
		    color = flashyColor;
		}
		tsa.setColor(k, color);
		tsa.setCoordinate(k, new Point3d(i * space, (m) * space, map[i][m] / hDivider));
		k++;
		tsa.setColor(k, color);
		tsa.setCoordinate(k, new Point3d((i + 1) * space, (m) * space, map[i + 1][m] / hDivider));
		k++;
	    }
	}

	// Normals
	NormalGenerator ng = new NormalGenerator();
	GeometryInfo geoInfo = new GeometryInfo(tsa);
	ng.generateNormals(geoInfo);
	
	return geoInfo.getGeometryArray();
    }
    
    private void addMap(Integer[][] map)
    {
	GeometryArray mapGeom = createGeometryArray(map);

	BranchGroup bg = new BranchGroup();
	// Manage Appearance
	//
	//
	Appearance appearance = new Appearance();

	// Appearance capabilities
	appearance.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_WRITE);
	appearance.setCapability(Appearance.ALLOW_POINT_ATTRIBUTES_WRITE);

	ColoringAttributes colAtt = new ColoringAttributes(50, 50, 50, 255);
	appearance.setColoringAttributes(colAtt);

	// Polygon attributes
	PolygonAttributes polyAttribs = new PolygonAttributes(PolygonAttributes.POLYGON_FILL,
		PolygonAttributes.CULL_BACK, 0);
	appearance.setPolygonAttributes(polyAttribs);

	// Line Attributes: anti-aliasing
	// TODO may be set as global parameter
	LineAttributes lineAtt = new LineAttributes();
	lineAtt.setLineAntialiasingEnable(false);
	// lineAtt.setLinePattern(LineAttributes.PATTERN_DASH);
	appearance.setLineAttributes(lineAtt);

	// Point Attributes: anti-aliasing
	// TODO may be set as global parameter
	PointAttributes pointAtt = new PointAttributes();
	pointAtt.setPointAntialiasingEnable(false);
	appearance.setPointAttributes(pointAtt);

	// Color
	Color3f mapAmbColor = new Color3f(20.0f, 18.0f, 10.0f);
	Color3f mapSpecColor = new Color3f(25.5f, 2.0f, 1.5f);
	Color3f mapEmiColor = new Color3f(0f, 0f, 0f);
	Color3f mapDifColor = new Color3f(140f, 14.0f, 14.0f);
	appearance.setMaterial(new Material(mapAmbColor, mapEmiColor, mapDifColor, mapSpecColor, 0.5f));

	// Set Appearance
	mapShape = new Shape3D(mapGeom, appearance);
	mapShape.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
	bg.addChild(mapShape);

	// Map
	TransformGroup tgMap = new TransformGroup();
	tgMap.addChild(bg);
	Transform3D transMap = new Transform3D();

	double space = 1 / (double) lod;
	transMap.setTranslation(new Vector3d(-map.length * space / 2.0, -map[0].length * space / 2.0, -0.0005));
	tgMap.setTransform(transMap);

	tg.addChild(tgMap);
    }

    private GraphicsConfiguration initGraphicsConf()
    {
	GraphicsConfigTemplate3D gct3D = new GraphicsConfigTemplate3D();
	gct3D.setSceneAntialiasing(GraphicsConfigTemplate3D.PREFERRED);
	GraphicsConfiguration config = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment()
		.getDefaultScreenDevice().getBestConfiguration(gct3D);
	return config;
    }
}
