import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
// Links:
//	Links to interesting ones: http://www.cuug.ab.ca/~dewara/mandelbrot/Mandelbrowser.html
//	Tutorial: https://www.youtube.com/watch?v=0ySznjdXMEA

public class MandelbrotSet3_Parallelized_Harrison_Optimization extends Thread{

//	Static double centerA = -1.25066;	//focus for A axis
//	Static double centerB = 0.02012;	//      for B axis
//	Static double radius  = 0.00017;
//	Static  int   quality = 255;		//CPP program used 100000 //Matthias used 255*3

//	static double centerA = -1.25066;		//focus for A axis
//	static double centerB = 0.02012;				//for B axis
//	static double radius  = .000017;
//	static  int   quality = 255*10000;			//CPP program used 100000 //Matthias used 255*3
//	static  int   width   = 3840;
//	static  int   height  = 2160;

//	static double centerA = -1.1570776551228361;		//focus for A axis
//	static double centerB = 0.2;				//for B axis
//	static double radius  = 0.00000000000000000004;
//	static  int   quality = 25500;			//CPP program used 100000 //Matthias used 255*3
//	static  int   width   = 2000;
//	static  int   height  = 2000;
	

	static double centerA = 0;		//focus for A axis
	static double centerB = 0;		//for B axis
	static double radius  = 2;
	static  int   quality = 2000;	//CPP program used 100000 //Matthias used 255*3
	static  int   width   = 1440;	//6500
	static  int   height  = 2560;	//9000
	private int   ID;
	private BufferedImage coutImg;
	static final int NUM_THREADS = 10;
	
	public static void main(String[] args)throws IOException{
		DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		System.out.println("Started at: "+df.format(Calendar.getInstance().getTime()));
		
		BufferedImage coutImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		
		Thread[] Threads = new Thread[NUM_THREADS];
		File f = null;
		
		for(int x = 0; x<NUM_THREADS; x++){
			Threads[x] = new MandelbrotSet3_Parallelized_Harrison_Optimization(coutImg, x);
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
		
		try{
			//f = new File("F:\\Eclipse\\Workspaces\\Javer\\Mandelbrot Set\\src\\OutputImages\\"+"["+width+" x " +height+"] "+centerA+", "+centerB+", "+radius+", "+quality+" modded colors style"+".png");
			f = new File("F:\\Eclipse\\Workspaces\\Javer\\Mandelbrot Set\\src\\OutputImages\\MultiThreadingTest_Harrison Optimization.png");
			ImageIO.write(coutImg, "png", f);
		}catch (IOException e){
			System.out.println("Error: " + e);
		}
		System.out.println("Done at: "+df.format(Calendar.getInstance().getTime()));
	}//End of MAIN
	
	
	public static int Calculate_MandlebrotSet(Complex z, int loopFor){
		Complex tmp = new Complex();
		for(int i = 1; i <= loopFor; i++){ //or we could just have initialized i as 1 in the first place, but shut up//We did actually end up doing that, but shut up
			tmp = Complex.add(tmp.pow(2), z);
			//tmp = Complex.add(Complex.multiply(tmp, Complex.multiply(tmp,tmp)), z);
			if (tmp.dist() >= 2.0)
				return i;
		}
		return 0;
	}//End of Calculate_MandlebrotSet
	
	public static int Calculate_JuliaSet(Complex z, Complex c, int loopFor){
		Complex tmp = Complex.add(z.pow(2), c);
		for(int i = 1; i <= loopFor; i++){ //or we could just have initialized i as 1 in the first place, but shut up//We did actually end up doing that, but shut up
			tmp = Complex.add(tmp.pow(2), c);
			//tmp = Complex.add(Complex.multiply(tmp, Complex.multiply(tmp,tmp)), z);
			if (tmp.dist() >= 2.0)
				return i;
		}
		return 0;
	}//End of Calculate_JuliaSet

	public MandelbrotSet3_Parallelized_Harrison_Optimization(BufferedImage image, int id){
		//Stores parameters for use in threading
		this.coutImg = image;
		this.ID = id;
	}

	public void run(){
//		System.out.print(ID+" / "+NUM_THREADS+" * "+height+" = ");
//		System.out.println((ID/NUM_THREADS)*height);
		System.out.println("Thread " + ID + " started.");
		int countWidth  = 0;
		int countHeight = 0;
		
		int a = 0;
		int r = 0;
		int g = 0;
		int b = 0;
		int p = 0;

		int longer = width > height ? width: height;
		double goToHeight = centerB - (radius*height) / longer;
		double goToWidth  = centerA + (radius*width)  / longer;
		
		for (double h = centerB + (radius*height)/longer; h >= goToHeight; h -= ((2*radius)/longer)){//*(NUM_THREADS)) {
			for (double w = centerA - (radius*width)/longer; w <= goToWidth; w += (2*radius)/longer) {
	            	int k = MandelbrotSet3_Parallelized_Harrison_Optimization.Calculate_MandlebrotSet(new Complex(w, h), (quality));
	            	//int k = MandelbrotSet3_Parallelized_Harrison_Optimization.Calculate_JuliaSet(new Complex(w, h), new Complex(-0.726895347709114071439,0.188887129043845954792),(quality));
	            	if (k == 0) {
	                	a = 255;
	                	r = 0;
	                	g = 0;
	                    b = 0;
	                }
	                else {
	                	a = 255;
	                	r = (k%255%50*50)+000;
	                	g = (k%255%50*50)+100;
	                    b = (k%255%50*50)+150;
	                }
//	            	else {
//	                	a = 255;
//	                	r = (k%255);
//	                	g = (k+100)%255;
//	                    b = (k+150)%255;
//	                }
	
	                p = (a<<24) | (r<<16) | (g<<8) | b;
					//if (ID!=0) 
	                coutImg.setRGB(countWidth, countHeight, p);
	                if (countWidth>width-1) break;			//THE -1 IS IMPORTANT DO NOT REMOVE
	                else countWidth++;
				
//				else {
//					//if (countHeight+NUM_THREADS-1>height) break;
//					countHeight+=ID+1;
//				}
            }
            countWidth = 0;
//            if (countHeight>height-NUM_THREADS-1) break;	//THE -1 IS IMPORTANT DO NOT REMOVE
//            countHeight+=NUM_THREADS;
            if (countHeight>height-1) break;	//THE -1 IS IMPORTANT DO NOT REMOVE
            countHeight++;
			
			if((((double)countHeight/height)*100)%10 == 0){
				System.out.println(ID+": "+(((double)countHeight/height))*100 +"%");
				
//				//TEST PRINT IMAGE FOR EACH THREAD AHHHHHHHHHHHHHHHHHH
//				File f = null;
//				for(int x = 0; x<NUM_THREADS; x++){
//					try{
//						
//						//f = new File("F:\\Eclipse\\Workspaces\\Javer\\Mandelbrot Set\\src\\OutputImages\\"+"["+width+" x " +height+"] "+centerA+", "+centerB+", "+radius+", "+quality+" modded colors style"+".png");
//						f = new File("F:\\Eclipse\\Workspaces\\Javer\\Mandelbrot Set\\src\\OutputImages\\test\\"+ID+"-"+(((double)countHeight/height))*100 +"%+"+".png");
//						ImageIO.write(coutImg, "png", f);
//					}catch (IOException e){
//						System.out.println("Error: " + e);
//					}
//				}
//				//TESTINGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG	

			}//End of Percentage Counter
		}//End of Height loop
		
	}//End of RUN
	
}//End of CLASS
