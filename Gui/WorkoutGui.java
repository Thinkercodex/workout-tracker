import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.geometry.*;	
import javafx.event.ActionEvent;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;

public class WorkoutGui extends Application{
	
	private Label textLabel;
	
	private Label textLabel2;
	
	private Label textLabelCurrentUser;
	
	private TextField newUsernameEnteredTextField;
	
	private TextField usernameTextField;
	
	private TextField selectWorkoutTextField;
	
	private TextField newWorkoutTextField;
	
	private TextField newWorkoutMaxRepNumTextField;
	
	private TextField maxRepNumTextField;

  	private Button logOutButton;
	
  	private Button returnButton;
	
  	private Button viewUserWorkoutsButton;
	
  	private Button newUserButton;
	
  	private Button logInButton;
	
  	private Button newWorkoutButton;
	
  	private Button workoutSelectionButton;
	
  	private Button workoutStartButton;
	
  	private Button createWorkoutButton;
	
  	private Button addRepButton;
	
  	private Button delRepButton;
	
  	private ListView<String> list;
	
	private GridPane mainPane;
	
	private int currentUserId;
	
	private String currentUsername;
	
	private int currentWorkoutId;
	
	private String currentWorkout;
	
	private Connection connector;
	
	public void start(Stage primaryStage){
		primaryStage.setTitle("Workout Tracker");
		
		//Initial State
		textLabel = new Label("Welcome to the Workout Tracker");
		textLabel2 = new Label("");
		
		logInButton = new Button("Log In");
		logInButton.setOnAction(this::eventHandler);
		
		newUserButton = new Button("New User");
		newUserButton.setOnAction(this::eventHandler);
		
    	mainPane = new GridPane();
		mainPane.setAlignment(Pos.CENTER);

		mainPane.add(textLabel, 0,0,1,1);
		mainPane.add(logInButton, 0,1,1,1);
		mainPane.add(newUserButton, 0,2,1,1);
		
		mainPane.setHgap(12);
		mainPane.setVgap(12);
		
		Scene mainScene = new Scene(mainPane, 620, 520);
		primaryStage.setScene(mainScene);
		primaryStage.show();
 	}
	
