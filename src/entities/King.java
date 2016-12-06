package entities;

import effects.Graphics;

public class King extends GameObject{//This is the creation of the King object JL
	private double standByLocation, minX, maxX;
	
	private static final double KING_DEFAULT_MAXHEALTH = 150;//This sets the max health of the King. JL
	
	public King(String banner, double xPosition, double yPosition, double standByLocation, //This sets the location for the health. JL
			double minX, double maxX){
		this(KING_DEFAULT_MAXHEALTH, banner, xPosition, yPosition, standByLocation, minX, maxX);
	}
	
	public King(double maxHealth, String banner, double xPosition, double yPosition, 
			double standByLocation, double minX, double maxX) {
		super(maxHealth, banner, Graphics.createKing("Idle", "Right", 0), xPosition, yPosition);
		this.standByLocation = standByLocation;
		this.minX = minX;
		this.maxX = maxX;
	}

	@Override
	public void onDeath() {//This function is for when the King dies
		death();
	}

	public void move(){
		if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){//This is the initially direction the King is moving. JL
			if(getX() - 1.25 >= minX){//This is set so if it hits the left wall it will switch directions. JL
				setX(getX() - 1.25);
				setImageLocation();
			}
			else{
				reverse();
				setX(getX() + 1.25);
				setImageLocation();
				return;
			}
		}
		else{
			if(getX() + 1.25 <= maxX){//This is set so if it hits the right wall it will switch directions. JL
				setX(getX() + 1.25);
				setImageLocation();
			}
			else{
				reverse();
				setX(getX() - 1.25);
				setImageLocation();
				return;
			}
		}
		
		if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){//These are for when the King is doing a walking animation for a specfic direction
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Walking")){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Left_1")){
					getCharacterPortrait().setImage(Graphics.createKing("Walking", "Left", 2));
				}
				else{
					getCharacterPortrait().setImage(Graphics.createKing("Walking", "Left", 1));
				}
			}
			else{
				getCharacterPortrait().setImage(Graphics.createKing("Walking", "Left", 1));
			}
		}
		else{
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Walking")){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Right_1")){
					getCharacterPortrait().setImage(Graphics.createKing("Walking", "Right", 2));
				}
				else{
					getCharacterPortrait().setImage(Graphics.createKing("Walking", "Right", 1));
				}
			}
			else{
				getCharacterPortrait().setImage(Graphics.createKing("Walking", "Right", 1));
			}
		}
	}
	
	public void reverse(){//This is what changes what direction the King faces. JL
		if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Walking")){
				getCharacterPortrait().setImage(Graphics.createKing("Walking", "Right", 1));
			}
			else{
				getCharacterPortrait().setImage(Graphics.createKing("Idle", "Right", 0));
			}
		}
		else{
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Walking")){
				getCharacterPortrait().setImage(Graphics.createKing("Walking", "Left", 1));
			}
			else{
				getCharacterPortrait().setImage(Graphics.createKing("Idle", "Left", 0));
			}
		}
	}
	
	public void retreat(){//This is to set it so that the King will get away from whoever is attacking her. JL
		if(getX() > standByLocation){//The standByLocation is where it will stay idle if there are enemies present. JL
			if(!getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
				getCharacterPortrait().setImage(Graphics.createKing("Walking", "Left", 1));
			}
			
			if(getX() - 1.25 > standByLocation){//This will make it so the King will stay in a certain location if x-2 is greater. JL
				setX(getX() - 1.25);
			}
			else{
				setX(standByLocation);
				getCharacterPortrait().setImage(Graphics.createKing("Idle", "Right", 0));
			}
		}
		else if(getX() < standByLocation){//This will make it so if X if greater the King will move places.
			if(!getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
				getCharacterPortrait().setImage(Graphics.createKing("Walking", "Right", 1));
			}
			
			if(getX() + 1.25 < standByLocation){//This will make the King move directions. JL
				setX(getX() + 1.25);
			}
			else{
				setX(standByLocation);//this makes the King face right. JL
				getCharacterPortrait().setImage(Graphics.createKing("Idle", "Left", 0));
			}
		}
		
		setImageLocation();
	}
	
}
