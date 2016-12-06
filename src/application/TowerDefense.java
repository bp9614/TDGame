package application;

import entities.Archer;
import entities.Enemy;
import entities.Fighter;
import entities.GameObject;
import entities.King;
import entities.Player;
import entities.Prince;
import entities.Princess;
import entities.Queen;
import entities.Tank;
import entities.Warrior;
import effects.Graphics;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/*
 * The project/GUI will be using JavaFX. Built-in Java library. README is available if you can't run it.
 * All JavaFX applications must extend Application to run.
 * -----------------------------------------------------------------------------------------------------
 * The TowerDefense class is the main source of operations for this game. It carries the screen (in JavaFX
 * the Stage), the scene, which is the medium for the pane and is what the stage shows, and the pane, 
 * holds any and every object to be displayed. There is an inner class called Menu, which is what holds
 * any objects/pane elements related to a menu. The reason we chose to have the Menu as an inner class is 
 * that games may have menus, but the game is not a menu, so it should be a separate class. Also, to 
 * allow the class to be able to use the main game's methods, menu must be within the class.
 * ------------------------------------------------------------------------------------------------------
 * To avoid immense amount of loading of images (although, any character animations have to constantly
 * load up a new image, which will significantly slow down the program and can cause .25 FPS (and or 
 * crashes), so, if you read this Carpenter, don't use 8x, don't ever use 8x, and usually don't use 4x 
 * if there is 12+ characters on screen)). We also set them as final to alert any programmers that these
 * files are only loaded once. (Though they may be moved around/changed internally).
 * ------------------------------------------------------------------------------------------------------
 * We don't want people to redefine our class, neither do we want them to randomly call one of these 
 * methods, so we set it as a final class (un-inheritable), and we set every class to private.
 * ------------------------------------------------------------------------------------------------------
 * Just for added effects, unless you are playing the game, the background is blurred out. Other effects
 * include when you are adding in enemies, you'll see a button to click, which then shows you an interface
 * to click and choose what soldier to place. Also on that screen, shows you where to click to add the
 * soldiers to.
 */
public final class TowerDefense extends Application{
	private Scene scene;
	private Pane onScene;
	private Settings settings;
	private Timeline gameTimeline;
	private boolean isGamePlaying;
	private String addThisSoldier;
	private Player player, enemy;
	private final Menu menus;
	private final Text numberOfCoinsText, coinCost_1, coinCost_2, coinCost_3;
	private final Button addToArmyButton;
	private final Rectangle characterInterface_1, characterInterface_2, characterInterface_3;
	private final ImageView coinImage_1, coinImage_2, coinImage_3, archerImage, warriorImage, tankImage;
	
	public TowerDefense(){
		onScene = new Pane();
		
		scene = new Scene(onScene, 1200, 900);

		settings = new Settings();
		settings.loadSettings();
		
		menus = new Menu();
		
		isGamePlaying = false;
		
		player = new Player();
		enemy = new Enemy();
		
		numberOfCoinsText = new Text(1120, 42, "x" + player.getNumberOfCoins());
		
		addToArmyButton = new Button(">>");
		
		characterInterface_1 = new Rectangle(1003, 115, 150, 80);
		characterInterface_2 = new Rectangle(1003, 195, 150, 80);
		characterInterface_3 = new Rectangle(1003, 275, 150, 80);
		coinCost_1 = new Text(1100, 165, "x20");
		coinCost_2 = new Text(1100, 245, "x15");
		coinCost_3 = new Text(1100, 325, "x30");
		
		coinImage_1 = Graphics.createCoin(1075, 148);
		coinImage_2 = Graphics.createCoin(1075, 228);
		coinImage_3 = Graphics.createCoin(1075, 308);
		archerImage = new ImageView(Graphics.createArcher("Attacking", "Right", 1));
		warriorImage = new ImageView(Graphics.createWarrior("Attacking", "Right", 2));
		tankImage = new ImageView(Graphics.createTank("Attacking", "Right", 2));
		
		addThisSoldier = "";
		
		setDefaultsAndEvents();
		setGameTimeline();
	}
	
