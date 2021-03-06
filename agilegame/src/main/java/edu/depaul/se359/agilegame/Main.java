package edu.depaul.se359.agilegame;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.util.Random;

public class Main extends Application {

    final int xDimension = 8; // creating a 6x6 maps
    final int yDimension = 4;
    final int scale = 100; // Scale everything by 90. You can experiment here.
    final int playerScale = 50;
    final int diceScale = 75;
    Pane root;
    Scene scene;
    GameBoard gameBoard = GameBoard.getInstance(xDimension, yDimension);
    int[][] gameMap;
    Image pOneImage, pTwoImage;
    ImageView pOneImageView, pTwoImageView;
    
    Image diceImage;
    ImageView diceImageView;

    Image startImage, endImage, shortcutImageOne, shortcutImageTwo;
    
    Player playerOne, playerTwo;
    int rollValue;
    Button diceRoll;
    Label diceLabel, playerOneScoreLabel, playerTwoScoreLabel, playerOneAnswerLabel, playerTwoAnswerLabel, playerOneLabelResponse, playerTwoLabelResponse, playerOneLivesLabel, playerTwoLivesLabel, shortcutLabel;
    String[] team1questions;
    String[] team2questions;
    //temporary buttons and variables
    Button tmpT1Button, team1AnswerAButton, team1AnswerBButton, team1AnswerCButton;
    Button tmpT2Button, team2AnswerAButton, team2AnswerBButton, team2AnswerCButton;
    Button expPack, sourceCodeManagementGit, buildAutomation;
    Boolean questionDisplay = false;
    int t1count = 0;
    int t2count = 0;
    //display question stack pane
    StackPane stack;

    int playerOneScore = 0;
    int playerTwoScore = 0;
    
    int playerOneLives = 3;
    int playerTwoLives = 3;
    
    String a = "A";
    String b = "B";
    String c = "C";

    //end game notification
    Alert end;


