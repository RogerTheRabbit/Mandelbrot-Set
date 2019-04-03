import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GUI_Testing extends Application {
	
	Stage window;
	
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
		
		//Name label
		Label nameLabel = new Label("Username: ");
//		nameLabel.setStyle("-fx-background-color:WHITE;-fx-padding:20");
		JFXRippler rippler = new JFXRippler(nameLabel);
		
		GridPane.setConstraints(rippler, 0, 0);
		
		//Name input
		
		JFXTextField nameInput = new JFXTextField("Pre-filled text");
		nameInput.setLabelFloat(true);
		nameInput.setPromptText("Username");
		nameInput.setStyle("-jfx-focus-color:#00BCD4;"
				+ "-fx-padding: 0.7em 0.57em;");
		GridPane.setConstraints(nameInput, 1, 0);
		
		//Password label
		Label passwordLabel = new Label("Password: ");
		GridPane.setConstraints(passwordLabel, 0,1);

		//Password input
		JFXTextField passwordInput = new JFXTextField();
		passwordInput.setLabelFloat(true);
		passwordInput.setPromptText("placeholder");
		passwordInput.setStyle("-jfx-focus-color:#00BCD4");
		passwordInput.setPromptText("Put your password here");
		passwordInput.setStyle("-fx-padding: 0.7em 0.57em;");
		GridPane.setConstraints(passwordInput, 1, 1);
		
		JFXButton jfoenixButton = new JFXButton("Flat button".toUpperCase());
		JFXButton loginButton = new JFXButton("Raised Button".toUpperCase());
		loginButton.setStyle("-fx-padding: 0.7em 0.57em;"
				+ "-fx-font-size: 14px;"
				+ "-jfx-button-type: RAISED;"
				+ "-fx-background-color: rgb(77,102,204);"
				+ "-fx-pref-width: 200;"
				+ "-fx-text-fill: WHITE;");
		jfoenixButton.setStyle("-fx-padding: 0.7em 0.57em;"
				+ "-fx-font-size: 14px;"
				+ "-fx-background-color: rgb(77,102,204);"
				+ "-fx-pref-width: 200;" 
				+ "-fx-text-fill: WHITE;");

		
		JFXTextField field = new JFXTextField();
		field.setLabelFloat(true);
		field.setPromptText("Floating prompt");
		 
		JFXTextField validationField = new JFXTextField();
		validationField.setPromptText("With Validation..");
		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setMessage("Input Required");
//		validator.setAwsomeIcon(new Icon(AwesomeIcon.WARNING,"2em",";","error"));
		validationField.getValidators().add(validator);
		//Updates when field is unfocused
		validationField.focusedProperty().addListener((o,oldVal,newVal)->{
		    if(!newVal) System.out.println("Field changed from " + oldVal + " to " + newVal);//validationField.validate();
		});


		GridPane.setConstraints(field, 3, 3);
		GridPane.setConstraints(validationField, 3, 4);
		
//		Button loginButton = new Button("Login");
		GridPane.setConstraints(jfoenixButton, 1, 3);
		GridPane.setConstraints(loginButton, 1, 2);

		loginButton.setOnAction( e -> {
			
			
			
			try {
				MandelbrotSet3_Parallax_Parallelized_Harrison_Optimization.main(0, 0, 2, 500, 500, 500, 6, null);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		});

		jfoenixButton.setOnAction( e -> {
			
			
			
			try {
				MandelbrotSet3_Parallax_Parallelized_Harrison_Optimization.main(0, 0, 2, 500, Integer.parseInt(nameInput.getText()), Integer.parseInt(passwordInput.getText()), 6, null);
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		grid.getChildren().addAll(validationField, field, jfoenixButton, rippler, nameInput, passwordInput, passwordLabel, loginButton);

		Scene scene = new Scene(grid, 400, 280);
		window.setScene(scene);
		window.show();
	}
}
