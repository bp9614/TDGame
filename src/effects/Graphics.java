package effects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;

public final class Graphics {
	public static final Background BLURRED_BACKGROUND = new Background(new BackgroundImage(
			new Image("blurred_castle.png", 1200, 900, false, true), null, null, null, null));
	public static final Background BACKGROUND = new Background(new BackgroundImage(
			new Image("castle.png", 1200, 900, false, true), null, null, null, null));
	public static final Background CANCLICK = new Background(new BackgroundImage(
			new Image("WithClickableAreas.png", 1200, 900, false, true), null, null, null, null));

	
	private Graphics(){}
	
	public static ImageView createCoin(double x, double y){
		ImageView coinImage = new ImageView(new Image("Coin.png", 17, 20, false, true));
		
		coinImage.setX(x);
		coinImage.setY(y);
		
		return coinImage;
	}
	
	public static ImageView createFerret(){
		ImageView ferretImage = new ImageView(new Image("blurred_castle.png", 1200, 900, false, true));
		
		ferretImage.setX(0);
		ferretImage.setY(0);
		
		return ferretImage;
	}
	
	public static Image createPrincess(String stance, String direction, int animationNumber){//This sets a image for the Princess
		Image princessImage = null;
		if(stance.equals("Idle")){
			princessImage = new Image("/PrincessPictures/PrincessIdle_" + direction + ".png", 23.6, 40, //This is the image of the Princess standing idle facing a direction. JL
					false, true);
		}
		else{
			princessImage = new Image("/PrincessPictures/PrincessWalking_" //This is the image of the Princess walking in a direction. JL
					+ direction + "_" + animationNumber + ".png", 23.6, 40, false, true);
		}
		
		return princessImage;
	}
	
	public static Image createQueen(String direction){//This is used to determine where the Queen will be placed and what direction she facing. JL
		Image queenImage = new Image("/QueenPictures/Queen_" + direction + ".png", 28.4, 40, false, true);
		
		return queenImage;
	}
	
	public static Image createKing(String stance, String direction, int animationNumber){//This sets a image for the King. JL
		Image kingImage = null;
		if(stance.equals("Idle")){
			kingImage = new Image("/KingPictures/KingIdle_" + direction + ".png", 26.47, 40, //This is the image of the King standing idle facing a direction. JL
					false, true);
		}
		else{
			kingImage = new Image("/KingPictures/KingWalking_" //This is the image of the King walking in a direction. JL
					+ direction + "_" + animationNumber + ".png", 26.47, 40, false, true);
		}
		
		return kingImage;
	}
	
	public static Image createPrince(String stance, String direction, int animationNumber){//This sets a image for the Prince. JL
		Image princeImage = null;
		if(stance.equals("Idle")){
			princeImage = new Image("/PrincePictures/PrinceIdle_" + direction + ".png", 34.85, 40, //This is the image of the Prince standing idle facing a direction. JL
					false, true);
		}
		else{
			princeImage = new Image("/PrincePictures/PrinceWalking_" //This is the image of the Prince walking in a direction. JL
					+ direction + "_" + animationNumber + ".png", 34.85, 40, false, true);
		}
		
		return princeImage;
	}
	
	public static Image createWarrior(String stance, String direction, int animationNumber){ //This sets the image for the warrior. JL
		Image warriorImage = null;
		if(stance.equals("Attacking")){//This sets a image for the attacking image for the warrior. JL
			warriorImage = new Image("/WarriorPictures/Warrior_Attacking_" + direction + "_" 
					+ animationNumber + ".png", 30, 40, false, true);
		}
		else {
			warriorImage = new Image("/WarriorPictures/BaseModel_" + stance + "_" + direction //This sets a image for the Warrior to stand idle or walking in a direction. JL
					+ ".png", 30, 40, false, true);
		}
		
		return warriorImage;
	}
	
	public static Image createTank(String stance, String direction, int animationNumber){//This sets a image for the Tank. JL
		Image tankImage = null;
		if(stance.equals("Attacking")){
			tankImage = new Image("/TankPictures/Knight_Attacking_" + direction + "_" + animationNumber + ".png", //This sets a image for the attacking image for the Tank. JL 
					30, 40, false, true);
		}
		else{
			tankImage = new Image("/TankPictures/Knight_" + stance + "_" + direction + ".png", 30, 40, false, true);//This sets a image for the Tank to stand idle or walking in a direction. JL
		}
		
		return tankImage;
	}
	
	public static Image createArcher(String stance, String direction, int animationNumber){ //This sets a image for the Archer. JL
		Image archerImage = null;
		if(stance.equals("Attacking")){
			archerImage = new Image("/ArcherPictures/Archer_Attacking_" + direction + "_" //This sets a image for the attacking image for the Archer. JL 
					+ animationNumber + ".png", 30, 40, false, true);
		}
		else {
			archerImage = new Image("/ArcherPictures/BaseModel_" + stance + "_" + direction //This sets a image for the Archer to stand idle or walking in a direction. JL
					+ ".png", 30, 40, false, true);
		}
		
		return archerImage;
	}
	
/*	
	public static Image createGate(){}
*/
}
