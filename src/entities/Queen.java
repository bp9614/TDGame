package entities;

import effects.Graphics;

public class Queen extends GameObject{//This is the creation of the Queen object JL
	private double standByLocation, minX, maxX;
	
	private static final double QUEEN_DEFAULT_MAXHEALTH = 120;//This sets the max health of the Queen. JL
	
	public Queen(String banner, double xPosition, double yPosition, double standByLocation, //This sets the location for the health. JL
			double minX, double maxX){
		this(QUEEN_DEFAULT_MAXHEALTH, banner, xPosition, yPosition, standByLocation, minX, maxX);
	}
	
	public Queen(double maxHealth, String banner, double xPosition, double yPosition, 
			double standByLocation, double minX, double maxX) {
		super(maxHealth, banner, Graphics.createQueen("Right"), xPosition, yPosition);
		this.standByLocation = standByLocation;
		this.minX = minX;
		this.maxX = maxX;
	}

	@Override
	public void onDeath() {//This function is for when the Queen dies
		death();
	}
	
	public void move(){
		if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){//This is the initially direction the Queen is moving. JL
			if(getX() - 2 >= minX){//This is set so if it hits the left wall it will switch directions. JL
				setX(getX() - 2);
			}
			else{
				reverse();
				setX(getX() + 2);
			}
		}
		else{
			if(getX() + 2 <= maxX){//This is set so if it hits the right wall it will switch directions. JL
				setX(getX() + 2);
			}
			else{
				reverse();
				setX(getX() - 2);
			}
		}
		
		setImageLocation();
	}
	
	public void reverse(){//This is what changes what direction the Queen faces. JL
		if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
			getCharacterPortrait().setImage(Graphics.createQueen("Right"));
		}
		else{
			getCharacterPortrait().setImage(Graphics.createQueen("Left"));
		}
	}
	
	public void retreat(){//This is to set it so that the Queen will get away from whoever is attacking her. JL
		if(getX() > standByLocation){//The standByLocation is where it will stay idle if there are enemies present. JL
			if(!getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
				getCharacterPortrait().setImage(Graphics.createQueen("Left"));
			}
			
			if(getX() - 2 > standByLocation){//This will make it so the Queen will stay in a certain location if x-2 is greater. JL
				setX(getX() - 2);
			}
			else{
				setX(standByLocation);
				getCharacterPortrait().setImage(Graphics.createQueen("Right"));
			}
		}
		else if(getX() < standByLocation){//This will make it so if X if greater the Queen will move places.
			if(!getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
				getCharacterPortrait().setImage(Graphics.createQueen("Right"));
			}
			
			if(getX() + 2 < standByLocation){//This will make the Queen move directions. JL
				setX(getX() + 2);
			}
			else{
				setX(standByLocation);
				getCharacterPortrait().setImage(Graphics.createQueen("Left"));//this makes the Queen face right. JL
			}
		}
		
		setImageLocation();
	}
}