	/*
	 * To avoid having an overload of materials being initialized in the constructor, this and one other
	 * method will set all the default values (and possible events when clicked on).
	 */
	private void setDefaultsAndEvents(){
		onScene.setBackground(Graphics.BLURRED_BACKGROUND);
		numberOfCoinsText.setFont(new Font(15));
		
		/*
		 * All interfaces are outlined in black to separate them. Gray was a nice color to use for the
		 * interface, opacity (transparency) is lowered from 1 (non-transparent) so that the player
		 * can still see the castle/royal family through the interface. Images below go with the interface,
		 * showing the user what characters they can add and for what price.
		 */
		characterInterface_1.setStroke(Color.BLACK);
		characterInterface_1.setFill(Color.LIGHTGRAY);
		characterInterface_1.setOpacity(0.8);
		characterInterface_2.setStroke(Color.BLACK);
		characterInterface_2.setFill(Color.LIGHTGRAY);
		characterInterface_2.setOpacity(0.8);
		characterInterface_3.setStroke(Color.BLACK);
		characterInterface_3.setFill(Color.LIGHTGRAY);
		characterInterface_3.setOpacity(0.8);
		
		coinCost_1.setFont(new Font(15));
		coinCost_2.setFont(new Font(15));
		coinCost_3.setFont(new Font(15));
		
		archerImage.setX(1030);
		archerImage.setY(138);
		
		warriorImage.setX(1030);
		warriorImage.setY(218);
		
		tankImage.setX(1030);
		tankImage.setY(298);
		
		
		addToArmyButton.setLayoutX(1152);
		addToArmyButton.setLayoutY(115);
		
		
		characterInterface_1.setOnMouseClicked(e->{
			addThisSoldier = "Archer";
			characterInterface_1.setFill(Color.GRAY);;
			characterInterface_2.setFill(Color.LIGHTGRAY);
			characterInterface_3.setFill(Color.LIGHTGRAY);
		});
		characterInterface_2.setOnMouseClicked(e->{
			addThisSoldier = "Warrior";
			characterInterface_1.setFill(Color.LIGHTGRAY);;
			characterInterface_2.setFill(Color.GRAY);
			characterInterface_3.setFill(Color.LIGHTGRAY);
		});
		characterInterface_3.setOnMouseClicked(e->{
			addThisSoldier = "Tank";
			characterInterface_1.setFill(Color.LIGHTGRAY);;
			characterInterface_2.setFill(Color.LIGHTGRAY);
			characterInterface_3.setFill(Color.GRAY);
		});
		
		addToArmyButton.setOnAction(e->{
			if(addToArmyButton.getText().equals(">>")){
				addToArmyButton.setText("<<");
				setAddToArmyEvent();
				onScene.setBackground(Graphics.CANCLICK);
				onScene.getChildren().addAll(characterInterface_1, characterInterface_2,
						characterInterface_3, coinCost_1, coinCost_2, coinCost_3, coinImage_1,
						coinImage_2, coinImage_3, archerImage, warriorImage, tankImage);
			}
			else{
				addToArmyButton.setText(">>");
				onScene.setOnMouseClicked(null);
				onScene.setBackground(Graphics.BACKGROUND);
				onScene.getChildren().removeAll(characterInterface_1, characterInterface_2,
						characterInterface_3, coinCost_1, coinCost_2, coinCost_3, coinImage_1,
						coinImage_2, coinImage_3, archerImage, warriorImage, tankImage);
			}
		});
	}
	
	private void newGame(){
		reset();
		
		onScene.setBackground(Graphics.BACKGROUND);
		onScene.getChildren().addAll(addToArmyButton, Graphics.createCoin(1095, 25), numberOfCoinsText);
		
		isGamePlaying = true;
		gameTimeline.play();
		
		addCharacters();
		setPauseEvent();
	}
	
	private void continueGame(){
		onScene.setBackground(Graphics.BACKGROUND);
		onScene.getChildren().addAll(addToArmyButton, Graphics.createCoin(1095, 25), numberOfCoinsText);
		
		gameTimeline.play();
		
		addCharacters();
		setPauseEvent();
	}
	
	private void setPauseEvent(){
		scene.setOnKeyPressed(e->{
			if(e.getCode().equals(KeyCode.ESCAPE)){
				menus.pause();
				setResumeEvent();
			}
		});
	}
	
	private void setResumeEvent(){
		scene.setOnKeyPressed(e->{
			if(e.getCode().equals(KeyCode.ESCAPE)){
				menus.resume();
				setPauseEvent();
			}
		});
	}
	
	private void setAddToArmyEvent(){
		onScene.setOnMouseClicked(e->{
			if(e.getY() >= 265 && e.getY() <= 320){
				if(e.getX() >= 187 && e.getX() <= 286){
					addSoldier("Third Floor");
				}
			}
			else if(e.getY() >= 460 && e.getY() <= 505){
				if(e.getX() >= 194 && e.getX() <= 283){
					addSoldier("Second Floor");
				}
			}
			else if(e.getY() >= 665 && e.getY() <= 715){
				if(e.getX() >= 189 && e.getX() <= 294){
					addSoldier("First Floor");
				}
				else if(e.getX() >= 330 && e.getX() <= 440){
					addSoldier("Outside");
				}
			}
			else{
				System.out.println("?");
			}
		});
	}
	
	private void addSoldier(String location){
		if(addThisSoldier.equals("Archer") && player.getNumberOfCoins() >= 20){
			player.changeNumberOfCoins(-20);
			onScene.getChildren().add(player.addArcher(location).getCharacterPortrait());
			numberOfCoinsText.setText("x" + player.getNumberOfCoins());
		}
		if(addThisSoldier.equals("Warrior") && player.getNumberOfCoins() >= 15){
			player.changeNumberOfCoins(-15);
			onScene.getChildren().add(player.addWarrior(location).getCharacterPortrait());
			numberOfCoinsText.setText("x" + player.getNumberOfCoins());
		}
		if(addThisSoldier.equals("Tank") && player.getNumberOfCoins() >= 30){
			player.changeNumberOfCoins(-30);
			onScene.getChildren().add(player.addTank(location).getCharacterPortrait());
			numberOfCoinsText.setText("x" + player.getNumberOfCoins());
		}
	}
	
