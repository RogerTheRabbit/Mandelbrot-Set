import com.jfoenix.controls.JFXProgressBar;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MandelbrotSet3_Parallax_ProgressBar extends Application {

	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage secondaryStage) throws Exception {
		
		secondaryStage.initStyle(StageStyle.UNDECORATED);
//		StackPane layout = new StackPane();
		
		
		VBox layout = new VBox();
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout, 550, 100);
		
		
		Label label = new Label("Working on it");
		label.setPadding(new Insets(0, 0, 20, 0));
		
		//Progress Bar
//		JFXProgressBar jfxBar = new JFXProgressBar();
//		jfxBar.setPrefWidth(500);
		
		JFXProgressBar jfxBarInf = new JFXProgressBar();
		jfxBarInf.setPrefWidth(500);
		jfxBarInf.setProgress(-1.0f);
		Timeline timeline = new Timeline(
		    new KeyFrame(Duration.ZERO, new KeyValue(jfxBarInf.progressProperty(), 0), new KeyValue(jfxBarInf.progressProperty(), 0)),
		    new KeyFrame(Duration.seconds(2), new KeyValue(jfxBarInf.progressProperty(), 5), new KeyValue(jfxBarInf.progressProperty(), 1)));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		
		
		
		layout.getChildren().addAll(label, jfxBarInf);
		
		secondaryStage.setScene(scene);
		secondaryStage.show();
	}
}