	public void eventHandler(ActionEvent event){	
		try{
			//New User State
			if(event.getSource() == newUserButton){
				textLabel.setText("Enter your new username:");
				
				newUsernameEnteredTextField = new TextField();
				newUsernameEnteredTextField.setPrefWidth(110);
				newUsernameEnteredTextField.setOnAction(this::eventHandler);
				
				returnButton = new Button("Return to the Main Screen");
				returnButton.setOnAction(this::eventHandler);
				
				mainPane.getChildren().remove(newUserButton);
				mainPane.getChildren().remove(logInButton);
				mainPane.getChildren().remove(usernameTextField);
				textLabel2.setText("");
				mainPane.getChildren().remove(list);
				
				mainPane.add(newUsernameEnteredTextField, 0,1,1,1);
				mainPane.add(returnButton, 0,2,1,1);
			}
			//Username Creation State
			else if(event.getSource() == newUsernameEnteredTextField){
				
				String usernameMessage = newUsernameEnteredTextField.getText();
				
				boolean invalidUsername = false;
				
				//Check if username already exists in database
				connector = WorkoutSQLProcedure.connectOpenSQL();
				
				ResultSet result = WorkoutSQLProcedure.showAllUsers(connector);
				
				while(result.next()){
					if(usernameMessage.equals(result.getString(1))){
						invalidUsername = true;
					}
				}
				
				connector.close();
				
				if(usernameMessage.length() > 20){
					newUsernameEnteredTextField.clear();
					
					textLabel.setText("Sorry, but the username that you have entered is over 20 characters.\nPlease enter a shorter username:");
				}
				else if(usernameMessage.length() == 0){
					newUsernameEnteredTextField.clear();
					
					textLabel.setText("Invalid username\nPlease enter a different username:");
				}
				else if(invalidUsername){
					newUsernameEnteredTextField.clear();
					
					textLabel.setText("Sorry, but the username that you have entered already exists.\nPlease enter a different username:");
				}
				else{
					textLabel.setText("The username " + usernameMessage + " has been added\nNow please press the button to see the workout selection screen");
					
					//Add username to database
					connector = WorkoutSQLProcedure.connectOpenSQL();
					
					WorkoutSQLProcedure.addWorker(usernameMessage, connector);
					
					connector.close();
					
					currentUsername = usernameMessage;
					
					workoutSelectionButton = new Button("Workout Selection");
					workoutSelectionButton.setOnAction(this::eventHandler);
					
					mainPane.getChildren().remove(newUsernameEnteredTextField);
					
					mainPane.add(workoutSelectionButton, 0,1,1,1);
				}
			}
			//Log In State
			else if(event.getSource() == logInButton){
				textLabel.setText("Please enter your username from the list of available users:");
				
				textLabel2.setText("Available Usernames:");
				
				list = new ListView<String>();
				list.setPrefHeight(200);
				list.setPrefWidth(400);
				
				//List available users in database
				connector = WorkoutSQLProcedure.connectOpenSQL();
				
				ResultSet result = WorkoutSQLProcedure.showAllUsers(connector);
				
				while(result.next()){
					list.getItems().add(result.getString(1));
				}
				
				connector.close();
				
				usernameTextField = new TextField();
				usernameTextField.setPrefWidth(110);
				usernameTextField.setOnAction(this::eventHandler);
				
				mainPane.getChildren().remove(newUserButton);
				mainPane.getChildren().remove(logInButton);
				
				mainPane.add(usernameTextField, 0,1,1,1);
				mainPane.add(textLabel2, 0,4,1,1);
				mainPane.add(list, 0,5,1,1);
			}
			//Log In Username Verification State
			else if(event.getSource() == usernameTextField){
				mainPane.getChildren().remove(newUserButton);
				
				String usernameMessage = usernameTextField.getText();
				
				boolean invalidUsername = true;
				
				//Check if username exists in database
				connector = WorkoutSQLProcedure.connectOpenSQL();
				
				ResultSet result = WorkoutSQLProcedure.showAllUsers(connector);
				
				while(result.next() && invalidUsername){
					if(usernameMessage.equals(result.getString(1))){
						invalidUsername = false;
					}
				}
				
				connector.close();
				
				if(invalidUsername){
					usernameTextField.clear();
					
					textLabel.setText("Sorry, but the username that you have entered does not exists.\nPlease enter a valid username or create a new user:");
					
					newUserButton = new Button("New User");
					newUserButton.setOnAction(this::eventHandler);
					
					mainPane.add(newUserButton, 0,2,1,1);					
				}
				else{
					textLabel.setText("The user " + usernameMessage + " has been selected.\nNow please press the button to see the workout selection screen");
					
					currentUsername = usernameMessage;
					
					workoutSelectionButton = new Button("Workout Selection");
					workoutSelectionButton.setOnAction(this::eventHandler);
					
					mainPane.getChildren().remove(usernameTextField);
					textLabel2.setText("");
					mainPane.getChildren().remove(list);
					
					mainPane.add(workoutSelectionButton, 0,1,1,1);
				}
			}
			//Log Out State
			else if(event.getSource() == logOutButton || event.getSource() == returnButton){
				mainPane.getChildren().clear();
				
				textLabel.setText("Welcome to the Workout Tracker");
				textLabel2.setText("");
				
				mainPane.add(textLabel, 0,0,1,1);
				mainPane.add(logInButton, 0,1,1,1);
				mainPane.add(newUserButton, 0,2,1,1);
			}
			//View User Workouts State
			else if(event.getSource() == viewUserWorkoutsButton){
				mainPane.getChildren().clear();
				
				textLabelCurrentUser = new Label("Currently Signed In as: " + currentUsername);
				
				textLabel.setText("List of previous workouts:");
				
				//Show all user workouts
				list.getItems().clear();
				
				//List available users in database
				connector = WorkoutSQLProcedure.connectOpenSQL();
				
				ResultSet result = WorkoutSQLProcedure.showUserWorkouts(currentUserId, connector);
				//Set currentUserId
				while(result.next()){
					list.getItems().add(result.getString(1) + ":    " + result.getString(3) + " Repetitions,    Date: " + result.getString(2));
				}
				
				connector.close();
				
				mainPane.add(textLabel, 0,0,1,1);
				mainPane.add(list, 1,0,1,1);
				mainPane.add(textLabelCurrentUser, 0,9,1,1);
				mainPane.add(workoutSelectionButton, 0,10,1,1);
				mainPane.add(logOutButton, 0,11,1,1);
			}
			//Workout Selection State
			else if(event.getSource() == workoutSelectionButton){
				mainPane.getChildren().remove(list);
				mainPane.getChildren().remove(logOutButton);
				mainPane.getChildren().remove(viewUserWorkoutsButton);
				mainPane.getChildren().remove(textLabelCurrentUser);
				
				//Get user ID from current unique username
				connector = WorkoutSQLProcedure.connectOpenSQL();
				
				ResultSet userIdResult = WorkoutSQLProcedure.showUserID(currentUsername, connector);
				
				userIdResult.next();
				
				currentUserId = userIdResult.getInt(1);
				
				connector.close();
				
				textLabelCurrentUser = new Label("Currently Signed In as: " + currentUsername);
				
				logOutButton = new Button("Log Out");
				logOutButton.setOnAction(this::eventHandler);
				
				viewUserWorkoutsButton = new Button("View My Workouts");
				viewUserWorkoutsButton.setOnAction(this::eventHandler);
				
				textLabel.setText("Enter a workout from the list or press\nthe create a new workout button to enter a new workout");		
				
				//List all workouts
				list.getItems().clear();
				
				//List available users in database
				connector = WorkoutSQLProcedure.connectOpenSQL();
				
				ResultSet result = WorkoutSQLProcedure.showWorkoutNames(connector);
				
				while(result.next()){
					list.getItems().add(result.getString(1));
				}
				
				connector.close();
				
				selectWorkoutTextField = new TextField();
				selectWorkoutTextField.setPrefWidth(110);
				selectWorkoutTextField.setOnAction(this::eventHandler);
				
				newWorkoutButton = new Button("Create a New Workout");
				newWorkoutButton.setOnAction(this::eventHandler);
				
				mainPane.getChildren().remove(workoutSelectionButton);
				
				mainPane.add(selectWorkoutTextField, 0,1,1,1);
				mainPane.add(list, 0,2,1,1);
				mainPane.add(newWorkoutButton, 0,4,1,1);
				mainPane.add(textLabelCurrentUser, 0,5,1,1);
				mainPane.add(viewUserWorkoutsButton, 0,6,1,1);
				mainPane.add(logOutButton, 0,7,1,1);
			}
			//New Workout Creation State
			else if(event.getSource() == newWorkoutButton){
				mainPane.getChildren().remove(list);
				mainPane.getChildren().remove(textLabel2);
				
				textLabel.setText("Enter the name of the new workout:");
				textLabel2.setText("Enter the maximum number of repetitions for this workout:");
				
				newWorkoutTextField = new TextField();
				newWorkoutTextField.setPrefWidth(110);
				
				newWorkoutMaxRepNumTextField = new TextField();
				newWorkoutMaxRepNumTextField.setPrefWidth(110);
				
				createWorkoutButton = new Button("Create Workout");
				createWorkoutButton.setOnAction(this::eventHandler);
				
				mainPane.getChildren().remove(selectWorkoutTextField);
				mainPane.getChildren().remove(newWorkoutButton);
				
				mainPane.add(newWorkoutTextField, 0,1,1,1);
				mainPane.add(textLabel2, 0,2,1,1);
				mainPane.add(newWorkoutMaxRepNumTextField, 0,3,1,1);
				mainPane.add(createWorkoutButton, 0,4,1,1);
			}
			//New Workout Creation Verification State
			else if(event.getSource() == createWorkoutButton){
				int workoutMaxRepNumMessage = 10;
				
				boolean invalidRepNum = false;
				
				try{
					workoutMaxRepNumMessage = Integer.parseInt(newWorkoutMaxRepNumTextField.getText());
					textLabel2.setText("");
				}
				catch(NumberFormatException e){
					invalidRepNum = true;
				}
				
				String workoutNameMessage = newWorkoutTextField.getText();
				
				boolean invalidWorkoutName = false;
				
				//Check if workout name already exists in database
				connector = WorkoutSQLProcedure.connectOpenSQL();
				
				ResultSet result = WorkoutSQLProcedure.showWorkoutNames(connector);
				
				while(result.next()){
					if(workoutNameMessage.equals(result.getString(1))){
						invalidWorkoutName = true;
					}
				}
				
				connector.close();
				
				if(invalidRepNum){
					newWorkoutMaxRepNumTextField.clear();
					
					textLabel2.setText("Invalid number of repetitions value.\nPlease enter a valid integer:");
				}
				else if(workoutNameMessage.length() > 40){
					newWorkoutTextField.clear();
					
					textLabel.setText("Sorry, but the workout name that you have entered is over 40 characters.\nPlease enter a shorter workout name:");
				}
				else if(workoutNameMessage.length() == 0){
					newWorkoutTextField.clear();
					
					textLabel.setText("Invalid workout name.\nPlease enter a different workout name:");
				}
				else if(invalidWorkoutName){
					newWorkoutTextField.clear();
					
					textLabel.setText("Sorry, but the workout name that you have entered already exists.\nPlease enter a different username:");
				}
				else{
					textLabel.setText("Your workout " + workoutNameMessage + " has been added\nNow please press the button to start the workout");
					
					//Add workout name and workout to database
					connector = WorkoutSQLProcedure.connectOpenSQL();
					
					WorkoutSQLProcedure.addWorkoutName(workoutNameMessage, connector);
					
					ResultSet resultWorkoutID = WorkoutSQLProcedure.showWorkoutID(workoutNameMessage, connector);
					
					resultWorkoutID.next();
					
					currentWorkoutId = resultWorkoutID.getInt(1);
					
					WorkoutSQLProcedure.addWorkout(currentUserId, currentWorkoutId, workoutMaxRepNumMessage, connector);
					
					connector.close();
					
					currentWorkout = workoutNameMessage;
					
					workoutStartButton = new Button("Start Workout");
					workoutStartButton.setOnAction(this::eventHandler);
					
					mainPane.getChildren().remove(createWorkoutButton);
					mainPane.getChildren().remove(newWorkoutTextField);
					mainPane.getChildren().remove(newWorkoutMaxRepNumTextField);
					
					mainPane.add(workoutStartButton, 0,1,1,1);
				}
			}
			//Workout Selection Verification State
			else if(event.getSource() == selectWorkoutTextField){
				mainPane.getChildren().remove(newWorkoutButton);
				
				String workoutNameMessage = selectWorkoutTextField.getText();
				
				boolean invalidWorkoutName = true;
				
				//Check if workout name exists in database
				connector = WorkoutSQLProcedure.connectOpenSQL();
				
				ResultSet result = WorkoutSQLProcedure.showWorkoutNames(connector);
				
				while(result.next() && invalidWorkoutName){
					if(workoutNameMessage.equals(result.getString(1))){
						invalidWorkoutName = false;
					}
				}
				
				connector.close();
				
				if(invalidWorkoutName){
					selectWorkoutTextField.clear();
					
					textLabel.setText("Sorry, but the workout name that you have entered does not exists.\nPlease enter a valid workout name or create a new workout:");
					
					newWorkoutButton = new Button("Create a New Workout");
					newWorkoutButton.setOnAction(this::eventHandler);
					
					mainPane.add(newWorkoutButton, 0,3,1,1);
				}
				else{
					textLabel.setText("The workout " + workoutNameMessage + " has been selected.\nNow please enter the maximum number of repetitions:");
					
					currentWorkout = workoutNameMessage;
					
					maxRepNumTextField = new TextField();
					maxRepNumTextField.setOnAction(this::eventHandler);
					maxRepNumTextField.setPrefWidth(110);
					
					mainPane.getChildren().remove(list);
					mainPane.getChildren().remove(selectWorkoutTextField);
					mainPane.getChildren().remove(newWorkoutButton);
					
					mainPane.add(maxRepNumTextField, 0,1,1,1);
				}
			}
			//Entered Max Repetition State
			else if(event.getSource() == maxRepNumTextField){
				int workoutMaxRepNumMessage = 10;
				
				boolean invalidRepNum = false;
				
				try{
					workoutMaxRepNumMessage = Integer.parseInt(maxRepNumTextField.getText());
					textLabel2.setText("");
				}
				catch(NumberFormatException e){
					invalidRepNum = true;
				}
				
				if(invalidRepNum){
					maxRepNumTextField.clear();
					textLabel.setText("Invalid number of repetitions value.\nPlease enter a valid integer:");
				}
				else{
					textLabel.setText("Now please press start workout button to start the workout:");
					
					connector = WorkoutSQLProcedure.connectOpenSQL();
					
					ResultSet resultWorkoutID = WorkoutSQLProcedure.showWorkoutID(currentWorkout, connector);
					
					resultWorkoutID.next();
					
					currentWorkoutId = resultWorkoutID.getInt(1);
					
					WorkoutSQLProcedure.addWorkout(currentUserId, currentWorkoutId, workoutMaxRepNumMessage, connector);
					
					connector.close();
					
					mainPane.getChildren().remove(maxRepNumTextField);
					
					workoutStartButton = new Button("Start Workout");
					workoutStartButton.setOnAction(this::eventHandler);
					
					mainPane.add(workoutStartButton, 0,1,1,1);
				}
			}
			//Workout State
			else if(event.getSource() == workoutStartButton){
				//Show workout reps
				connector = WorkoutSQLProcedure.connectOpenSQL();
				
				ResultSet resultWorkoutID = WorkoutSQLProcedure.showWorkoutReps(currentWorkoutId, connector);
				
				resultWorkoutID.next();
				
				int numReps = resultWorkoutID.getInt(1);
				int numRemainingReps = resultWorkoutID.getInt(2);
				int totalReps = numReps + numRemainingReps;
				
				connector.close();
				
				textLabel.setText("Current Workout: " + currentWorkout + "\nNumber of Repetitions: " + numReps + " Repetitions\nRemaining Number of Repetitions: " + numRemainingReps + "/" + totalReps + " Repetitions\n");
				
				addRepButton = new Button("Add a Repetition");
				addRepButton.setOnAction(this::eventHandler);
				
				delRepButton = new Button("Remove a Repetition");
				delRepButton.setOnAction(this::eventHandler);
				
				mainPane.getChildren().remove(workoutStartButton);
				
				mainPane.add(addRepButton, 0,1,1,1);
				mainPane.add(delRepButton, 0,2,1,1);
			}
			else if(event.getSource() == addRepButton){
				//Add rep to workout
				connector = WorkoutSQLProcedure.connectOpenSQL();
				
				WorkoutSQLProcedure.addRep(currentWorkoutId, connector);

				connector.close();
				
				//Show workout reps
				connector = WorkoutSQLProcedure.connectOpenSQL();
				
				ResultSet resultWorkoutID = WorkoutSQLProcedure.showWorkoutReps(currentWorkoutId, connector);
				
				resultWorkoutID.next();
				
				int numReps = resultWorkoutID.getInt(1);
				int numRemainingReps = resultWorkoutID.getInt(2);
				int totalReps = numReps + numRemainingReps;
				
				connector.close();
				
				textLabel.setText("Current Workout: " + currentWorkout + "\nNumber of Repetitions: " + numReps + " Repetitions\nRemaining Number of Repetitions: " + numRemainingReps + "/" + totalReps + " Repetitions\n");
			}
			else if(event.getSource() == delRepButton){
				//Add rep to workout
				connector = WorkoutSQLProcedure.connectOpenSQL();
				
				WorkoutSQLProcedure.delRep(currentWorkoutId, connector);
				
				connector.close();
				
				//Show workout reps
				connector = WorkoutSQLProcedure.connectOpenSQL();
				
				ResultSet resultWorkoutID = WorkoutSQLProcedure.showWorkoutReps(currentWorkoutId, connector);
				
				resultWorkoutID.next();
				
				int numReps = resultWorkoutID.getInt(1);
				int numRemainingReps = resultWorkoutID.getInt(2);
				int totalReps = numReps + numRemainingReps;
				
				connector.close();
				
				textLabel.setText("Current Workout: " + currentWorkout + "\nNumber of Repetitions: " + numReps + " Repetitions\nRemaining Number of Repetitions: " + numRemainingReps + "/" + totalReps + " Repetitions\n");
			}
		}
		catch(SQLException e){
			textLabel.setText("Database error: " + e.getMessage());
		}
		
	}

}