	private void setGameTimeline(){
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>(){
			private int giveCoins = 1;
			
			@Override
			public void handle(ActionEvent e) {
				((Enemy) enemy).makeMove(player);
				increaseCoins();
				placeCharacters();
				moveCharacters();
				removeDeadCharacters();
				hasWon();
			}
			
			public void removeDeadCharacters(){
				player.removeDeadCharacters();
				enemy.removeDeadCharacters();
				
				for(GameObject deadPeople: player.getListOfDeadCharacters()){
					onScene.getChildren().remove(deadPeople.getCharacterPortrait());
				}
				
				for(GameObject deadPeople: enemy.getListOfDeadCharacters()){
					onScene.getChildren().remove(deadPeople.getCharacterPortrait());
				}
			}
			
			public void placeCharacters(){
				for(GameObject royality: player.getRoyality()){
					if(!onScene.getChildren().contains(royality.getCharacterPortrait())){
						onScene.getChildren().add(royality.getCharacterPortrait());
					}
				}
				for(Fighter invaders: player.getInvadingArmy()){
					if(!onScene.getChildren().contains(invaders.getCharacterPortrait())){
						onScene.getChildren().add(invaders.getCharacterPortrait());
					}
				}
				for(Fighter firstFloor: player.getFirstFloorDefendingArmy()){
					if(!onScene.getChildren().contains(firstFloor.getCharacterPortrait())){
						onScene.getChildren().add(firstFloor.getCharacterPortrait());
					}
				}
				for(Fighter secondFloor: player.getSecondFloorDefendingArmy()){
					if(!onScene.getChildren().contains(secondFloor.getCharacterPortrait())){
						onScene.getChildren().add(secondFloor.getCharacterPortrait());
					}
				}
				for(Fighter thirdFloor: player.getThirdFloorDefendingArmy()){
					if(!onScene.getChildren().contains(thirdFloor.getCharacterPortrait())){
						onScene.getChildren().add(thirdFloor.getCharacterPortrait());
					}
				}
				
				
				for(GameObject royality: enemy.getRoyality()){
					if(!onScene.getChildren().contains(royality.getCharacterPortrait())){
						onScene.getChildren().add(royality.getCharacterPortrait());
					}
				}
				for(Fighter invaders: enemy.getInvadingArmy()){
					if(!onScene.getChildren().contains(invaders.getCharacterPortrait())){
						onScene.getChildren().add(invaders.getCharacterPortrait());
					}
				}
				for(Fighter firstFloor: enemy.getFirstFloorDefendingArmy()){
					if(!onScene.getChildren().contains(firstFloor.getCharacterPortrait())){
						onScene.getChildren().add(firstFloor.getCharacterPortrait());
					}
				}
				for(Fighter secondFloor: enemy.getSecondFloorDefendingArmy()){
					if(!onScene.getChildren().contains(secondFloor.getCharacterPortrait())){
						onScene.getChildren().add(secondFloor.getCharacterPortrait());
					}
				}
				for(Fighter thirdFloor: enemy.getThirdFloorDefendingArmy()){
					if(!onScene.getChildren().contains(thirdFloor.getCharacterPortrait())){
						onScene.getChildren().add(thirdFloor.getCharacterPortrait());
					}
				}
			}
			
			public void moveCharacters(){
				player.moveAllCharacters(enemy);
				enemy.moveAllCharacters(player);
			}
			
			public void increaseCoins(){
				if(giveCoins == 15){
					player.changeNumberOfCoins(1);
					enemy.changeNumberOfCoins(1);
					numberOfCoinsText.setText("x" + player.getNumberOfCoins());
					giveCoins = 1;
				}
				else{
					giveCoins++;
				}
			}
			
			public void hasWon(){
				if(player.getRoyality().size() == 0 || enemy.getRoyality().size() == 0){
					isGamePlaying = false;
					onScene.setOnMouseClicked(null);
					onScene.getChildren().clear();
					gameTimeline.stop();
					menus.gameOverMenu();
				}
			}
		};
		
		gameTimeline = new Timeline(new KeyFrame(Duration.millis(1000.0/30.0), event));
		gameTimeline.setCycleCount(Timeline.INDEFINITE);
		gameTimeline.setRate(settings.getSpeed());
	}
	
	private void addCharacters(){
		for(GameObject royality: player.getRoyality()){
			if(!onScene.getChildren().contains(royality.getCharacterPortrait())){
				onScene.getChildren().add(royality.getCharacterPortrait());
			}
		}
		for(Fighter invaders: player.getInvadingArmy()){
			if(!onScene.getChildren().contains(invaders.getCharacterPortrait())){
				onScene.getChildren().add(invaders.getCharacterPortrait());
			}
		}
		for(Fighter firstFloor: player.getFirstFloorDefendingArmy()){
			if(!onScene.getChildren().contains(firstFloor.getCharacterPortrait())){
				onScene.getChildren().add(firstFloor.getCharacterPortrait());
			}
		}
		for(Fighter secondFloor: player.getSecondFloorDefendingArmy()){
			if(!onScene.getChildren().contains(secondFloor.getCharacterPortrait())){
				onScene.getChildren().add(secondFloor.getCharacterPortrait());
			}
		}
		for(Fighter thirdFloor: player.getThirdFloorDefendingArmy()){
			if(!onScene.getChildren().contains(thirdFloor.getCharacterPortrait())){
				onScene.getChildren().add(thirdFloor.getCharacterPortrait());
			}
		}
		
		
		for(GameObject royality: enemy.getRoyality()){
			if(!onScene.getChildren().contains(royality.getCharacterPortrait())){
				onScene.getChildren().add(royality.getCharacterPortrait());
			}
		}
		for(Fighter invaders: enemy.getInvadingArmy()){
			if(!onScene.getChildren().contains(invaders.getCharacterPortrait())){
				onScene.getChildren().add(invaders.getCharacterPortrait());
			}
		}
		for(Fighter firstFloor: enemy.getFirstFloorDefendingArmy()){
			if(!onScene.getChildren().contains(firstFloor.getCharacterPortrait())){
				onScene.getChildren().add(firstFloor.getCharacterPortrait());
			}
		}
		for(Fighter secondFloor: enemy.getSecondFloorDefendingArmy()){
			if(!onScene.getChildren().contains(secondFloor.getCharacterPortrait())){
				onScene.getChildren().add(secondFloor.getCharacterPortrait());
			}
		}
		for(Fighter thirdFloor: enemy.getThirdFloorDefendingArmy()){
			if(!onScene.getChildren().contains(thirdFloor.getCharacterPortrait())){
				onScene.getChildren().add(thirdFloor.getCharacterPortrait());
			}
		}
	}
	
