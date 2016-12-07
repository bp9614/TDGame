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
 * BP
 * -----------------------------------------------------------------------------------------------------
 * The project/GUI will be using JavaFX. Built-in Java library. README is available if you can't run it.
 * All JavaFX applications must extend Application to run. 
 * -----------------------------------------------------------------------------------------------------
 * One predominant action you may see within this and most JavaFX classes are the lambda notation (for 
 * JavaFX), or e->{}. One example of the lambda notation in its full form is where the Timeline is 
 * initialized. So, lambda notion consists of an EventHandler, which as the name may state, handles events. 
 * It is very similar to the robocode where if something happens, an event is triggered and a certain
 * method (the handle method) is called. The lambda notation is just quicker way of writing the 
 * EventHandlers out (and what happens in that event). 
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
 * ------------------------------------------------------------------------------------------------------
 * FOR VARIABLES BELOW
 * ------------------------------------------------------------------------------------------------------
 * isGamePlay is used to determine, in the start menu, whether to allow the user to continue where they
 * left off in their game (only if you exited to the start menu, not if you reset the application).
 * ------------------------------------------------------------------------------------------------------
 * gameTimeline is the game. A timeline is allows animations, or even certain actions, to be done in a 
 * repetition as long as the timeline is playing. In this program, it controls movement, character 
 * actions, character addition/removals, and money addition.
 * ------------------------------------------------------------------------------------------------------
 * numberOfCoinsText is a text that is shown when you're playing the game to show how much money you
 * currently have.
 * ------------------------------------------------------------------------------------------------------
 * player and enemy, the two people (well, one is an algorithm) that will be playing this game. The players
 * contain every game object piece.
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
	 * BP
	 * -----------------------------------------------------------------------------------------------------
	 * To avoid having an overload of materials being initialized in the constructor, this and one other
	 * method will set all the default values (and possible events when clicked on).
	 */
	private void setDefaultsAndEvents(){
		onScene.setBackground(Graphics.BLURRED_BACKGROUND);
		numberOfCoinsText.setFont(new Font(15));
		
		/*
		 * BP
		 * -----------------------------------------------------------------------------------------------------
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
		
		/*
		 * BP
		 * ----------------------------------------------------------------------------------------------
		 * This is to open up the character addition interface/show where to add characters. Also adds 
		 * and removes any objects for the background depending on the current text of the army button 
		 * (>> is closed, << is open). Since the adding of the army event is based on clicking on the pane,
		 * when the option is closed, the pane's event has to be removed (by setting the event to null),
		 * so that the user can't accidentally click that area again and add a new character to the spot.
		 * ----------------------------------------------------------------------------------------------
		 * This is one of the examples of the lambda notation within this class. SetOnAction means when
		 * that button is clicked, just like a MouseClicked event (which this can also have), execute
		 * whatever is within this block. 
		 */
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
	
	/*
	 * BP
	 * ----------------------------------------------------------------------------------------------
	 * A few things need to be set up before the game can start. For a new game, every changable value
	 * must be reset, which includes resetting the timeline, every player, possible texts, so that
	 * a completely new game can be played. Next, since the game is playing now (well, a little after.
	 * Timelime has not played yet), the background can be changed to the non-blurry background, showing
	 * the user the castle and the ground. Any characters that the player and enemy have are added onto
	 * the pane to be displayed and the pause event (options shown when you press the escape key).
	 */
	private void newGame(){
		reset();
		
		onScene.setBackground(Graphics.BACKGROUND);
		onScene.getChildren().addAll(addToArmyButton, Graphics.createCoin(1095, 25), numberOfCoinsText);
		
		isGamePlaying = true;
		gameTimeline.play();
		
		addCharacters();
		setPauseEvent();
	}
	
	/*
	 * Very similar to the new game except the game isn't reset (so any previous characters will be
	 * added back in), and isGamePlaying does not need to be set to true since it was already set with
	 * the newGame method (will not be changed until the game ends).
	 */
	private void continueGame(){
		onScene.setBackground(Graphics.BACKGROUND);
		onScene.getChildren().addAll(addToArmyButton, Graphics.createCoin(1095, 25), numberOfCoinsText);
		
		gameTimeline.play();
		
		addCharacters();
		setPauseEvent();
	}
	
	/*
	 * BP
	 * ----------------------------------------------------------------------------------------------
	 * This particular event has to be given to the scene, only because key pressed events pertaining 
	 * not to (very) certain objects will all go to the scene. If the pane has a key pressed event ->
	 * ignored cause the scene takes higher precedence, even if the scene doesn't have an for key pressing.
	 * ----------------------------------------------------------------------------------------------
	 * What is function does is that it stops the game (in the pause class) and opens up the pause
	 * menu, then it sets this event (the onKeyPressed) to the resume event.
	 */
	private void setPauseEvent(){
		scene.setOnKeyPressed(e->{
			if(e.getCode().equals(KeyCode.ESCAPE)){
				menus.pause();
				setResumeEvent();
			}
		});
	}
	
	/*
	 * BP
	 * ----------------------------------------------------------------------------------------------
	 * As the name implies, if you press escape while any sort of pause menu is up (not the main start 
	 * menu, credits, etc.), which includes the smaller options and instructions menu, resumes the game
	 * and set the scene's escape/onKeyPressed event back to the pause event.
	 */
	private void setResumeEvent(){
		scene.setOnKeyPressed(e->{
			if(e.getCode().equals(KeyCode.ESCAPE)){
				menus.resume();
				setPauseEvent();
			}
		});
	}
	
	/*
	 * BP
	 * ----------------------------------------------------------------------------------------------
	 * So, this is the event for the pane (can also be for the scene, which is unlike for the keyPressed 
	 * event, which has to be on the scene) for adding characters/soldiers into the game. If you
	 * click on certain locations, adds a new soldier to the location you specified (well, certain
	 * location). These are the same locations shown within the rectangles in the background when
	 * the character addition interface is open.
	 * -----------------------------------------------------------------------------------------------------
	 * If any of the, well, rectangles are clicked on, that rectangle is changed from light gray to
	 * gray which would alert the player this is the current option. If any of the rectangles are
	 * gray, don't want to confuse the player and have them think that two are checked for some 
	 * reason. 
	 * -------------------------------------------------------------------------------------------
	 * Second functionality of these are that they alert the system which soldier to add. Although,
	 * I could've done this by looking for which one of these interfaces was gray, which would
	 * remove the need for the string indicating which to add and having to manage this variable.
	 */
	private void setAddToArmyEvent(){
		onScene.setOnMouseClicked(e->{
			if(e.getY() >= 265 && e.getY() <= 320 && e.getX() >= 187 && e.getX() <= 286){
				addSoldier("Third Floor");
			}
			else if(e.getY() >= 460 && e.getY() <= 505 && e.getX() >= 194 && e.getX() <= 283){
				addSoldier("Second Floor");
			}
			else if(e.getY() >= 665 && e.getY() <= 715 && e.getX() >= 189 && e.getX() <= 294){
				addSoldier("First Floor");
			}
			else if(e.getY() >= 665 && e.getY() <= 715 && e.getX() >= 330 && e.getX() <= 440){
				addSoldier("Outside");
			}
			else if(e.getX() >= 1003 && e.getX() <= 1153 && e.getY() >= 115 && e.getY() < 195){
				addThisSoldier = "Archer";
				characterInterface_1.setFill(Color.GRAY);;
				characterInterface_2.setFill(Color.LIGHTGRAY);
				characterInterface_3.setFill(Color.LIGHTGRAY);
			}
			else if(e.getX() >= 1003 && e.getX() <= 1153 && e.getY() >= 195 && e.getY() < 275){
				addThisSoldier = "Warrior";
				characterInterface_1.setFill(Color.LIGHTGRAY);;
				characterInterface_2.setFill(Color.GRAY);
				characterInterface_3.setFill(Color.LIGHTGRAY);
			}
			else if(e.getX() >= 1003 && e.getX() <= 1153 && e.getY() >= 275 && e.getY() < 355){
				addThisSoldier = "Tank";
				characterInterface_1.setFill(Color.LIGHTGRAY);;
				characterInterface_2.setFill(Color.LIGHTGRAY);
				characterInterface_3.setFill(Color.GRAY);
			}
		});
	}
	
	/*
	 * BP
	 * ----------------------------------------------------------------------------------------------
	 * So, this adds a soldier to the player's army, given the location and which character you've
	 * chosen to use. As long as you have enough money to buy it, money is deducted from your current,
	 * and a new character is placed into the army(list). To make the enemy appear instantly after 
	 * buying the soldier (if this option wasn't here, would have to wait for the next frame of the
	 * timeline to update that the character is there), had the player return that new character so that
	 * the program could add that character('s image) immediately. Same with the text for the amount
	 * of money you have. Updates it here so that the change is reflected immediately.
	 */
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
	
	/*
	 * BP
	 * ----------------------------------------------------------------------------------------------
	 * As stated before, this is what makes the game run, an event that will run at 30 frames per second
	 * at its default (up to 240 fps, but in actuality, probably will get crash your computer), executing
	 * this handle method every frame.
	 * ----------------------------------------------------------------------------------------------
	 * As an overview, what happens every turn is that it starts by increasing the number of coins for both
	 * players, then the enemy makes their move (whatever they decide), then attempts to places any 
	 * character that isn't on the screen that is supposed to be, has every character (that is not dead) 
	 * perform an action, and then checks if the player has won.
	 * ----------------------------------------------------------------------------------------------
	 * Why this order?
	 * If the enemy does get an extra coin this turn, allows it to use that new coin immediately.
	 * When the enemy places a character, it isn't automatically placed on the screen like the user, 
	 * instead, has to have the placeCharacter method place those characters. As soon as a character is
	 * placed on the screen, they can move. Thus, has to be done after the enemy (may) place a soldier.
	 * RemoveDeadCharacters must be after moveCharacters since moveCharacters includes attacking (and
	 * possibly killing). If this was before moveCharacters, their dead body will be there for another
	 * turn. Finally, hasWon has to be last as if it were before removeDeadCharacters(), there will be
	 * an extra frame to notice that everyone in the royal family has died.
	 */
	private void setGameTimeline(){
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>(){
			private int giveCoins = 1;
			
			@Override
			public void handle(ActionEvent e) {
				increaseCoins();
				((Enemy) enemy).makeMove(player);
				placeCharacters();
				moveCharacters();
				removeDeadCharacters();
				hasWon();
			}
			
			/*
			 * BP
			 * ------------------------------------------------------------------------------------
			 * Consists of two parts, removing the dead characters from the ArrayLists, and removing them
			 * from the screen. May have noticed that the for loop may be a bit different than normal.
			 * C++ has it, using a for each loop so that there is no need for the common for(int i = ...),
			 * instead can be shorter using its iterator.
			 */
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
			
			/*
			 * BP
			 * ------------------------------------------------------------------------------------
			 * Checks every single group of characters to use if they can be added onto the scene. If
			 * they are already on the scene, well... Ignore them.
			 */
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
			
			/*
			 * BP
			 * -------------------------------------------------------------------------------------
			 * After 15 frames, increases both players coins by 1, reflects the change on the coin text,
			 * and resets the count.
			 */
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
			
			/*
			 * BP
			 * ----------------------------------------------------------------------------------
			 * If every royal member has died for any player, immediate ends the game, removes any
			 * possible event and objects on the pane, and notifies the system that the game has ended 
			 * (through the isGamePlaying and by calling the gaveOverMenu).
			 */
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
		
		/*
		 * BP
		 * ---------------------------------------------------------------------------------------
		 * Duration.millis(...) sets up the delay for each frame. 1000/30 is every second = 30 frames,
		 * or 30 FPS. Increasing the rate increases the game speed... Which could blow up the game due
		 * to too many things happening.
		 * ---------------------------------------------------------------------------------------
		 * Don't want the game to randomly stop, so it runs indefinitely until it is called to stop.
		 */
		gameTimeline = new Timeline(new KeyFrame(Duration.millis(1000.0/30.0), event));
		gameTimeline.setCycleCount(Timeline.INDEFINITE);
		gameTimeline.setRate(settings.getSpeed());
	}
	
	/*
	 * BP
	 * ---------------------------------------------------------------------------------------
	 * The add characters is actually outside the event file only because characters will immediately need 
	 * to be added onto the pane either when clicking for a new game or to continue the game.
	 */
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
	
	/*
	 * BP
	 * ----------------------------------------------------------------------------------------------
	 */
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
			instructionsText[0].setText("The main goal of this game is to storm the enemy castle \n and defeat the royal family.\n\n"
					+ "To place down the soldier you want, select from the character menu (>>)\nand choose one of the shown locations.\n\n"
					+ "You have 3 soldier types: Swordsmen, Archer, and Tank\n\n"
					+ "Swordsmen are normal swordsmen that will run and attack \nenemies right in front of them\n\n"
					+ "Archers are ranged soldiers that attack from the rear\n\n");
			instructionsText[1].setFont(new Font(20));
			instructionsText[1].setText("Tanks are heavy soldiers and are there to take \ndamage and protect your Swordsmen and archers\n\n"
					+ "You gain gold over time.\n\n");
			
			
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
