
public class Runnable_ProgressBar_Tester {

	public static void main(String[] args) {
		
		Runnable_ProgressBar bar = new Runnable_ProgressBar();
		
		bar.main(null);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bar.shutDown();

	}

}
