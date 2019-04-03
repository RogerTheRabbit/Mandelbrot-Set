import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// Links:
//	Links to interesting ones: http://www.cuug.ab.ca/~dewara/mandelbrot/Mandelbrowser.html
//	Tutorial: https://www.youtube.com/watch?v=0ySznjdXMEA

public class MandelbrotSet3_Parallax_Parallelized_Harrison_Optimization extends Thread{
	
	static final int NUM_THREADS = 4;
	static final int xResolution = 1440;
	static final int yResolution = 2560;
	static final boolean saveImages = false;
	
	//-1.1570776551228361 0.2
	static double centerA = 0;				//focus for A axis
	static double centerB = 0;				//for B axis
	static double radius  = 2;
	static  int   quality = 13;				//CPP program used 100000 //Matthias used 255*3
	static  int   width   = 900;			//6500
	static  int   height  = 900;			//9000
	static  int   layerCount = 6;			//**MUST BE LESS THAN OR EQUAL TO QUALITY**
	static BufferedImage[] coutImgArray = new BufferedImage[layerCount+1];
	private int   ID;
	
	static double step = .1;
	
	public static void main(String[] args)throws IOException, InterruptedException{
		DateFormat df = new SimpleDateFormat("MM_dd_yy HH-mm-ss");
		System.out.println("Started at: "+df.format(Calendar.getInstance().getTime()));
		
		Thread[] Threads = new Thread[NUM_THREADS];
		File f = null;
			for(int x = 0; x<NUM_THREADS; x++){
				Threads[x] = new MandelbrotSet3_Parallax_Parallelized_Harrison_Optimization(x);
				Threads[x].start();
				//Shove the threads.join thing here for sequential thread runs.
			}
			for(int x = 0; x<NUM_THREADS; x++){
				//Shove the threads.join thing here for concurrency.
				try {
					Threads[x].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(saveImages){
				int counter = 0;
				String time = df.format(Calendar.getInstance().getTime());
				for(BufferedImage img : coutImgArray){
					try{
						f = new File("F:\\Eclipse\\Workspaces\\Javer\\Mandelbrot Set\\src\\OutputImages\\Parallax Images\\Parallax layer "+counter+" at "+time+".png");
						ImageIO.write(img, "png", f);
						counter++;
					}catch (IOException e){
						System.out.println("Error: " + e);
					}
				}
			}
			
			System.out.println("Done at: "+df.format(Calendar.getInstance().getTime()));
		
			
		MandelbrotParallax mandelImg = new MandelbrotParallax(coutImgArray, xResolution, yResolution, step);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI(mandelImg);
			}
		});
		
		
//		//Move mouse in "circle"
		while (true){
			int r = 200;
			double h = 0;
			double w = -r;
			double percent;
			for(double stepCounter = -r; stepCounter<=r; stepCounter+=step){
				percent = stepCounter/(2*r);
				w = r*Math.cos(percent*Math.PI*2)*2;
				h = r*Math.sin(percent*Math.PI*2);
				mandelImg.update(w+xResolution/2, h+yResolution/2);
				Thread.sleep(0,1);
			}
		}
		
		
		
		
	}//End of MAIN
	
	//Creates GUI
	private static void createAndShowGUI(MandelbrotParallax mandelImg) {
		JFrame f = new JFrame("Parallax");													//Creates JFrame
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);									//Sets close operation
		f.setUndecorated(true);																//Removes border from window
		f.pack();																			//Not sure what this does
		f.setSize(xResolution, yResolution);												//Sets the size of the window
		f.setVisible(true);																	//Makes the window visible
		f.toFront();																		//Brings window to front
		f.add(mandelImg);		//adds images to GUI
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();						//Gets screen dimensions
		f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2);	//Moves jFrame to center of the screen
		
//		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(				//Makes blank Cursor
//				new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor");
//		f.getContentPane().setCursor(blankCursor);											//Sets blank cursor
			
	}//End of GUI creation
	
	public static int Calculate_MandlebrotSet(Complex z, int loopFor){
		Complex tmp = new Complex();
		
		for(int i = 1; i <= loopFor; i++){
			tmp = Complex.add(tmp.pow(2), z);
			if (tmp.dist() >= 2.0)
				return i;
		}
		return 0;
	}//End of Calculate_MandlebrotSet
	
	public static int Calculate_JuliaSet(Complex z, Complex c, int loopFor){
		Complex tmp = Complex.add(z.pow(2), c);
		for(int i = 1; i <= loopFor; i++){
			tmp = Complex.add(tmp.pow(2), c);
			if (tmp.dist() >= 2.0)
				return i;
		}
		return 0;
	}//End of Calculate_JuliaSet

	public MandelbrotSet3_Parallax_Parallelized_Harrison_Optimization(int id){
		//Stores parameters for use in threading
		this.ID = id;
	}

	public void run(){
		for(int x = 0; x<coutImgArray.length; x++){
			coutImgArray[x] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		}
		
		System.out.println("Thread " + ID + " started.");
		int countWidth  = 0;
		int countHeight = 0;
		
		int a;
		int r;
		int g;
		int b;
		int p;

		int longer = width > height ? width: height;
		double goToHeight = centerB - (radius*height) / longer;
		double goToWidth  = centerA + (radius*width)  / longer;
		
		a = 0; r = 0; g = 0; b = 0;
		for (double h = centerB + (radius*height)/longer; h >= goToHeight && countHeight < height - 1; h -= ((2*radius)/longer)){//*(NUM_THREADS)) {
			for (double w = centerA - (radius*width)/longer; w <= goToWidth && countWidth < width - 1; w += (2*radius)/longer) {
				//Mandelbrot Set
            	int k = MandelbrotSet3_Parallax_Parallelized_Harrison_Optimization.Calculate_MandlebrotSet(new Complex(w, h), (quality));
            	//Julia Set
//	            int k = MandelbrotSet3_Parallelized_Harrison_Optimization.Calculate_JuliaSet(new Complex(w, h), new Complex(-0.726895347709114071439,0.188887129043845954792),(quality));
            	if(k == 0){
                	a = 255;
                	r = 0;
                	g = 0;
                    b = 0;
	                p = (a<<24) | (r<<16) | (g<<8) | b;
	                coutImgArray[0].setRGB(countWidth, countHeight, p);
            	}
            	else if(k > 1){
                	a = 255;
                	r = (k%255%50*50)+000;
                	g = (k%255%50*50)+100;
                    b = (k%255%50*50)+150;
	                p = (a<<24) | (r<<16) | (g<<8) | b;
//	                coutImgArray[(int)((k+1)/(quality/(double)layerCount))].setRGB(countWidth, countHeight, p);
	                coutImgArray[((g) + (quality)*(g/(layerCount)))%(layerCount+1)].setRGB(countWidth, countHeight, p);
                }
                countWidth++;
            }
            countWidth = 0;
            countHeight++;

            //Percentage Counter
			if((((double)countHeight/height)*100)%10 == 0){
				System.out.println(ID+": "+(((double)countHeight/height))*100 +"%");
			}//End of Percentage Counter
		}//End of Height loop
	}//End of RUN
}//End of CLASS


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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}