	private class Menu{
		private final Text title, decreaseVolume, increaseVolume, isMuteOrNot, volumeLevel, currentSpeed, 
			currentInstructions, creditsText, winOrLose, gameStatsTitle, playerTitle, enemyTitle,
			totalCoins_Player, totalCoins_Enemy, tank_Total_Player, tank_Total_Enemy, warrior_Total_Player, warrior_Total_Enemy, 
			archer_Total_Player, archer_Total_Enemy, princess_Killed_Player, princess_Killed_Enemy, prince_Killed_Player, 
			prince_Killed_Enemy, king_Killed_Player, king_Killed_Enemy, queen_Killed_Player, queen_Killed_Enemy;
		private final Text[] instructionsText;
		private final Button[] startMenuButtons, changeSpeeds, pauseButtons;
		private final Button muteOrUnmute, returnToStartMenu, returnToPauseMenu;
		private final Rectangle unfilledBar, volumeBar, pauseBox, menuBox;
		private final HBox rowOfButtons;
		private final VBox alignPauseButtons, gameStats_Images_Player, gameStats_Images_Enemy, gameStats_Text_Player,
			gameStats_Text_Enemy;
		private final ImageView gameStats_Coin_Player, gameStats_Coin_Enemy, gameStats_Warrior_Player, gameStats_Warrior_Enemy,
			gameStats_Tank_Player, gameStats_Tank_Enemy, gameStats_Archer_Player, gameStats_Archer_Enemy, gameStats_Princess_Player,
			gameStats_Princess_Enemy, gameStats_Prince_Player, gameStats_Prince_Enemy, gameStats_King_Player, gameStats_King_Enemy,
			gameStats_Queen_Player, gameStats_Queen_Enemy;
		
		public Menu(){
			title = new Text(450, 150, "Castle Siege");
			startMenuButtons = new Button[5];
			startMenuButtons[0] = new Button("New Game");
			startMenuButtons[1] = new Button("Continue Game");
			startMenuButtons[2] = new Button("Options");
			startMenuButtons[3] = new Button("Instructions");
			startMenuButtons[4] = new Button("Credits");
			
			
			decreaseVolume = new Text(425, 410, "-");
			increaseVolume = new Text(760, 410, "+");
			isMuteOrNot = new Text(450, 270, "");
			volumeLevel = new Text(475, 455, "Current Volume Level: " + settings.getVolume() + "%");
			muteOrUnmute = new Button();
			unfilledBar = new Rectangle(450, 385, 300, 30);
			volumeBar = new Rectangle(450, 385, 3 * settings.getVolume(), 30);
			currentSpeed = new Text(520, 590, "");
			changeSpeeds = new Button[4];
			changeSpeeds[0] = new Button("1x");
			changeSpeeds[1] = new Button("2x");
			changeSpeeds[2] = new Button("4x");
			changeSpeeds[3] = new Button("8x");
			rowOfButtons = new HBox(15);
			
			
			currentInstructions = new Text(580, 610, "1/2");
			instructionsText = new Text[2];
			instructionsText[0] = new Text(340, 260, "");
			instructionsText[1] = new Text(340, 260, "");
			
			
			creditsText = new Text(280, 300, "");
			
			
			pauseBox = new Rectangle(170, 250, Color.GRAY);
			menuBox = new Rectangle(600, 500, Color.GRAY);
			pauseButtons = new Button[4];
			pauseButtons[0] = new Button("Resume");
			pauseButtons[1] = new Button("Instructions");
			pauseButtons[2] = new Button("Options");
			pauseButtons[3] = new Button("Exit to Main Menu");
			alignPauseButtons = new VBox(10);
			
			
			winOrLose = new Text(320, 150, "");
			gameStatsTitle = new Text(450, 300, "Game Stats");
			playerTitle = new Text(340, 360, "Player");
			enemyTitle = new Text(750, 360, "Enemy");
			gameStats_Coin_Player = Graphics.createCoin(0, 0);
			gameStats_Coin_Enemy = Graphics.createCoin(0, 0); 
			gameStats_Warrior_Player = new ImageView(Graphics.createWarrior("Idle", "Right", 0)); 
			gameStats_Warrior_Enemy = new ImageView(Graphics.createWarrior("Idle", "Right", 0)); 
			gameStats_Tank_Player = new ImageView(Graphics.createTank("Idle", "Right", 0)); 
			gameStats_Tank_Enemy = new ImageView(Graphics.createTank("Idle", "Right", 0)); 
			gameStats_Archer_Player = new ImageView(Graphics.createArcher("Idle", "Right", 0)); 
			gameStats_Archer_Enemy = new ImageView(Graphics.createArcher("Idle", "Right", 0)); 
			gameStats_Princess_Player = new ImageView(Graphics.createPrincess("Idle", "Right", 0)); 
			gameStats_Princess_Enemy = new ImageView(Graphics.createPrincess("Idle", "Right", 0)); 
			gameStats_Prince_Player = new ImageView(Graphics.createPrince("Idle", "Right", 0)); 
			gameStats_Prince_Enemy = new ImageView(Graphics.createPrince("Idle", "Right", 0)); 
			gameStats_King_Player = new ImageView(Graphics.createKing("Idle", "Right", 0)); 
			gameStats_King_Enemy = new ImageView(Graphics.createKing("Idle", "Right", 0)); 
			gameStats_Queen_Player = new ImageView(Graphics.createQueen("Right")); 
			gameStats_Queen_Enemy = new ImageView(Graphics.createQueen("Right"));
			totalCoins_Player = new Text("");
			totalCoins_Enemy = new Text("");
			tank_Total_Player = new Text(""); 
			tank_Total_Enemy = new Text("");
			warrior_Total_Player = new Text("");
			warrior_Total_Enemy = new Text("");
			archer_Total_Player = new Text("");
			archer_Total_Enemy = new Text("");
			princess_Killed_Player = new Text("");
			princess_Killed_Enemy = new Text("");
			prince_Killed_Player = new Text("");
			prince_Killed_Enemy = new Text("");
			king_Killed_Player = new Text("");
			king_Killed_Enemy = new Text("");
			queen_Killed_Player = new Text(""); 
			queen_Killed_Enemy = new Text("");
			gameStats_Images_Player = new VBox(15);
			gameStats_Images_Enemy = new VBox(15);
			gameStats_Text_Player = new VBox(35);
			gameStats_Text_Enemy = new VBox(35);
			
			
			returnToStartMenu = new Button("Return to Start Menu");
			returnToPauseMenu = new Button("Return to Pause Menu");
			
			setDefaults();
			setEvents();
		}
		
