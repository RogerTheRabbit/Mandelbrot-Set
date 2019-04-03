import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class GUI extends Application{
	
//	static int LOADING = 0;
//	static Stage secondaryStage;
	boolean CAN_RUN = false;

	
	Stage window;
	JFXTextField[] ints = new JFXTextField[3];
	JFXTextField[] doubles = new JFXTextField[3];
	JFXTextField[] paths = new JFXTextField[1];
	
//	JFXTextField[] names = new JFXTextField[5];
	
	public static void main(String[] args){
		launch (args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Sexier than Raj");
		
		GridPane grid = new GridPane();
		//Padding around gridpane
		grid.setPadding(new Insets(15, 15, 15, 15));
		//Padding around individual cells in gridpane
		grid.setVgap(10);
		grid.setHgap(20);
		
		
		//SECTION 111111

		
		Label resolution = new Label("Resolution");
		resolution.setPadding(new Insets(15, 15, 15, 0));
		GridPane.setConstraints(resolution, 0,0);
		
		JFXTextField pixWidth = new JFXTextField("900");
		pixWidth.setLabelFloat(true);
		pixWidth.setPromptText("Pixel Width of Image");
		pixWidth.setStyle("-jfx-focus-color:rgb(77,102,204);"
				+ "-fx-padding: 0.7em 0.57em;");
		GridPane.setConstraints(pixWidth, 0, 1);
		ints[0] = pixWidth;
		
		JFXTextField pixHeight = new JFXTextField("900");
		pixHeight.setLabelFloat(true);
		pixHeight.setPromptText("Pixel Height of Image");
		pixHeight.setStyle("-jfx-focus-color:rgb(77,102,204);"
				+ "-fx-padding: 0.7em 0.57em;");
		GridPane.setConstraints(pixHeight, 1, 1);
		ints[1] = pixHeight;
		
		JFXTextField quality = new JFXTextField("15");
		quality.setLabelFloat(true);
		quality.setPromptText("Quality");
		quality.setStyle("-jfx-focus-color:rgb(77,102,204);"
				+ "-fx-padding: 0.7em 0.57em;");
		GridPane.setConstraints(quality, 2, 1);
		ints[2] = quality;
		
		pixWidth.focusedProperty().addListener((o,oldVal,newVal)->{
			if(!newVal){
				CAN_RUN = validateInt(pixWidth);
		    }
		});
		
		pixHeight.focusedProperty().addListener((o,oldVal,newVal)->{
			if(!newVal){
				CAN_RUN = validateInt(pixHeight);
		    }
		});
		
		quality.focusedProperty().addListener((o,oldVal,newVal)->{
			if(!newVal){
				CAN_RUN = validateInt(quality);
		    }
		});
		
		GridPane section1 = new GridPane();
		//Padding around gridpane
		section1.setPadding(new Insets(15, 15, 15, 15));
		section1.getChildren().addAll(resolution, pixWidth, pixHeight, quality);
		GridPane.setConstraints(section1, 0, 1);
		
		//SECTION 222222
		
		Label cordinates = new Label("Cordinates");
		cordinates.setPadding(new Insets(15, 15, 15, 0));
		GridPane.setConstraints(cordinates, 0,0);
		
		JFXTextField xCord = new JFXTextField("0");
		xCord.setLabelFloat(true);
		xCord.setPromptText("X cord of center");
		xCord.setStyle("-jfx-focus-color:rgb(77,102,204);"
				+ "-fx-padding: 0.7em 0.57em;");
		GridPane.setConstraints(xCord, 0, 1);
		doubles[0] = xCord;
		
		JFXTextField yCord = new JFXTextField("0");
		yCord.setLabelFloat(true);
		yCord.setPromptText("Y cord of center");
		yCord.setStyle("-jfx-focus-color:rgb(77,102,204);"
				+ "-fx-padding: 0.7em 0.57em;");
		GridPane.setConstraints(yCord, 1, 1);
		doubles[1] = yCord;
		
		JFXTextField radius = new JFXTextField("2");
		radius.setLabelFloat(true);
		radius.setPromptText("Radius");
		radius.setStyle("-jfx-focus-color:rgb(77,102,204);"
				+ "-fx-padding: 0.7em 0.57em;");
		GridPane.setConstraints(radius, 2, 1);
		doubles[2] = radius;
		
		xCord.focusedProperty().addListener((o,oldVal,newVal)->{
			if(!newVal){
				CAN_RUN = validateDouble(xCord);
		    }
		});
		
		yCord.focusedProperty().addListener((o,oldVal,newVal)->{
			if(!newVal){
				CAN_RUN = validateDouble(yCord);
		    }
		});
		
		radius.focusedProperty().addListener((o,oldVal,newVal)->{
			if(!newVal){
				CAN_RUN = validateDouble(radius);
		    }
		});
		
		
		GridPane section2 = new GridPane();
		//Padding around gridpane
		section2.setPadding(new Insets(15, 15, 15, 15));
		section2.getChildren().addAll(cordinates, xCord, yCord, radius);
		GridPane.setConstraints(section2, 0, 2);
		
		
		//SECTION 333333
		
		Label fileLocLbl = new Label("File output location");
		fileLocLbl.setPadding(new Insets(15, 15, 15, 0));
		GridPane.setConstraints(fileLocLbl, 0, 0);
		
		JFXTextField fileLoc = new JFXTextField("F:");
		fileLoc.setLabelFloat(true);
		fileLoc.setPromptText("File Location");
		fileLoc.setStyle("-jfx-focus-color:rgb(77,102,204);"
				+ "-fx-padding: 0.7em .57em;");
		fileLoc.setPrefWidth(300);
		GridPane.setConstraints(fileLoc, 0, 1);
		paths[0] = fileLoc;
		
		
		fileLoc.focusedProperty().addListener((o,oldVal,newVal)->{
			if(!newVal){
				CAN_RUN = validPath(fileLoc);
		    }
		});
		
		JFXTextField fileName = new JFXTextField("theBestName.png");
		fileName.setLabelFloat(true);
		fileName.setPromptText("File Name");
		fileName.setStyle("-jfx-focus-color:rgb(77,102,204);"
				+ "-fx-padding: 0.7em .57em;");
		fileName.setPrefWidth(148);
		GridPane.setConstraints(fileName, 1, 1);
		
		fileName.focusedProperty().addListener((o,oldVal,newVal)->{
			if(!newVal){
				CAN_RUN = validName(fileName);
		    }
		});
		
		
		GridPane section3 = new GridPane();
		//Padding around gridpane
		section3.setPadding(new Insets(15, 15, 15, 15));
		section3.getChildren().addAll(fileLocLbl, fileLoc, fileName);
		GridPane.setConstraints(section3, 0, 3);
		
		//SECTION 444444
		
		ToggleGroup group = new ToggleGroup();
        
		JFXRadioButton opParalax = new JFXRadioButton("Interactive Paralax");
		opParalax.setPadding(new Insets(5));
		opParalax.setToggleGroup(group);
		opParalax.setUserData("Interactive Parallax");
		opParalax.setSelected(true);
	
		JFXRadioButton opGenImg = new JFXRadioButton("GenerateImage");
		opGenImg.setPadding(new Insets(5));
		opGenImg.setToggleGroup(group);
		opGenImg.setUserData("GenerateImage");
		

		GridPane.setConstraints(opParalax, 0, 0);
		GridPane.setConstraints(opGenImg, 1, 0);
		
		
		GridPane section4 = new GridPane();
		//Padding around gridpane
		section4.setPadding(new Insets(15, 15, 15, 15));
		section4.getChildren().addAll(opParalax, opGenImg);
		GridPane.setConstraints(section4, 0, 4);
		
		
		//This is not used atm.
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    @Override
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

		         if (group.getSelectedToggle() != null) {

		             System.out.println(group.getSelectedToggle().getUserData().toString());
		             // Do something here with the userData of newly selected radioButton

		         }
		     }
		});
		
		JFXButton MParalax = new JFXButton("Generate Parallax Mandelbot".toUpperCase());
		
		MParalax.setStyle("-fx-padding: 0.7em 0.57em;"
				+ "-fx-font-size: 14px;"
				+ "-jfx-button-type: RAISED;"
				+ "-fx-background-color: rgb(77,102,204);"
				+ "-fx-pref-width: 300;"
				+ "-fx-text-fill: WHITE;");
		GridPane.setConstraints(MParalax, 0, 5);

		GridPane.setHalignment(MParalax, HPos.CENTER);
		
		Stage secondaryStage = new Stage();
		secondaryStage.initStyle(StageStyle.UNDECORATED);
		secondaryStage.setAlwaysOnTop(true);
		
		VBox layout2 = new VBox();
		layout2.setAlignment(Pos.CENTER);
		Scene scene2 = new Scene(layout2, 550, 100);
		
		
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
		
		
		
		layout2.getChildren().addAll(label, jfxBarInf);
		
		secondaryStage.setScene(scene2);
		
		
		
		
		
		MParalax.setOnAction(e -> {
			
			if(validateAll()) {
			
//				if (group.getSelectedToggle() != null) {
//	
//		             System.out.println(group.getSelectedToggle().getUserData().toString());
//		             // Do something here with the userData of newly selected radioButton
//	
//		         }
				
				if(group.getSelectedToggle().getUserData().toString().equals("Interactive Parallax")) {

					
					Runnable runnable = new Runnable_ProgressBar();
					Thread runnableThread = new Thread(runnable);
					
					runnableThread.start();

//					secondaryStage.show();
//					Thread t;
//					t = new Thread({
//						public void run(){
//							try {
//								MandelbrotSet3_Parallax_Parallelized_Harrison_Optimization.main(Double.parseDouble(xCord.getText()), Double.parseDouble(yCord.getText()), Double.parseDouble(radius.getText()), Integer.parseInt(quality.getText()), Integer.parseInt(pixWidth.getText()), Integer.parseInt(pixHeight.getText()), 5, null);
//								
////								closeSecondStage();
//							} catch (NumberFormatException | IOException | InterruptedException e1) {
//								e1.printStackTrace();
//							}							
//						}
//
//					});
					
					
					Thread t;
					t = new Parallax(Double.parseDouble(xCord.getText()), Double.parseDouble(yCord.getText()), Double.parseDouble(radius.getText()), Integer.parseInt(quality.getText()), Integer.parseInt(pixWidth.getText()), Integer.parseInt(pixHeight.getText()), 5);

					t.start();
					
					try {
						t.join();
//						secondaryStage.close();
					} catch (InterruptedException e1) {
						
						e1.printStackTrace();
					}
					
					
					
					
//					new Thread(() -> {
//						try {
//							MandelbrotSet3_Parallax_Parallelized_Harrison_Optimization.main(Double.parseDouble(xCord.getText()), Double.parseDouble(yCord.getText()), Double.parseDouble(radius.getText()), Integer.parseInt(quality.getText()), Integer.parseInt(pixWidth.getText()), Integer.parseInt(pixHeight.getText()), 5, null);
//						} catch (NumberFormatException | IOException | InterruptedException e1) {
//							e1.printStackTrace();
//						}
//					}).start();
				}
				
				if(group.getSelectedToggle().getUserData().toString().equals("GenerateImage")) {
					
					new Thread(() -> {
						String path = fileLoc.getText() + "\\" + fileName.getText(); //"F:\\Eclipse\\Workspaces\\Javer\\Mandelbrotset_GUI\\OutputImages\\TESTIMAGE.png";
						
						System.out.println(path);
						
						try {
							MandelbrotSet3_Parallelized_Harrison_Optimization.main(Double.parseDouble(xCord.getText()), Double.parseDouble(yCord.getText()), Double.parseDouble(radius.getText()), Integer.parseInt(quality.getText()), Integer.parseInt(pixWidth.getText()), Integer.parseInt(pixHeight.getText()), path, 15, null);
							
						} catch (NumberFormatException | IOException e1) {
							e1.printStackTrace();
						}
					}).start();
				}
			}
		});
		
		GridPane section5 = new GridPane();
		//Padding around gridpane
		section5.setPadding(new Insets(15, 15, 15, 15));
		section5.getChildren().addAll(MParalax);
		GridPane.setConstraints(section5, 0, 5);

		
		grid.getChildren().addAll(//resolution, pixWidth, pixHeight, quality,
								  section1,
//								  xCord, yCord, radius,
								  section2,
//								  fileLocLbl, fileLoc,
								  section3,
//								  opParalax, opGenImg,
								  section4,
				
//								  MParalax
								  section5);

		Scene scene = new Scene(grid, 500, 550);
		window.setScene(scene);
		window.show();
	}
	
	private boolean validateInt(TextField input){
		
		try{
			int cin = Integer.parseInt(input.getText());
			input.setStyle("-jfx-focus-color:rgb(77,102,204);"
					+ "-fx-padding: 0.7em 0.57em;");
			System.out.print("Int is: " + cin + "\n");
			return true;
		}catch(NumberFormatException e){
//			input.setPromptText("NOT AN IN");
			input.setStyle("-jfx-focus-color:#F44336;"
					+ "-fx-padding: 0.7em 0.57em;"
					+ "-jfx-unfocus-color:#F44336;");
			System.out.println("Error: " + input.getText() + " is not an int\n");
			return false;
		}
	}
	
	
	private boolean validateDouble(TextField input){

		try{
			double cin = Double.parseDouble(input.getText());
			input.setStyle("-jfx-focus-color:rgb(77,102,204);"
					+ "-fx-padding: 0.7em 0.57em;");
			System.out.print("Double is: " + cin + "\n");
			return true;
		}catch(NumberFormatException e){
			input.setStyle("-jfx-focus-color:#F44336;"
					+ "-fx-padding: 0.7em 0.57em;"
					+ "-jfx-unfocus-color:#F44336;");
			System.out.println("Error: " + input.getText() + " is not an int\n");
			return false;
		}
	}


	//https://stackoverflow.com/questions/10208052/string-equals-with-multiple-conditions-and-one-action-on-result?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
	private boolean validName(TextField input){
		boolean isOk = true;
		
		Set<String> specialChars = new HashSet<String>();
	    specialChars.add("!");
	    specialChars.add("@");
	    specialChars.add("#");
    
	    if (specialChars.contains(input.getText().substring(0,1))){
	      //Do stuff to make invalid.
	      isOk = false;
	    }
		
		for(int x = 0; x<input.getText().length(); x++){
		  //Check other characters
		}
		
		return isOk;
		
	}
	
	private boolean validPath(TextField input) {
    
	    String str = input.getText();
	    int counter = 0;
	    for(int x = str.length()-1; x>=0; x--){
			//if(str.substring(str.length()-1, str.length()).equals("/") || str.substring(str.length()-1, str.length()).equals("\\"))
			if(str.substring(x, x+1).equals("/") || str.substring(x, x+1).equals("\\"))
	    		counter++;
			else
				break;
	    }
	    
	    input.setText(str.substring(0, str.length()-counter));
		File file = new File(input.getText());
		if (file.isDirectory()) {
			System.out.println("K, THAT'S FINE");
			input.setStyle("-jfx-focus-color:rgb(77,102,204);"
					+ "-fx-padding: 0.7em 0.57em;");
		}
		else{
			System.out.println("NO, YOU'RE WRONG");
			input.setStyle("-jfx-focus-color:#F44336;"
					+ "-fx-padding: 0.7em 0.57em;"
					+ "-jfx-unfocus-color:#F44336;");
			}
		return file.isDirectory();
	}
	
	private boolean validateAll(){
		boolean cout = true;
		
		for(int x = 0; x<doubles.length; x++){
			if(!validateDouble(doubles[x]))
				cout = false;
		}
		
		for(int x = 0; x<ints.length; x++){
			if(!validateInt(ints[x]))
				cout = false;
		}
		
//		cout = validPath(paths[0]);
		
		System.out.println((cout ? "ALLS GOOD" : "NOT GOOD! NOT GOOD! ABORT ABORT"));
		return cout;
	}
	
	
//	public void closeSecondStage(){
//		GUI.secondaryStage.close();
//	}
	
	
}
