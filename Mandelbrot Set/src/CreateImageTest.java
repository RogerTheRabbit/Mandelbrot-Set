import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class CreateImageTest {
	public static void main(String args[])throws IOException{
		
		int width = 30000;
		int height = width;
		
		int a = 255;
		int r = 0;
		int g = 0;
		int b = 255;
		int p = 0;
		
		BufferedImage coutImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		File f = null;
		
		
		
		for(int w = 0; w<width; w++){
			for(int h =  0; h<height;h++){
				a=w;
				b=h;
				p = (a<<24) | (r<<16) | (g<<8) | b;
				//System.out.println(p);
				coutImg.setRGB(w, h, p);
			}
		}
		try{
			f = new File("F:\\Eclipse\\Workspaces\\Javer\\Calc Stuffs\\src\\OutputImages\\Output.jpg");
			ImageIO.write(coutImg, "jpg", f);
		}catch (IOException e){
			System.out.println("Error: " + e);
		}
		System.out.println("Done");
	}
}