		public void setDefaults(){
			title.setFont(Font.font("Kunstler Script", FontWeight.BOLD, 80));
			
			
			increaseVolume.setFont(new Font(30));
			decreaseVolume.setFont(new Font(30));
			
			isMuteOrNot.setFont(new Font(20));
			if(settings.isMute()){
				isMuteOrNot.setText("Is The Game's Volume Muted: Yes");
			}
			else{
				isMuteOrNot.setText("Is The Game's Volume Muted: No");
			}
			
			volumeLevel.setFont(new Font(20));
			
			if(settings.isMute()){
				muteOrUnmute.setText("Turn Volume On");
			}
			else{
				muteOrUnmute.setText("Turn Volume Off");
			}
			muteOrUnmute.setLayoutX(530);
			muteOrUnmute.setLayoutY(295);
			
			unfilledBar.setStroke(Color.BLACK);
			unfilledBar.setFill(Color.WHITE);
			volumeBar.setStroke(Color.BLACK);
			volumeBar.setFill(Color.AQUA);
			
			currentSpeed.setText("Current Speed: " + settings.getSpeed() + "x");
			currentSpeed.setFont(new Font(20));
			
			rowOfButtons.getChildren().addAll(changeSpeeds[0], changeSpeeds[1], changeSpeeds[2], 
					changeSpeeds[3]);
			rowOfButtons.setLayoutX(505);
			rowOfButtons.setLayoutY(520);
			
			
			currentInstructions.setFont(new Font(30));
			instructionsText[0].setFont(new Font(20));
			instructionsText[0].setText("The main goal of this game is to storm the enemy castle \n and defeat the royal family\n\n"
					+ "To place down the soldier you want select and drag to a\n location in the castle\n\n"
					+ "You have 3 soldier types: Swordsmen, Archer, and Tank\n\n"
					+ "Swordsmen are normal swordsmen that will run and attack \nenemies right in front of them\n\n"
					+ "Archers are ranged soldiers that attack from the rear\n\n");
			instructionsText[1].setFont(new Font(20));
			instructionsText[1].setText("Tanks are heavy soldiers and are there to take \ndamage and protect your Swordsmen and archers\n\n"
					+ "You gain gold from killing enemies and over time");
			
			
			creditsText.setFont(new Font(30));
			creditsText.setText("Main Programmer: Brian Pham \n\nMisc. Programmers: Amy Chen, Jonathan Li"
					+ "\n\nFerret Picture: Google\n\nDesigner: Jonathan Li");
			
			
			pauseBox.setX(515);
			pauseBox.setY(325);
			pauseBox.setArcHeight(15);
			pauseBox.setArcWidth(15);
			
			alignPauseButtons.getChildren().addAll(pauseButtons[0], pauseButtons[1], pauseButtons[2],
					pauseButtons[3]);
			alignPauseButtons.setLayoutX(530);
			alignPauseButtons.setLayoutY(375);
			alignPauseButtons.setAlignment(Pos.CENTER);
			
			menuBox.setX(300);
			menuBox.setY(200);
			menuBox.setArcHeight(15);
			menuBox.setArcWidth(15);
			
			
			winOrLose.setFont(Font.font(null, FontWeight.BOLD, 120));
			gameStatsTitle.setFont(new Font(60));
			
			playerTitle.setFont(new Font(40));
			playerTitle.setUnderline(true);
			
			enemyTitle.setFont(new Font(40));
			enemyTitle.setUnderline(true);
			
			
			totalCoins_Player.setFont(new Font(15));
			totalCoins_Enemy.setFont(new Font(15));
			tank_Total_Player.setFont(new Font(15)); 
			tank_Total_Enemy.setFont(new Font(15));
			warrior_Total_Player.setFont(new Font(15));
			warrior_Total_Enemy.setFont(new Font(15));
			archer_Total_Player.setFont(new Font(15));
			archer_Total_Enemy.setFont(new Font(15));
			princess_Killed_Player.setFont(new Font(15));
			princess_Killed_Enemy.setFont(new Font(15));
			prince_Killed_Player.setFont(new Font(15));
			prince_Killed_Enemy.setFont(new Font(15));
			king_Killed_Player.setFont(new Font(15));
			king_Killed_Enemy.setFont(new Font(15));
			queen_Killed_Player.setFont(new Font(15)); 
			queen_Killed_Enemy.setFont(new Font(15));
			
			gameStats_Images_Player.getChildren().addAll(gameStats_Coin_Player, gameStats_Warrior_Player, gameStats_Tank_Player,
					gameStats_Archer_Player, gameStats_Princess_Player, gameStats_Prince_Player, gameStats_King_Player,
					gameStats_Queen_Player);
			gameStats_Images_Player.setLayoutX(340);
			gameStats_Images_Player.setLayoutY(400);
			
			gameStats_Images_Enemy.getChildren().addAll(gameStats_Coin_Enemy, gameStats_Warrior_Enemy, gameStats_Tank_Enemy,
					gameStats_Archer_Enemy, gameStats_Princess_Enemy, gameStats_Prince_Enemy, gameStats_King_Enemy,
					gameStats_Queen_Enemy);
			gameStats_Images_Enemy.setLayoutX(750);
			gameStats_Images_Enemy.setLayoutY(400);

			gameStats_Text_Player.getChildren().addAll(totalCoins_Player, warrior_Total_Player, tank_Total_Player,
					archer_Total_Player, princess_Killed_Player, prince_Killed_Player, king_Killed_Player, queen_Killed_Player);
			gameStats_Text_Player.setLayoutX(380);
			gameStats_Text_Player.setLayoutY(400);
			
			gameStats_Text_Enemy.getChildren().addAll(totalCoins_Enemy, warrior_Total_Enemy, tank_Total_Enemy,
					archer_Total_Enemy, princess_Killed_Enemy, prince_Killed_Enemy, king_Killed_Enemy, queen_Killed_Enemy);
			gameStats_Text_Enemy.setLayoutX(790);
			gameStats_Text_Enemy.setLayoutY(400);
			
			
			returnToPauseMenu.setLayoutX(515);
			returnToPauseMenu.setLayoutY(640);
		}
		
