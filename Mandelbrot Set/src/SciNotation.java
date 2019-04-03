
public class SciNotation {
	private int integer;
	private long number;
	private long exponent;
	
	public SciNotation(long number, long exponent){
		this.number = number;
		this.exponent = exponent;
	}
	
	public SciNotation(){
		this.number = 0;
		this.exponent = 0;
	}
	
	public SciNotation times(SciNotation num){
		return new SciNotation(num.number*this.number, num.exponent+this.exponent);
	}
	
	public String toString(){
		return number+"E"+exponent;
	}

}
