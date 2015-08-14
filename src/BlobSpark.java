import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class BlobSpark implements Spark{
	private long lifespan;
	public double maxSpeed = 10;
	public double accel;
	private final int maxRadius = 3;
	private final int maxDiameter = maxRadius * 2;
	private double direction;
	private long spawnTime;
	private Color c;
	private double x;
	private double y;
	private Ellipse2D.Double spark;
	private FireworksPanel parent;
	
	public BlobSpark(FireworksPanel parent, double direction, double x, double y, Color c, long lifespan, double maxSpeed) {
		this.lifespan = lifespan;
		this.maxSpeed = maxSpeed;
		this.parent = parent;
		this.direction = direction;
		this.c = c;
		this.x = x - maxRadius;
		this.y = y - maxRadius;
		this.spark = new Ellipse2D.Double(0, 0, maxDiameter, maxDiameter);
		this.spawnTime = System.currentTimeMillis();
		this.accel = - 1.0 / lifespan * maxSpeed * 1.1;
	}
	
	
	private void step() {
		long currentTime = System.currentTimeMillis();
		long currentLifeLength = currentTime - spawnTime;
		if ( currentLifeLength < lifespan) {
			double currentSpeed = maxSpeed + accel * currentLifeLength;
			double dx = currentSpeed * Math.cos(Math.toRadians(direction));
			double dy = currentSpeed * Math.sin(Math.toRadians(direction));
			spark.x += dx;
			spark.y += dy;
			x += dx;
			y += dy;
			double shrink = 1 - ((double)currentLifeLength / lifespan);
			spark.height = maxDiameter * shrink;
			spark.width  = maxDiameter * shrink;
		} else {
			if (parent.sparksLeft() == 1) {
				parent.repaint();
			}
			parent.removeSpark(this);
		}
	}
	public void draw(Graphics2D g2d) {
		step();
		g2d.setColor(c);
		double loops = 70;
		for (int i = (int)loops; i > 0; i--) {
			double scale = Math.sin(i);
			AffineTransform at = AffineTransform.getTranslateInstance(x, y);
			at.scale(scale, scale);
			scale = ((double)i) / loops;
			Color newColor = new Color(c.getRed(), c.getGreen(), c.getBlue(), Math.min((int)Math.round(255 * (1/scale)), 255));
			g2d.setColor(newColor);
			g2d.fill(at.createTransformedShape(spark));
		}
	}
}
