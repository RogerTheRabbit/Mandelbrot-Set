import java.awt.image.BufferedImage;

public class MandelbrotSet_parallel extends Thread {
	
	//LIGHTNING
	static double focusA = -0.235125; //focus for A axis
	static double focusB = 0.827215; //for B axis
	static double radius = 0.00004;
	
	//BASIC
//	static double focusA = -0.5; //focus for A axis
//	static double focusB = 0; //for B axis
//	static double radius = 1;
	
	////COOL
//	static double focusA = -0.7463; //focus for A axis
//	static double focusB = 0.1102; //for B axis
//	static double radius = 0.005;
	
//	static double focusA = -0.722; //focus for A axis
//	static double focusB = 0.246; //for B axis
//	static double radius = 0.019;

	 //fA = -1.1570776551228361, Fb = 0.2
//	static double focusA = -1.1533577030005; //focus for A axis
//	static double focusB = 0.307486987838885; //for B axis
//	static double radius = 0.00000000053;
	
	
//	static double focusA = 0; //focus for A axis
//	static double focusB = 0; //for B axis
//	static double radius = 2;
	
	private int ID;
	private BufferedImage  image;
	static final int NUM_THREADS = 8;
	
	
	
	
	///MAINNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN
    public static void main(String[] args) {
        int width = 40000;
        int height = width;
        
        BufferedImage image = new BufferedImage(width + 1, height + 1, BufferedImage.TYPE_INT_BGR);
        
        Thread[] r = new Thread[NUM_THREADS];
        
        for (int i = 0; i < NUM_THREADS; i++)
        {
        	r[i] = new MandelbrotSet_parallel(image, i);
        	r[i].start();        	
        }
        
        
			try {
				for (int i = 0; i < NUM_THREADS; i++)
					r[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        
        GetSetPixels.writeImg(image, "F:/output_from_zoom.png");
    }
    
    
    
    
    
    
    public static int Calculate(Complex z, int loopFor){
        Complex tmp = new Complex();
        for(int i = 1; i <= loopFor; i++){ //or we could just have initialized as 1 in the first place, but shut up
            tmp = Complex.add(Complex.multiply(tmp,tmp), z);
            if (tmp.dist() >= 2.0)
                return i;
        }
        return 0;
    }
    
    
	   public MandelbrotSet_parallel(BufferedImage i, int id) {
	       // store parameter for later user
		   image = i;
		   ID = id;
	   }

	   public void run() {
		   
	    	int quality = 10000; //higher quality if number is larger        
	        int counterW = 0;
	        int counterH = ID * (image.getHeight()- 1) / (NUM_THREADS);
	        int p = 0;
	        
	        boolean lastBlack = false;
	        
	        double offset = (2 * radius)/(NUM_THREADS);
	        double cH = (2*radius)/(image.getHeight() - 1);
	        double cW = (2*radius)/(image.getWidth() - 1);
	        
	        for (double h = focusB + radius - ID * offset; h >= focusB + radius - (ID + 1) * offset && counterH < image.getHeight(); h -= cH) {
	            for (double w = focusA - radius; w <= focusA + radius && counterW < image.getWidth(); w += cW) {
	            	p = 0;
	                int k = MandelbrotSet_parallel.Calculate(new Complex(w, h), (quality));
	                p = GetSetPixels.setA(p, 0);

	                if (k == 0)
	                {
	                    p = GetSetPixels.setR(p, 0);
	                    p = GetSetPixels.setB(p, 0);
	                    p = GetSetPixels.setG(p, 0);     
	                    
	                    GetSetPixels.setARGB(image, counterW, counterH, p);	 
	                    
	                    if (lastBlack && counterW - 2 >= 0)
	                    {
	                    	GetSetPixels.setARGB(image, counterW - 1, counterH, p);
	                    }
	                    
	                    lastBlack = true;
	                    
	                    if (counterW + 2 < image.getWidth())
	                    {
		                    counterW += 2;
		                    w += cW; //decrease w to account for jump over counterW + 1;	                    	
	                    }
	                    else
	                    {
	                    	counterW++;
	                    	lastBlack = false;
	                    }

	                }
	                else
	                {
	                	//cool 1
//		                p = GetSetPixels.setR(p, (k) % 255);
//		                p = GetSetPixels.setB(p, (k - 100) % 255);
//		                p = GetSetPixels.setG(p, (k - 200) % 255);         
	                	
	                	//cool2
//		                p = GetSetPixels.setB(p, (k) % 255);
//		                p = GetSetPixels.setR(p, (k - 127) % 255);
//		                p = GetSetPixels.setG(p, (k - 255) % 255);  
		                
	                	///SUPER COOL GREEN?BLUE??
		                p = GetSetPixels.setG(p, (k) % 150);
		                p = GetSetPixels.setB(p, k % 250);//(k + 50) % 255);
		                p = GetSetPixels.setR(p, k % 5);//(k - 205) % 255); 
		                
	                	//blue
//		                p = GetSetPixels.setG(p, (k) % 255);
//		                p = GetSetPixels.setB(p, 255);
//		                p = GetSetPixels.setR(p, (k - 255) % 255); 
		                
	                	GetSetPixels.setARGB(image, counterW, counterH, p);
	                	
	                	if (lastBlack && counterW - 1 >= 0)
	                	{
	                		lastBlack = false;
	                		
	                		k = MandelbrotSet_parallel.Calculate(new Complex(w - cW, h), (quality));
	                		
		                	//cool 1
//			                p = GetSetPixels.setR(p, (k) % 255);
//			                p = GetSetPixels.setB(p, (k - 100) % 255);
//			                p = GetSetPixels.setG(p, (k - 200) % 255);         
		                	
		                	//cool2
//			                p = GetSetPixels.setB(p, (k) % 255);
//			                p = GetSetPixels.setR(p, (k - 127) % 255);
//			                p = GetSetPixels.setG(p, (k - 255) % 255);  
			                
		                	///SUPER COOL GREEN?BLUE??
			                p = GetSetPixels.setG(p, (k) % 150);
			                p = GetSetPixels.setB(p, k % 250);//(k + 50) % 255);
			                p = GetSetPixels.setR(p, k % 5);//(k - 205) % 255); 
			                
		                	//blue
//			                p = GetSetPixels.setG(p, (k) % 255);
//			                p = GetSetPixels.setB(p, 255);
//			                p = GetSetPixels.setR(p, (k - 255) % 255); 
			                
		                	GetSetPixels.setARGB(image, counterW - 1, counterH, p);
	                	}
	                	counterW++;
	                }
	            }
	            counterH++;
	            counterW = 0;
	            if (counterH %10 == 0)
	            		System.out.println("Thread " + ID + ": " + counterH + "/" + (ID + 1) * (image.getHeight()- 1) / (NUM_THREADS));
	        }
	   }
}