    @Override
    public void start(Stage primaryStage) throws Exception{
        root = new AnchorPane();
        scene = new Scene(root, 1000, 450);
        //Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
        primaryStage.setTitle("Agile Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        gameMap = gameBoard.getMap();
        drawMap();
        gameBoard.displayMap();
        
        shortcutLabel = new Label ("");
        shortcutLabel.setTranslateX(325);
        shortcutLabel.setTranslateY(415);
        root.getChildren().add(shortcutLabel);

        // adds a button to dice roll
        diceRoll = new Button("Roll");
        diceRoll.setTranslateX(850);
        diceRoll.setTranslateY(50);
        root.getChildren().add(diceRoll);

        // adds a text box for dice
        diceLabel = new Label("Roll: ");
        diceLabel.setTranslateX(850);
        diceLabel.setTranslateY(100);
        root.getChildren().add(diceLabel);

        playerOneScoreLabel = new Label("Player One Score: " + playerOneScore);
        playerOneScoreLabel.setTranslateX(10);
        playerOneScoreLabel.setTranslateY(415);
        root.getChildren().add(playerOneScoreLabel);

        playerTwoScoreLabel = new Label("Player Two Score: " + playerTwoScore);
        playerTwoScoreLabel.setTranslateX(500);
        playerTwoScoreLabel.setTranslateY(415);
        root.getChildren().add(playerTwoScoreLabel);

        playerOneLivesLabel = new Label("Player One Lives: " + playerOneLives);
        playerOneLivesLabel.setTranslateX(150);
        playerOneLivesLabel.setTranslateY(415);
        root.getChildren().add(playerOneLivesLabel);

        playerTwoLivesLabel = new Label("Player Two Lives: " + playerTwoLives);
        playerTwoLivesLabel.setTranslateX(640);
        playerTwoLivesLabel.setTranslateY(415);
        root.getChildren().add(playerTwoLivesLabel);
        
        // adds a button for Expansion Pack
        expPack = new Button("Expansion");
        expPack.setTranslateX(850);
        expPack.setTranslateY(375);
        root.getChildren().add(expPack);

        // adds buttons for "Source Code Management & Git" and "Build Automation"
        sourceCodeManagementGit =  new Button("SCManagement&Git");
        sourceCodeManagementGit.setTranslateX(800);
        sourceCodeManagementGit.setTranslateY(415);
        root.getChildren().add(sourceCodeManagementGit);

        buildAutomation = new Button("BA");
        buildAutomation.setTranslateX(950);
        buildAutomation.setTranslateY(415);
        root.getChildren().add(buildAutomation);


        // initialize arrays with questions
        team1questions = new String[] {"Which ceremony do we practice first day of iteration?\n" +
                                        "\nA) Spring planning\n" +
                                        "B) Retrospective\n" +
                                        "C) Daily scum\n",

                                        "Knock knock.\n" +
                                                "Who’s there?\n" +
                                                "Processes and tools.\n" +
                                                "Processes and tools who?\n" +
                                                "\nA) Scrum master\n" +
                                                "B) Agile methodology\n" +
                                                "C) Developer\n",

                                        "Time to show the team what you did in 2 weeks,\n" +
                                                "if you did anything?\n" +
                                                "\nA) Backlog grooming\n" +
                                                "B) Sprint demo\n" +
                                                "C) Show case\n",

                                        "Developer: Uggh I need coffee for this meeting\n" +
                                                "Scrum Master: Dont complain, its only 15 minutes\n" +
                                                "\nWhich ceremony are they going to practice?\n" +
                                                "\nA) Coffee run to Dunkin' Donuts(Scrum runs on Dunkin')\n" +
                                                "B) Daily scum\n" +
                                                "C) Backlog grooming\n",

                                        "PO: I have so much work in my hands for developers\n" +
                                                "Developer: Well explain to me first what I have to do.\n" +
                                                "\nWhich ceremony will they have to practice\n" +
                                                "to get this work done?\n" +
                                                "\nA) Spring planning\n" +
                                                "B) Spring demo\n" +
                                                "C) Backlog grooming\n"};

        team2questions = new String[] {"PO: Estimates in 3, 2, 1...\n" +
                                            "Developer 1: 5\n" +
                                            "Developer 2: 3\n" +
                                            "Developer 3: 5\n" +
                                            "\nIn which ceremony does estimation happen?\n" +
                                            "\nA) Spring planning\n" +
                                            "B) Backlog grooming\n" +
                                            "C) Daily scum\n",

                                        "PO: Now that we've committed to these stories,\n" +
                                                "is your choice to pick\n" +
                                                "Developer 1: I want story ...\n" +
                                                "Developer 2: Ok ill take story number ...\n" +
                                                "\nIn which ceremony does stories get assigned?\n" +
                                                "\nA) Spring planning\n" +
                                                "B) Sprint demo\n" +
                                                "C) Retrospective\n",

                                        "What have you completed since the last meeting?\n" +
                                                "What do you plan to complete by the next meeting?\n" +
                                                "What is getting in your way?\n" +
                                                "\nThese questions are part of?\n" +
                                                "\nA) Daily Scrum\n" +
                                                "B) Sharing you life story\n" +
                                                "C) Meeting with manager\n",

                                        "Squads sharing to other squads\n" +
                                                "What the squad completed since the last meeting?\n" +
                                                "What the squad plans to complete by the next meeting?\n" +
                                                "What is getting in the squads way?\n" +
                                            "\nWhich ceremony are they going to practice?\n" +
                                            "\nA) Coffee run to Starbucks\n" +
                                            "B) Bigger daily scum\n" +
                                            "C) Scrum of scrums\n",

                                        "What is the typical length of sprint in a project?\n" +
                                            "\nA) 6 months\n" +
                                            "B) From start to finish of project\n" +
                                            "C) 2 weeks\n"};

        // temporary testing for printing questions to console
        for(String x : team1questions){
            System.out.println("Team 1 Question: " + x);
        }
        for(String y : team2questions){
            System.out.println("Team 2 Question: " + y);
        }

        // adds temporary button to display questions for player 1
        tmpT1Button = new Button("Team 1 Button");
        tmpT1Button.setTranslateX(810);
        tmpT1Button.setTranslateY(140);
        root.getChildren().add(tmpT1Button);

        // three buttons for player 1 to answer A, B or C
        team1AnswerAButton = new Button("A");
        team1AnswerAButton.setTranslateX(810);
        team1AnswerAButton.setTranslateY(175);
        root.getChildren().add(team1AnswerAButton);

        team1AnswerBButton = new Button("B");
        team1AnswerBButton.setTranslateX(850);
        team1AnswerBButton.setTranslateY(175);
        root.getChildren().add(team1AnswerBButton);

        team1AnswerCButton = new Button("C");
        team1AnswerCButton.setTranslateX(890);
        team1AnswerCButton.setTranslateY(175);
        root.getChildren().add(team1AnswerCButton);


        // adds temporary button to display questions for player 2
        tmpT2Button = new Button("Team 2 Button");
        tmpT2Button.setTranslateX(810);
        tmpT2Button.setTranslateY(250);
        root.getChildren().add(tmpT2Button);

        // three buttons for player 2 to answer A, B or C
        team2AnswerAButton = new Button("A");
        team2AnswerAButton.setTranslateX(810);
        team2AnswerAButton.setTranslateY(285);
        root.getChildren().add(team2AnswerAButton);

        team2AnswerBButton = new Button("B");
        team2AnswerBButton.setTranslateX(850);
        team2AnswerBButton.setTranslateY(285);
        root.getChildren().add(team2AnswerBButton);

        team2AnswerCButton = new Button("C");
        team2AnswerCButton.setTranslateX(890);
        team2AnswerCButton.setTranslateY(285);
        root.getChildren().add(team2AnswerCButton);

        // adds player one answer label
        playerOneAnswerLabel = new Label("Answer: " );
        playerOneAnswerLabel.setTranslateX(930);
        playerOneAnswerLabel.setTranslateY(185);
        root.getChildren().add(playerOneAnswerLabel);

        // adds player one correct or wrong label
        playerOneLabelResponse = new Label("You are: " );
        playerOneLabelResponse.setTranslateX(810);
        playerOneLabelResponse.setTranslateY(215);
        root.getChildren().add(playerOneLabelResponse);

        // adds player two answer label
        playerTwoAnswerLabel = new Label("Answer: " );
        playerTwoAnswerLabel.setTranslateX(930);
        playerTwoAnswerLabel.setTranslateY(295);
        root.getChildren().add(playerTwoAnswerLabel);

        // adds player two correct or wrong label
        playerTwoLabelResponse = new Label("You are: " );
        playerTwoLabelResponse.setTranslateX(810);
        playerTwoLabelResponse.setTranslateY(325);
        root.getChildren().add(playerTwoLabelResponse);


        // places the players images
        placePlayerOne();
        placePlayerTwo();

        // loads images
        loadPlayerOneImage();
        loadPlayerTwoImage();

        // set current images (ImageView) to the Pane
        root.getChildren().add(pOneImageView);
        root.getChildren().add(pTwoImageView);
        
        //rolls dice if the button is pushed
        diceRoll.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                rollDice();
            }
        });

        //handler for t1button
        tmpT1Button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                playerOneAnswerLabel.setText("Answer: ");
                displayQuestion(team1questions, t1count, "player1");
                System.out.println("t1count = " + t1count);
                System.out.println("t2count = " + t2count);
                System.out.println(isQuestionBeingDisplayed());

            }
        });

        team1AnswerButtons();

        //handler for t2button
        tmpT2Button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                playerTwoAnswerLabel.setText("Answer: ");
                displayQuestion(team2questions, t2count, "player2");
                System.out.println("t2count = " + t2count);
                System.out.println("t1count = " + t1count);
                System.out.println(isQuestionBeingDisplayed());
            }
        });

        team2AnswerButtons();
    }
    
    public void reduceLivesPlayerOne() {
    	System.out.println("\nX:" + playerOne.xCell + "Y:" + playerOne.yCell);
        System.out.println("X:" + pOneImageView.getX() + "Y:" + pOneImageView.getY());
        
    	if(playerOneLives == 1) {
    		playerOne.xCell = 0;
    		playerOne.yCell = 0;
            pOneImageView.setX(25);
            pOneImageView.setY(0);
    		playerOneLives = 3;
    		playerOneLivesLabel.setText("Player One Lives: " + playerOneLives);
    		
    	}else {
    		playerOneLives--;
    		playerOneLivesLabel.setText("Player One Lives: " + playerOneLives);
    	}
    	
    	System.out.println("\nX:" + playerOne.xCell + "Y:" + playerOne.yCell);
        System.out.println("X:" + pOneImageView.getX() + "Y:" + pOneImageView.getY());
    }
    
    public void reduceLivesPlayerTwo() {
    	if(playerTwoLives == 1) {
    		playerTwo.xCell = 0;
    		playerTwo.yCell = 0;
            pTwoImageView.setX(25);
            pTwoImageView.setY(50);
    		playerTwoLives= 3;
    		playerTwoLivesLabel.setText("Player One Lives: " + playerTwoLives);
    		
    	}else {
    		playerTwoLives--;
    		playerTwoLivesLabel.setText("Player One Lives: " + playerTwoLives);

    	}
    }


    public void team1AnswerButtons(){

        team1AnswerAButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	 playerOne.xCell = rollValue + playerOne.xCell;
                 pOneImageView.setX(playerOne.xCell*100+25);
                 if(playerOne.xCell > 7) {
                 	playerOne.xCell = playerOne.xCell - 8;
                 	playerOne.yCell++;
                 	pOneImageView.setX(playerOne.xCell*100+25);
                 	pOneImageView.setY(playerOne.yCell*100);
                 }
                 
                 if(playerOne.xCell == 1 && playerOne.yCell == 1) {
                 	playerOne.yCell = 2;
                 	pOneImageView.setY(playerOne.yCell*100);
                	shortcutLabel.setText("You landed on a shortcut!");
                 }else if(playerOne.xCell == 5 && playerOne.yCell ==2) {
                 	playerOne.yCell = 3;
                 	pOneImageView.setY(playerOne.yCell*100);
                	shortcutLabel.setText("You landed on a shortcut!");
                 } else {
                 	shortcutLabel.setText("");
                 }
                 
                if(t1count == 1 && isQuestionBeingDisplayed() == true){
                    playerOneScore++;
                    playerOneScoreLabel.setText("Player One: " + playerOneScore);
                    playerOneAnswerLabel.setText("Answer: " + getCorrectAnswer("player1"));
                    playerOneLabelResponse.setText("You are: " + "CORRECT!");
                    dismissQuestion();
                    
                }
                else{
                    reduceLivesPlayerOne();
                    playerOneAnswerLabel.setText("Answer: " + getCorrectAnswer("player1"));
                    playerOneLabelResponse.setText("You are: " + "WRONG!");
                    dismissQuestion();
                }
                //if exceeds game board at the end, go to x=7 and y=3
                if(playerOne.yCell > 3 || (playerOne.yCell == 3 && playerOne.xCell > 7)){
                    playerOne.xCell = 7;
                    playerOne.yCell = 3;
                    pOneImageView.setX(playerOne.xCell*100+25);
                    pOneImageView.setY(playerOne.yCell*100);
                }
                System.out.println("T1 A Button T1X: " + playerOne.xCell + "T1Y " + playerOne.yCell);
                if(playerOne.xCell == 7 && playerOne.yCell == 3){
                    displayEndGameNotification();
                }
            }
        });

        team1AnswerBButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	playerOne.xCell = rollValue + playerOne.xCell;
                pOneImageView.setX(playerOne.xCell*100+25);
                if(playerOne.xCell > 7) {
                	playerOne.xCell = playerOne.xCell - 8;
                	playerOne.yCell++;
                	pOneImageView.setX(playerOne.xCell*100+25);
                	pOneImageView.setY(playerOne.yCell*100);
                }
                
                if(playerOne.xCell == 1 && playerOne.yCell == 1) {
                	playerOne.yCell = 2;
                	pOneImageView.setY(playerOne.yCell*100);
                	shortcutLabel.setText("You landed on a shortcut!");
                }else if(playerOne.xCell == 5 && playerOne.yCell ==2) {
                	playerOne.yCell = 3;
                	pOneImageView.setY(playerOne.yCell*100);
                	shortcutLabel.setText("You landed on a shortcut!");
                } else {
                	shortcutLabel.setText("");
                }

                if((t1count == 2 && isQuestionBeingDisplayed() == true) ||
                        (t1count == 3 && isQuestionBeingDisplayed() == true) ||
                        (t1count == 4 && isQuestionBeingDisplayed() == true)){
                    playerOneScore++;
                    playerOneScoreLabel.setText("Player One: " + playerOneScore);
                    playerOneAnswerLabel.setText("Answer: " + getCorrectAnswer("player1"));
                    playerOneLabelResponse.setText("You are: " + "CORRECT!");
                    dismissQuestion();
                }
                else{
                    reduceLivesPlayerOne();
                    playerOneAnswerLabel.setText("Answer: " + getCorrectAnswer("player1"));
                    playerOneLabelResponse.setText("You are: " + "WRONG!");
                    dismissQuestion();
                }
                //if exceeds game board at the end, go to x=7 and y=3
                if(playerOne.yCell > 3 || (playerOne.yCell == 3 && playerOne.xCell > 7)){
                    playerOne.xCell = 7;
                    playerOne.yCell = 3;
                    pOneImageView.setX(playerOne.xCell*100+25);
                    pOneImageView.setY(playerOne.yCell*100);
                }
                System.out.println("T1 B Button T1X: " + playerOne.xCell + "T1Y " + playerOne.yCell);
                if(playerOne.xCell == 7 && playerOne.yCell == 3){
                    displayEndGameNotification();
                }
            }
        });

        team1AnswerCButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	playerOne.xCell = rollValue + playerOne.xCell;
                pOneImageView.setX(playerOne.xCell*100+25);
                if(playerOne.xCell > 7) {
                	playerOne.xCell = playerOne.xCell - 8;
                	playerOne.yCell++;
                	pOneImageView.setX(playerOne.xCell*100+25);
                	pOneImageView.setY(playerOne.yCell*100);
                }
                
                if(playerOne.xCell == 1 && playerOne.yCell == 1) {
                	playerOne.yCell = 2;
                	pOneImageView.setY(playerOne.yCell*100);
                	shortcutLabel.setText("You landed on a shortcut!");
                }else if(playerOne.xCell == 5 && playerOne.yCell ==2) {
                	playerOne.yCell = 3;
                	pOneImageView.setY(playerOne.yCell*100);
                	shortcutLabel.setText("You landed on a shortcut!");
                } else {
                	shortcutLabel.setText("");
                }
                
                if(t1count == 5 && isQuestionBeingDisplayed() == true){
                    playerOneScore++;
                    playerOneScoreLabel.setText("Player One: " + playerOneScore);
                    playerOneAnswerLabel.setText("Answer: " + getCorrectAnswer("player1"));
                    playerOneLabelResponse.setText("You are: " + "CORRECT!");
                    dismissQuestion();
                    
                }
                else{
                    reduceLivesPlayerOne();
                    playerOneAnswerLabel.setText("Answer: " + getCorrectAnswer("player1"));
                    playerOneLabelResponse.setText("You are: " + "WRONG!");
                    dismissQuestion();
                }
                //if exceeds game board at the end, go to x=7 and y=3
                if(playerOne.yCell > 3 || (playerOne.yCell == 3 && playerOne.xCell > 7)){
                    playerOne.xCell = 7;
                    playerOne.yCell = 3;
                    pOneImageView.setX(playerOne.xCell*100+25);
                    pOneImageView.setY(playerOne.yCell*100);
                }
                System.out.println("T1 C Button T1X: " + playerOne.xCell + "T1Y " + playerOne.yCell);
                if(playerOne.xCell == 7 && playerOne.yCell == 3){
                    displayEndGameNotification();
                }
            }
        });
        System.out.println("TEAM 1: " + playerOne.xCell + " " + playerOne.yCell);
    }

    public void team2AnswerButtons(){
        team2AnswerAButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	playerTwo.xCell = rollValue + playerTwo.xCell;
                pTwoImageView.setX(playerTwo.xCell*100+25);
                if(playerTwo.xCell > 7) {
                	playerTwo.xCell = playerTwo.xCell - 8;
                	playerTwo.yCell++;
                	pTwoImageView.setX(playerTwo.xCell*100+25);
                	pTwoImageView.setY(playerTwo.yCell*100+50);
                }
                
                if(playerTwo.xCell == 1 && playerTwo.yCell == 1) {
                	playerTwo.yCell = 2;
                	pTwoImageView.setY(playerTwo.yCell*100+50);
                	shortcutLabel.setText("You landed on a shortcut!");
                }else if(playerTwo.xCell == 5 && playerTwo.yCell ==2) {
                	playerTwo.yCell = 3;
                	pTwoImageView.setY(playerTwo.yCell*100+50);
                	shortcutLabel.setText("You landed on a shortcut!");
                } else {
                 	shortcutLabel.setText("");
                }
                
                if((t2count == 2 && isQuestionBeingDisplayed()) ||
                        (t2count == 3 && isQuestionBeingDisplayed())){
                    playerTwoScore++;
                    playerTwoScoreLabel.setText("Player Two: " + playerTwoScore);
                    playerTwoAnswerLabel.setText("Answer: " + getCorrectAnswer("player2"));
                    playerTwoLabelResponse.setText("Your are: " + "CORRECT!");
                    dismissQuestion();
                    
                }
                else{
                	reduceLivesPlayerTwo();
                    playerTwoAnswerLabel.setText("Answer: " + getCorrectAnswer("player2"));
                    playerTwoLabelResponse.setText("You Are: " + "WRONG!");
                    dismissQuestion();
                    
                }
                //if exceeds game board at the end, go to x=7 and y=3
                if(playerTwo.yCell > 3 || (playerTwo.yCell == 3 && playerTwo.xCell > 7)){
                    playerTwo.xCell = 7;
                    playerTwo.yCell = 3;
                    pTwoImageView.setX(playerTwo.xCell*100+25);
                    pTwoImageView.setY(playerTwo.yCell*100+50);
                }
                System.out.println("T2 A Button T2X: " + playerTwo.xCell + "T2Y " + playerTwo.yCell);
                if(playerTwo.xCell == 7 && playerTwo.yCell == 3){
                    displayEndGameNotification();
                }
            }
        });

        team2AnswerBButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	playerTwo.xCell = rollValue + playerTwo.xCell;
                pTwoImageView.setX(playerTwo.xCell*100+25);
                if(playerTwo.xCell > 7) {
                	playerTwo.xCell = playerTwo.xCell - 8;
                	playerTwo.yCell++;
                	pTwoImageView.setX(playerTwo.xCell*100+25);
                	pTwoImageView.setY(playerTwo.yCell*100+50);
                }
                
                if(playerTwo.xCell == 1 && playerTwo.yCell == 1) {
                	playerTwo.yCell = 2;
                	pTwoImageView.setY(playerTwo.yCell*100+50);
                	shortcutLabel.setText("You landed on a shortcut!");
                }else if(playerTwo.xCell == 5 && playerTwo.yCell ==2) {
                	playerTwo.yCell = 3;
                	pTwoImageView.setY(playerTwo.yCell*100+50);
                	shortcutLabel.setText("You landed on a shortcut!");
                } else {
                 	shortcutLabel.setText("");
                }
                 
                if(t2count == 1 && isQuestionBeingDisplayed()){
                    playerTwoScore++;
                    playerTwoScoreLabel.setText("Player Two: " + playerTwoScore);
                    playerTwoAnswerLabel.setText("Answer: " + getCorrectAnswer("player2"));
                    playerTwoLabelResponse.setText("Your are: " + "CORRECT!");
                    dismissQuestion();
                    playerTwo.xCell = rollValue + playerTwo.xCell;
                    
                }
                else{
                    reduceLivesPlayerTwo();
                    playerTwoAnswerLabel.setText("Answer: " + getCorrectAnswer("player2"));
                    playerTwoLabelResponse.setText("Your are: " + "WRONG!");
                    dismissQuestion();
                }
                //if exceeds game board at the end, go to x=7 and y=3
                System.out.println("T2 B Button T2X: " + playerTwo.xCell + "T2Y " + playerTwo.yCell);
                if(playerTwo.yCell > 3 || (playerTwo.yCell == 3 && playerTwo.xCell > 7)){
                    playerTwo.xCell = 7;
                    playerTwo.yCell = 3;
                    pTwoImageView.setX(playerTwo.xCell*100+25);
                    pTwoImageView.setY(playerTwo.yCell*100+50);
                }
                System.out.println("T2 B Button T2X: " + playerTwo.xCell + "T2Y " + playerTwo.yCell);
                if(playerTwo.xCell == 7 && playerTwo.yCell == 3){
                    displayEndGameNotification();
                }
            }
        });

        team2AnswerCButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	playerTwo.xCell = rollValue + playerTwo.xCell;
                pTwoImageView.setX(playerTwo.xCell*100+25);
                if(playerTwo.xCell > 7) {
                	playerTwo.xCell = playerTwo.xCell - 8;
                	playerTwo.yCell++;
                	pTwoImageView.setX(playerTwo.xCell*100+25);
                	pTwoImageView.setY(playerTwo.yCell*100+50);
                }
                
                if(playerTwo.xCell == 1 && playerTwo.yCell == 1) {
                	playerTwo.yCell = 2;
                	pTwoImageView.setY(playerTwo.yCell*100+50);
                	shortcutLabel.setText("You landed on a shortcut!");
                }else if(playerTwo.xCell == 5 && playerTwo.yCell ==2) {
                	playerTwo.yCell = 3;
                	pTwoImageView.setY(playerTwo.yCell*100+50);
                	shortcutLabel.setText("You landed on a shortcut!");
                } else {
                 	shortcutLabel.setText("");
                }
                
                if((t2count == 4 && isQuestionBeingDisplayed()) ||
                        (t2count == 5 && isQuestionBeingDisplayed())){
                    playerTwoScore++;
                    playerTwoScoreLabel.setText("Player Two: " + playerTwoScore);
                    playerTwoAnswerLabel.setText("Answer: " + getCorrectAnswer("player2"));
                    playerTwoLabelResponse.setText("Your are: " + "CORRECT!");
                    dismissQuestion();
                }
                else{
                    reduceLivesPlayerTwo();
                    playerTwoAnswerLabel.setText("Answer: " + getCorrectAnswer("player2"));
                    playerTwoLabelResponse.setText("You Are: " + "WRONG!");
                    dismissQuestion();
                }
                //if exceeds game board at the end, go to x=7 and y=3
                if(playerTwo.yCell > 3 || (playerTwo.yCell == 3 && playerTwo.xCell > 7)){
                    playerTwo.xCell = 7;
                    playerTwo.yCell = 3;
                    pTwoImageView.setX(playerTwo.xCell*100+25);
                    pTwoImageView.setY(playerTwo.yCell*100+50);
                }
                System.out.println("T2 C Button T2X: " + playerTwo.xCell + "T2Y " + playerTwo.yCell);
                if(playerTwo.xCell == 7 && playerTwo.yCell == 3){
                    displayEndGameNotification();
                }
            }
        });
    }

    public String getCorrectAnswer(String player){
        if((t1count == 1 && player == "player1") ||
                (t2count == 2 && player == "player2") || (t2count == 3 && player == "player2")){
            return a;
        }
        else if((t1count == 2 && player == "player1") || (t1count == 3 && player == "player1") || (t1count == 4 && player == "player1") ||
                    (t2count == 1 && player == "player2")){
            return b;
        }
        else if((t1count == 5 && player == "player1") ||
                    (t2count == 4 && player == "player2") || (t2count == 5 && player == "player2")){
            return c;
        }
        else {
            return "";
        }
    }

    public void displayEndGameNotification(){
        ButtonType playAgain = new ButtonType("Play Again", ButtonBar.ButtonData.OK_DONE);
        ButtonType expansionPack = new ButtonType("Expansion Pack", ButtonBar.ButtonData.OK_DONE);
        end = new Alert(Alert.AlertType.INFORMATION, "Good Game!", playAgain, expansionPack);
        end.setTitle("Game Over");
        end.setHeaderText(null);
        end.showAndWait(); //returns a ButtonType value that we can use to figure out which button was pressed, if any
    }

    public void displayQuestion(String[] questions, int count, String player){
        if(questionDisplay == false){
            Rectangle rect = new Rectangle(xDimension, yDimension, 8*scale, 4*scale); //messed with scale of rect a bit
            rect.setStroke(Color.BLACK); //black outline
            rect.setFill(Color.WHITE);
            String question;
            if(count >= questions.length){
                question = "Error, not enough questions";
            }
            else {
                question = questions[count];
            }
            if(t1count == count && player == "player1"){
                t1count++;
            }
            else if(t2count == count && player == "player2"){
                t2count++;
            }
            Text text = new Text(10, 50, question);
            text.setFont(new Font(30));
            stack = new StackPane();
            stack.getChildren().addAll(rect, text);
            root.getChildren().add(stack);
            questionDisplay = true;
        }
//        else{
//            root.getChildren().remove(stack);
//            questionDisplay = false;
//        }
    }


    public boolean isQuestionBeingDisplayed(){

        return questionDisplay;
    }

    public void dismissQuestion(){
        root.getChildren().remove(stack);
        questionDisplay = false;
    }

    public int rollDice(){
        Random rand = new Random();
        int randInt = rand.nextInt(6) + 1;

        rollValue = randInt;
        diceLabel.setText("Roll: " + randInt);
        loadDiceRollImage(randInt);
        
        root.getChildren().add(diceImageView);

        return rollValue;

    }

    public void drawMap() {

        for(int x = 0; x < xDimension; x++){
            for(int y = 0; y < yDimension; y++){
                Rectangle rect = new Rectangle(x*scale,y*scale,scale,scale);
                rect.setStroke(Color.BLACK); // We want the black outline
                if(x == 0 && y == 0){
                    //rect.setFill(Color.GREEN); // fills the board with random colors
                    startImage = new Image("/img/start.png", playerScale, playerScale, true, true);
                    rect.setFill(new ImagePattern(startImage));
                }
                else if(x == 7 && y == 3){
                    //rect.setFill(Color.RED); // fills the board with random colors
                    endImage = new Image("/img/end.png", playerScale, playerScale, true, true);
                    rect.setFill(new ImagePattern(endImage));
                }
                else if(x == 1 && y == 1 || x == 5 && y == 2) {
                	shortcutImageOne = new Image("/img/shortcutImageOne.png", playerScale, playerScale, true, true);
                    rect.setFill(new ImagePattern(shortcutImageOne));
                } 
//                else if(x == 7 && y == 1) {
//                	shortcutImageTwo = new Image("/img/shortcutImageTwo.png", playerScale, playerScale, true, true);
//                    rect.setFill(new ImagePattern(shortcutImageTwo));
//                }
                else{
                    rect.setFill(Color.color(Math.random(), Math.random(), Math.random())); // fills the board with random colors
                }
                root.getChildren().add(rect); // Add to the node tree in the pane
            }
        }
    }

    public void placePlayerOne(){
        playerOne = new Player();
    }
    public void placePlayerTwo(){
        playerTwo = new Player();
    }

    public void loadPlayerOneImage(){
        pOneImage = new Image("/img/coolEmoji.png", playerScale, playerScale, true, true);
        pOneImageView = new ImageView(pOneImage);
        pOneImageView.setX(playerOne.getPlayerLocation().x + playerScale/2);
        pOneImageView.setY(playerOne.getPlayerLocation().y + 0);
    }

    public void loadPlayerTwoImage(){
        pTwoImage = new Image("/img/hungryEmoji.png", playerScale, playerScale, true, true);
        pTwoImageView = new ImageView(pTwoImage);
        pTwoImageView.setX(playerTwo.getPlayerLocation().x + playerScale/2);
        pTwoImageView.setY(playerTwo.getPlayerLocation().y + playerScale);
    }
    
    public void loadDiceRollImage(int rollNumber){
        diceImage = new Image("/img/" + rollNumber + ".png", diceScale, diceScale, true, true);
        diceImageView = new ImageView(diceImage);
        diceImageView.setX(900);
        diceImageView.setY(50);

    }


    public static void main(String[] args) {
        launch(args);
    }
}