import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
class MandelbrotParallax extends JPanel {

	private BufferedImage[] layers;
	
	private double x;
	private double y;
	private int xOffset;
	private int yOffset;
	
	//Constructor
	public MandelbrotParallax(BufferedImage[] i, int xResolution, int yResolution, double step){
		layers = i;
		xOffset = (layers[0].getWidth()-xResolution)/2;
		yOffset = (layers[0].getHeight()-yResolution)/2;
		x = xResolution/2 + xOffset;
		y = yResolution/2 + yOffset;

        addMouseMotionListener(new MouseAdapter() {
        	//Leaving mouseDragged in since mouseMoved only works when mouse is over the jFrame.
            public void mouseDragged(MouseEvent e) {
            	update(e.getX(),e.getY());
            }
        	public void mouseMoved(MouseEvent e) {
        		update(e.getX(),e.getY());
            }
        });
	}//End of MandelbrotParallax Constructor
	
	public void paint(Graphics g) {
		super.paint(g);
		for(int i = 1; i<=layers.length; i++){
//			System.out.println(x+", "+y);
			g.drawImage(layers[i - 1], (int) ((x-layers[0].getWidth()/2)/(1+layers.length-(i))-xOffset),
									   (int) ((y-layers[0].getHeight()/2)/(1+layers.length-(i))-yOffset), null);
		}
	}

    public void update(double x, double y) {
        this.x = x + xOffset;
        this.y = y + yOffset;
    	repaint(0,0,-2*(xOffset)+layers[0].getWidth(),-2*(yOffset)+layers[0].getHeight());
    	try {
			Thread.sleep(0,1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}