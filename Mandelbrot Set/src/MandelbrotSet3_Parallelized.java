import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

// Links to interesting ones: http://www.cuug.ab.ca/~dewara/mandelbrot/Mandelbrowser.html




//Tutorial: https://www.youtube.com/watch?v=0ySznjdXMEA

public class MandelbrotSet3_Parallelized extends Thread{

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
	static double centerB = 0;				//for B axis
	static double radius  = 2;
	static  int   quality = 2555;			//CPP program used 100000 //Matthias used 255*3
	static  int   width   = 6500;	//6500
	static  int   height  = 6500;	//9000
	private int   ID;
	private BufferedImage coutImg;
	static final int NUM_THREADS = 10;
	
	public static void main(String[] args)throws IOException{
		DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		System.out.println("Started at: "+df.format(Calendar.getInstance().getTime()));
		
		BufferedImage coutImg = new BufferedImage(width+1, height+1, BufferedImage.TYPE_INT_BGR);
		
		Thread[] Threads = new Thread[NUM_THREADS];
		File f = null;
		
		for(int x = 0; x<NUM_THREADS; x++){
			Threads[x] = new MandelbrotSet3_Parallelized(coutImg, x);
			Threads[x].start();
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
			f = new File("F:\\Eclipse\\Workspaces\\Javer\\Mandelbrot Set\\src\\OutputImages\\MultiThreadingTest.png");
			ImageIO.write(coutImg, "png", f);
		}catch (IOException e){
			System.out.println("Error: " + e);
		}
		System.out.println("Done at: "+df.format(Calendar.getInstance().getTime()));
	}//End of MAIN
	
	
	
	
	
	public static int Calculate(Complex z, int loopFor){
		Complex tmp = new Complex();
		for(int i = 1; i <= loopFor; i++){ //or we could just have initialized i as 1 in the first place, but shut up//We did actually end up doing that, but shut up
			tmp = Complex.add(Complex.multiply(tmp,tmp), z);
			if (tmp.dist() >= 2.0)
				return i;
		}
		return 0;
	}//End of Calculate

	public MandelbrotSet3_Parallelized(BufferedImage image, int id){
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
		//////////
		int p = 0;

		int longer = width > height ? width: height;
		double offset = ((2*radius)/NUM_THREADS);
		double goToHeight = (centerB - (radius*height) / longer) + offset*(NUM_THREADS-(ID+1));//*(height/longer);
		double goToWidth  = centerA + (radius*width)  / longer;
		
		double from = centerB - offset*ID + (radius*height)/longer;
		
		System.out.println("offset "+ID+": "+offset);
		System.out.println("From "+ID+": "+from);
		System.out.println("To   "+ID+": "+goToHeight);
		
		for (double h = from; h >= goToHeight; h -= (2*radius)/longer) {
			for (double w = centerA - (radius*width)/longer; w <= goToWidth; w += (2*radius)/longer) {
            	int k = MandelbrotSet3_Parallelized.Calculate(new Complex(w, h), (quality));
            	if (k == 0) {
                	a = 255;
                	r = 0;
                	g = 0;
                    b = 0;
                }
            	
                else {
                	a = 255;
                	r = (k%255%50*50)+0;
                	g = (k%255%50*50)+100;
                    b = (k%255%50*50)+150;
                }
            	
//            	else {
//                	a = 255;
//                	r = (k%255);
//                	g = (k+100)%255;
//                	b = (k+150)%255;
//                }
            	
                p = (a<<24) | (r<<16) | (g<<8) | b;	//Puts the ARGB values into a single number using bitwise functions
                coutImg.setRGB(countWidth, countHeight+(int)(((double)ID/NUM_THREADS)*height), p);
                
                
                
                
                //System.out.println(countHeight);
                
                
                
                
                
                
                
				if (countWidth>width-1) break;
                else countWidth++;
            }//End of Width loop
            countWidth = 0;
            if (countHeight>height-1) break;
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
