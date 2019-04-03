public class MandelbrotSet {
	
	    static double focusA = 0; //focus for A axis
	    static double focusB = 0; //      for B axis
	    static double radius = 2;
	    
	public static void main(String[] args) {
		char[] shades = {' ', '.', ',', 'c', '8', 'M', '@', 'j', 'a', 'w', 'r', 'p', 'o', 'g', 'O', 'Q', 'E', 'P', 'G', 'J'};
		//char[] shades = {' ', '░', '▒', '▓', '▏', '▎', '▍', '▌', '▋', '▊'}; //10
		
		double width =  1000;
		double height = 1000;

		for (double h = focusB + radius; h >= focusB - radius; h -= (2*radius)/height) {
            for (double w = focusA - radius; w <= focusA + radius; w += (2*radius)/width) {
                int k = MandelbrotSet.Calculate(new Complex(w, h), shades.length-1);
                System.out.print(shades[k]);
            }
			System.out.println("");
		}
		System.out.println("Done");
	}
	
	public static int Calculate(Complex z, int loopFor){
		Complex tmp = new Complex();
		for(int i = 1; i <= loopFor; i++){ //or we could just have initialized i as 1 in the first place, but shut up//We did actually end up doing that, but shut up
			tmp = Complex.add(Complex.multiply(tmp,tmp), z);
			if (tmp.dist() >= 2.0)
				return i;
		}
		return 0;
	}
}