		private void setEvents(){
			startMenuButtons[0].setOnAction(e->{
				removeMenus();
				newGame();
			});
			startMenuButtons[1].setOnAction(e->{
				removeMenus();
				continueGame();
			});
			startMenuButtons[2].setOnAction(e->{
				removeMenus();
				optionsMenu();
			});
			startMenuButtons[3].setOnAction(e->{
				removeMenus();
				instructionsMenu();
			});
			startMenuButtons[4].setOnAction(e->{
				removeMenus();
				creditsMenu();
			});
			
			
			decreaseVolume.setOnMouseClicked(e->{
				settings.decreaseVolume();
				volumeBar.setWidth(settings.getVolume() * 3);
				volumeLevel.setText("Current Volume Level: " + settings.getVolume() + "%");
			});
			increaseVolume.setOnMouseClicked(e->{
				settings.increaseVolume();
				volumeBar.setWidth(settings.getVolume() * 3);
				volumeLevel.setText("Current Volume Level: " + settings.getVolume() + "%");
			});
			volumeBar.setOnMouseClicked(e->{
				settings.setVolume((int) Math.round((e.getX() - 450) / 3));
				volumeBar.setWidth(settings.getVolume() * 3);
				volumeLevel.setText("Current Volume Level: " + settings.getVolume() + "%");
			});
			volumeBar.setOnMouseDragged(e->{
				settings.setVolume((int) Math.round((e.getX() - 450) / 3));
				volumeBar.setWidth(settings.getVolume() * 3);
				volumeLevel.setText("Current Volume Level: " + settings.getVolume() + "%");
			});
			unfilledBar.setOnMouseClicked(e->{
				settings.setVolume((int) Math.round((e.getX() - 450) / 3));
				volumeBar.setWidth(settings.getVolume() * 3);
				volumeLevel.setText("Current Volume Level: " + settings.getVolume() + "%");
			});
			unfilledBar.setOnMouseDragged(e->{
				settings.setVolume((int) Math.round((e.getX() - 450) / 3));
				volumeBar.setWidth(settings.getVolume() * 3);
				volumeLevel.setText("Current Volume Level: " + settings.getVolume() + "%");
			});
			muteOrUnmute.setOnAction(e->{
				if(settings.isMute()){
					settings.changeMute();
					muteOrUnmute.setText("Turn Volume Off");
					isMuteOrNot.setText("Is The Game's Volume Muted: No");
				}
				else{
					settings.changeMute();
					muteOrUnmute.setText("Turn Volume On");
					isMuteOrNot.setText("Is The Game's Volume Muted: Yes");
				}
			});
			changeSpeeds[0].setOnAction(e->{
				settings.setSpeed(1);
				currentSpeed.setText("Current Speed: " + settings.getSpeed() + "x");
				gameTimeline.setRate(settings.getSpeed());
			});
			changeSpeeds[1].setOnAction(e->{
				settings.setSpeed(2);
				currentSpeed.setText("Current Speed: " + settings.getSpeed() + "x");
				gameTimeline.setRate(settings.getSpeed());
			});
			changeSpeeds[2].setOnAction(e->{
				settings.setSpeed(4);
				currentSpeed.setText("Current Speed: " + settings.getSpeed() + "x");
				gameTimeline.setRate(settings.getSpeed());
			});
			changeSpeeds[3].setOnAction(e->{
				settings.setSpeed(8);
				currentSpeed.setText("Current Speed: " + settings.getSpeed() + "x");
				gameTimeline.setRate(settings.getSpeed());
			});
			
			
			
			instructionsText[0].setOnMouseClicked(e->{
				onScene.getChildren().remove(instructionsText[0]);
				onScene.getChildren().add(instructionsText[1]);
				currentInstructions.setText("2/2");
			});
			instructionsText[1].setOnMouseClicked(e->{
				onScene.getChildren().remove(instructionsText[1]);
				onScene.getChildren().add(instructionsText[0]);
				currentInstructions.setText("1/2");
			});
			
			
			pauseButtons[0].setOnAction(e->{
				resume();
			});
			pauseButtons[1].setOnAction(e->{
				currentInstructions.setText("1/2");
				onScene.getChildren().addAll(menuBox, currentInstructions, instructionsText[0],
						returnToPauseMenu);
			});
			pauseButtons[2].setOnAction(e->{
				onScene.getChildren().addAll(menuBox, muteOrUnmute, isMuteOrNot, volumeLevel, decreaseVolume, 
						increaseVolume, unfilledBar, volumeBar, rowOfButtons, currentSpeed, 
						returnToPauseMenu);
			});
			pauseButtons[3].setOnAction(e->{
				onScene.getChildren().clear();
				scene.setOnKeyPressed(null);
				startMenu();
				onScene.setBackground(Graphics.BLURRED_BACKGROUND);
			});
			
			returnToStartMenu.setOnAction(e->{
				removeMenus();
				startMenu();
			});
			returnToPauseMenu.setOnAction(e->{
				onScene.getChildren().removeAll(menuBox, currentInstructions, instructionsText[0], 
						instructionsText[1], muteOrUnmute, isMuteOrNot, volumeLevel, decreaseVolume,
						increaseVolume, unfilledBar, volumeBar, rowOfButtons, currentSpeed, 
						returnToPauseMenu);
			});
		}
		
