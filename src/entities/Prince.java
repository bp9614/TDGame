package entities;

import effects.Graphics;

public class Prince extends GameObject{ //the object in the game that's called Prince
	private double standByLocation, minX, maxX; //the prince's location, the minimum and the maximum position the prince can be 
	
	private static final double PRINCE_DEFAULT_MAXHEALTH = 80; //sets the prince to automatically be at this position everytime the game starts up

	public Prince(String banner, double xPosition, double yPosition, double standByLocation, 
			double minX, double maxX){
		this(PRINCE_DEFAULT_MAXHEALTH, banner, xPosition, yPosition, standByLocation, minX, maxX);
	}
	
	public Prince(double maxHealth, String banner, double xPosition, double yPosition, 
			double standByLocation, double minX, double maxX) {
		super(maxHealth, banner, Graphics.createPrince("Idle", "Right", 0), xPosition, yPosition);
		this.standByLocation = standByLocation;
		this.minX = minX;
		this.maxX = maxX;
	}

	@Override
	public void onDeath() {
		death();
	}
	
	public void move(){
		if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
			if(getX() - 5 >= minX){ //prince is moving to the left by 5 as long as the character doesn't hit the minimum point of the wall
				setX(getX() - 5);
				setImageLocation(); //puts the image of the prince at this position
			}
			else{
				reverse(); //if the prince is going past the minimum position, then reset the prince to the right by 5 
				setX(getX() + 5);
				setImageLocation();
				return;
			}
		}
		else{
			if(getX() + 5 <= maxX){ //prince is moving to the right by 5 as long as the character doesn't hit the maximum point of the wall (farthest it can go)
				setX(getX() + 5);
				setImageLocation();
			}
			else{
				reverse(); //if the prince is past the right maximum wall, move the prince to the left 5 instead rather than having the prince go out of boundaries
				setX(getX() - 5);
				setImageLocation();
				return;
			}
		}
		
		if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){ //displays the images of the prince that is walking left
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Walking")){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Left_1")){
					getCharacterPortrait().setImage(Graphics.createPrince("Walking", "Left", 2));
				}
				else{
					getCharacterPortrait().setImage(Graphics.createPrince("Walking", "Left", 1));
				}
			}
			else{
				getCharacterPortrait().setImage(Graphics.createPrince("Walking", "Left", 1));
			}
		}
		else{
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Walking")){ //displays the images of the prince walking right
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Right_1")){
					getCharacterPortrait().setImage(Graphics.createPrince("Walking", "Right", 2));
				}
				else{
					getCharacterPortrait().setImage(Graphics.createPrince("Walking", "Right", 1));
				}
			}
			else{
				getCharacterPortrait().setImage(Graphics.createPrince("Walking", "Right", 1));
			}
		}
	}
	
	public void reverse(){
		if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Walking")){
				getCharacterPortrait().setImage(Graphics.createPrince("Walking", "Right", 1));
			}
			else{
				getCharacterPortrait().setImage(Graphics.createPrince("Idle", "Right", 0));
			}
		}
		else{
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Walking")){
				getCharacterPortrait().setImage(Graphics.createPrince("Walking", "Left", 1));
			}
			else{
				getCharacterPortrait().setImage(Graphics.createPrince("Idle", "Left", 0));
			}
		}
	}
	
	public void retreat(){ //prince retreating by displaying the images 
		if(getX() > standByLocation){
			if(!getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
				getCharacterPortrait().setImage(Graphics.createPrince("Walking", "Left", 1));
			}
			
			if(getX() - 5 > standByLocation){
				setX(getX() - 5);
			}
			else{
				setX(standByLocation);
				getCharacterPortrait().setImage(Graphics.createPrince("Idle", "Right", 0));
			}
		}
		else if(getX() < standByLocation){
			if(!getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
				getCharacterPortrait().setImage(Graphics.createPrince("Walking", "Right", 1));
			}
			
			if(getX() + 5 < standByLocation){
				setX(getX() + 5);
			}
			else{
				setX(standByLocation);
				getCharacterPortrait().setImage(Graphics.createPrince("Idle", "Left", 0));
			}
		}
		
		setImageLocation();
	}
}
