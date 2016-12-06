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
		ImageView ferretImage = new ImageView(new Image("SecondaryBackground.png", 1200, 900, false, true));
		
		ferretImage.setX(0);
		ferretImage.setY(0);
		
		return ferretImage;
	}
	
	public static Image createPrincess(String stance, String direction, int animationNumber){
		Image princessImage = null;
		if(stance.equals("Idle")){
			princessImage = new Image("/PrincessPictures/PrincessIdle_" + direction + ".png", 23.6, 40, 
					false, true);
		}
		else{
			princessImage = new Image("/PrincessPictures/PrincessWalking_" 
					+ direction + "_" + animationNumber + ".png", 23.6, 40, false, true);
		}
		
		return princessImage;
	}
	
	public static Image createQueen(String direction){
		Image queenImage = new Image("/QueenPictures/Queen_" + direction + ".png", 28.4, 40, false, true);
		
		return queenImage;
	}
	
	public static Image createKing(String stance, String direction, int animationNumber){
		Image kingImage = null;
		if(stance.equals("Idle")){
			kingImage = new Image("/KingPictures/KingIdle_" + direction + ".png", 26.47, 40, 
					false, true);
		}
		else{
			kingImage = new Image("/KingPictures/KingWalking_" 
					+ direction + "_" + animationNumber + ".png", 26.47, 40, false, true);
		}
		
		return kingImage;
	}
	
	public static Image createPrince(String stance, String direction, int animationNumber){
		Image princeImage = null;
		if(stance.equals("Idle")){
			princeImage = new Image("/PrincePictures/PrinceIdle_" + direction + ".png", 34.85, 40, 
					false, true);
		}
		else{
			princeImage = new Image("/PrincePictures/PrinceWalking_" 
					+ direction + "_" + animationNumber + ".png", 34.85, 40, false, true);
		}
		
		return princeImage;
	}
	
	public static Image createWarrior(String stance, String direction, int animationNumber){
		Image warriorImage = null;
		if(stance.equals("Attacking")){
			warriorImage = new Image("/WarriorPictures/Warrior_Attacking_" + direction + "_" 
					+ animationNumber + ".png", 30, 40, false, true);
		}
		else {
			warriorImage = new Image("/WarriorPictures/BaseModel_" + stance + "_" + direction 
					+ ".png", 30, 40, false, true);
		}
		
		return warriorImage;
	}
	
	public static Image createTank(String stance, String direction, int animationNumber){
		Image tankImage = null;
		if(stance.equals("Attacking")){
			tankImage = new Image("/TankPictures/Knight_Attacking_" + direction + "_" + animationNumber + ".png", 
					30, 40, false, true);
		}
		else{
			tankImage = new Image("/TankPictures/Knight_" + stance + "_" + direction + ".png", 30, 40, false, true);
		}
		
		return tankImage;
	}
	
	public static Image createArcher(String stance, String direction, int animationNumber){
		Image archerImage = null;
		if(stance.equals("Attacking")){
			archerImage = new Image("/ArcherPictures/Archer_Attacking_" + direction + "_" 
					+ animationNumber + ".png", 30, 40, false, true);
		}
		else {
			archerImage = new Image("/ArcherPictures/BaseModel_" + stance + "_" + direction 
					+ ".png", 30, 40, false, true);
		}
		
		return archerImage;
	}
	
/*	
	public static Image createGate(){}
*/
}