		public void startMenu(){
			VBox columnOfButtons = new VBox(20);
			columnOfButtons.setLayoutX(370);
			columnOfButtons.setLayoutY(377);
			
			if(isGamePlaying){
				columnOfButtons.getChildren().addAll(startMenuButtons[0], startMenuButtons[1],
						startMenuButtons[2], startMenuButtons[3], startMenuButtons[4]);
			}
			else{
				columnOfButtons.getChildren().addAll(startMenuButtons[0], startMenuButtons[2], 
						startMenuButtons[3], startMenuButtons[4]);
			}
			
			onScene.getChildren().addAll(title, columnOfButtons);
		}
		
		public void instructionsMenu(){
			currentInstructions.setText("1/2");
			
			onScene.getChildren().addAll(currentInstructions, instructionsText[0], returnToStartMenu);
		}
		
		public void optionsMenu(){		
			onScene.getChildren().addAll(muteOrUnmute, isMuteOrNot, volumeLevel, decreaseVolume, 
					increaseVolume, unfilledBar, volumeBar, rowOfButtons, currentSpeed, returnToStartMenu);
		}
		
		public void creditsMenu(){
			onScene.getChildren().addAll(creditsText, returnToStartMenu);
		}
		
		public void gameOverMenu(){
			if(player.getRoyality().size() == 0){
				winOrLose.setText("YOU LOST");
			}
			else{
				winOrLose.setText("YOU WON");
			}
			
			
			totalCoins_Player.setText("Total Coins: " + player.getTotalNumberOfCoins());
			totalCoins_Enemy.setText("Total Coins: " + enemy.getTotalNumberOfCoins());

			princess_Killed_Player.setText("Status: Alive");  
			prince_Killed_Player.setText("Status: Alive");  
			king_Killed_Player.setText("Status: Alive");  
			queen_Killed_Player.setText("Status: Alive"); 
			princess_Killed_Enemy.setText("Status: Alive");  
			prince_Killed_Enemy.setText("Status: Alive");  
			king_Killed_Enemy.setText("Status: Alive");  
			queen_Killed_Enemy.setText("Status: Alive"); 
			
			int[] totalNumberOfSoldiers_Player = {0, 0, 0};
			int[] totalNumberOfSoldiers_Enemy = {0, 0, 0};
			for(Fighter invaders: player.getInvadingArmy()){
				checkCharacters(invaders, totalNumberOfSoldiers_Player);
			}
			for(Fighter firstFloor: player.getFirstFloorDefendingArmy()){
				checkCharacters(firstFloor, totalNumberOfSoldiers_Player);
			}
			for(Fighter secondFloor: player.getSecondFloorDefendingArmy()){
				checkCharacters(secondFloor, totalNumberOfSoldiers_Player);
			}
			for(Fighter thirdFloor: player.getThirdFloorDefendingArmy()){
				checkCharacters(thirdFloor, totalNumberOfSoldiers_Player);
			}
			for(GameObject deadPeople: player.getListOfDeadCharacters()){
				checkCharacters(deadPeople, totalNumberOfSoldiers_Player);
			}
			
			for(Fighter invaders: enemy.getInvadingArmy()){
				checkCharacters(invaders, totalNumberOfSoldiers_Enemy);
			}
			for(Fighter firstFloor: enemy.getFirstFloorDefendingArmy()){
				checkCharacters(firstFloor, totalNumberOfSoldiers_Enemy);
			}
			for(Fighter secondFloor: enemy.getSecondFloorDefendingArmy()){
				checkCharacters(secondFloor, totalNumberOfSoldiers_Enemy);
			}
			for(Fighter thirdFloor: enemy.getThirdFloorDefendingArmy()){
				checkCharacters(thirdFloor, totalNumberOfSoldiers_Enemy);
			}
			for(GameObject deadPeople: enemy.getListOfDeadCharacters()){
				checkCharacters(deadPeople, totalNumberOfSoldiers_Enemy);
			}
			
			warrior_Total_Player.setText("Total Employed: " + totalNumberOfSoldiers_Player[0]);
			warrior_Total_Enemy.setText("Total Employed: " + totalNumberOfSoldiers_Enemy[0]);
			tank_Total_Player.setText("Total Employed: " + totalNumberOfSoldiers_Player[1]); 
			tank_Total_Enemy.setText("Total Employed: " + totalNumberOfSoldiers_Enemy[1]);
			archer_Total_Player.setText("Total Employed: " + totalNumberOfSoldiers_Player[2]);
			archer_Total_Enemy.setText("Total Employed: " + totalNumberOfSoldiers_Enemy[2]);
			
			
			onScene.setBackground(Graphics.BLURRED_BACKGROUND);
			onScene.getChildren().addAll(returnToStartMenu, winOrLose, gameStatsTitle, playerTitle,
					enemyTitle, gameStats_Images_Player, gameStats_Images_Enemy, gameStats_Text_Player, gameStats_Text_Enemy);
		}
		
