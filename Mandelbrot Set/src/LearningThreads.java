
public class LearningThreads implements Runnable{
	
	static int y;
	static int x;

	public LearningThreads (int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void run(){
		for (int i = x; i<y; i++){
			System.out.println(i);
			
		}
	}
	
	public static void main(String[] args) {
		Runnable test = new LearningThreads(100, 110);
		new Thread(test).start();
		Runnable test1 = new LearningThreads(0, 10);
		new Thread(test1).start();

	}

}
