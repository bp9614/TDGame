package entities;

import effects.Graphics;

public class King extends GameObject{
	private double standByLocation, minX, maxX;
	
	private static final double KING_DEFAULT_MAXHEALTH = 150;
	
	public King(String banner, double xPosition, double yPosition, double standByLocation, 
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
	public void onDeath() {
		death();
	}

	public void move(){
		if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
			if(getX() - 1.25 >= minX){
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
			if(getX() + 1.25 <= maxX){
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
		
		if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
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
	
	public void reverse(){
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
	
	public void retreat(){
		if(getX() > standByLocation){
			if(!getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
				getCharacterPortrait().setImage(Graphics.createKing("Walking", "Left", 1));
			}
			
			if(getX() - 1.25 > standByLocation){
				setX(getX() - 1.25);
			}
			else{
				setX(standByLocation);
				getCharacterPortrait().setImage(Graphics.createKing("Idle", "Right", 0));
			}
		}
		else if(getX() < standByLocation){
			if(!getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
				getCharacterPortrait().setImage(Graphics.createKing("Walking", "Right", 1));
			}
			
			if(getX() + 1.25 < standByLocation){
				setX(getX() + 1.25);
			}
			else{
				setX(standByLocation);
				getCharacterPortrait().setImage(Graphics.createKing("Idle", "Left", 0));
			}
		}
		
		setImageLocation();
	}
	
}