		private void checkCharacters(GameObject character, int[] array){
			if(character instanceof Warrior){
				array[0]+=1;
			}
			else if(character instanceof Tank){
				array[1]+=1;
			}
			else if(character instanceof Archer){
				array[2]+=1;
			}
			else if(character instanceof Princess){
				if(character.getBanner().equals("Enemy")){
					princess_Killed_Enemy.setText("Status: Killed");
				}
				else{
					princess_Killed_Player.setText("Status: Killed");
				}
			}
			else if(character instanceof Prince){
				if(character.getBanner().equals("Enemy")){
					prince_Killed_Enemy.setText("Status: Killed");
				}
				else{
					prince_Killed_Player.setText("Status: Killed");
				}
			}
			else if(character instanceof Queen){
				if(character.getBanner().equals("Enemy")){
					queen_Killed_Enemy.setText("Status: Killed");
				}
				else{
					queen_Killed_Player.setText("Status: Killed");
				}
			}
			else if(character instanceof King){
				if(character.getBanner().equals("Enemy")){
					king_Killed_Enemy.setText("Status: Killed");
				}
				else{
					king_Killed_Player.setText("Status: Killed");
				}
			}
		}
		
		public void removeMenus(){
			onScene.getChildren().clear();
		}
		
		public void pause(){
			gameTimeline.stop();
			
			onScene.setOnMouseClicked(null);
			addToArmyButton.setText(">>");
			
			onScene.getChildren().removeAll(addToArmyButton, characterInterface_1, characterInterface_2, 
					characterInterface_3, coinCost_1, coinCost_2, coinCost_3, coinImage_1, coinImage_2, 
					coinImage_3, archerImage, warriorImage, tankImage);
			onScene.setBackground(Graphics.BLURRED_BACKGROUND);
			onScene.getChildren().addAll(pauseBox, alignPauseButtons);
		}
		
		public void resume(){
			onScene.setBackground(Graphics.BACKGROUND);
			
			gameTimeline.play();
			
			setPauseEvent();
			
			onScene.getChildren().removeAll(pauseBox, alignPauseButtons, menuBox, currentInstructions, 
					instructionsText[0], instructionsText[1], muteOrUnmute, isMuteOrNot, volumeLevel, 
					decreaseVolume, increaseVolume, unfilledBar, volumeBar, rowOfButtons, currentSpeed, 
					returnToPauseMenu);
			onScene.getChildren().add(addToArmyButton);
		}
	}
	
	private void reset(){
		setGameTimeline();
		player = new Player();
		enemy = new Enemy();
		numberOfCoinsText.setText("x" + player.getNumberOfCoins());
		addThisSoldier = "";
		characterInterface_1.setFill(Color.LIGHTGRAY);
		characterInterface_2.setFill(Color.LIGHTGRAY);
		characterInterface_3.setFill(Color.LIGHTGRAY);
	}
	
	/*
	 * Once you call launch(...), the program will start here. We start the game off at the startMenu,
	 * which we setup from the Menu's startMenu.
	 * -----------------------------------------------------------------------------------------------
	 * To save settings, we call the saveSettings method of the settings class as the window is closed.
	 */
	@Override
	public void start(Stage primaryStage){
		menus.startMenu();
		
		primaryStage.setTitle("Tower Defense Game");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		primaryStage.setMaxHeight(920);
		primaryStage.setMinHeight(920);
		primaryStage.setMaxWidth(1210);
		primaryStage.setMinWidth(1210);
		primaryStage.setOnCloseRequest(e->{
			settings.saveSettings();
		});
	}
	
	public static void main(String args[]){
		launch(args);
	}
}
