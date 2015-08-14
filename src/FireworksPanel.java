import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

//background image courtesy of Stuart Miles at FreeDigitalPhotos.net

public class FireworksPanel extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = -7547909642877074891L;
	private LinkedList<Spark> sparks = new LinkedList<Spark>();
	private final Dimension maxDimension = new Dimension(400, 300);
	private Random randgen = new Random();
	//private Color transparent = new Color(0, 0, 0, 0);
	public int sparksLeft(){return sparks.size();}
	public boolean removeSpark(Spark s){return this.sparks.remove(s);}
	public Image background = Toolkit.getDefaultToolkit().getImage("pagodaBg.jpg");
	
	public FireworksPanel(){
		this.setPreferredSize(maxDimension);
		this.setMaximumSize(maxDimension);
		this.setMinimumSize(maxDimension);
		this.setLayout(null);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
		//g.setColor(transparent);
		//Rectangle clipped = g.getClip().getBounds();
		//g.fillRect(0, 0, clipped.width, clipped.height);
		Graphics2D g2D = (Graphics2D)g;
		Spark array[] = sparks.toArray(new Spark[0]);
		for(Spark s : array) {
			s.draw(g2D);
		}
	}
	
	private void explode(int x, int y) {
		int sparkCount = 50 + randgen.nextInt(20);
		Color color = new Color(randgen.nextInt(255), randgen.nextInt(255), randgen.nextInt(255));
		long lifespan = 1000 + randgen.nextInt(1000);
		int choice = randgen.nextInt(100);

		if (choice < 60) {
			createTrigSpark(x, y, sparkCount, color, lifespan);
		} else {
			createMovingSpark(x, y, sparkCount, color, lifespan);
		}
	}
	
	private void createTrigSpark(int x, int y, int sparkCount, Color c, long lifespan) {
		for (int i = 0; i < sparkCount; i++) {
			double direction = 360 * randgen.nextDouble();
			double speed = 10 * randgen.nextDouble() + 5;
			sparks.addLast(new NormalSpark(this, direction, x, y, c, lifespan, speed));
		}
	}
	
	private void createMovingSpark(int x, int y, int sparkCount, Color c, long lifespan) {
		for (int i = 0; i < sparkCount; i++) {
			double direction = 360 * randgen.nextDouble();
			double speed = 10 * randgen.nextDouble() + 5;
			sparks.addLast(new BlobSpark(this, direction, x, y, c, lifespan, speed));
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		explode(e.getX(), e.getY());
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {
	}
}
