import java.io.File;
import java.io.IOException;

public class PngToMp4 {
	public static void main(String[] args) throws IOException {
	    SequenceMuxer encoder = new SequenceMuxer(new File("C:\\Users\\Kevin\\Desktop\\test\\video_png.mp4"));
	    for (int i = 1; i < 100; i++) {
	        encoder.encodeImage(new File(String.format("C:\\Users\\Kevin\\Desktop\\test\\zoom%d.png", i)));
	    }
	    encoder.finish();
	}
}
