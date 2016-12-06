package entities;

import effects.Graphics;

public class Princess extends GameObject{
	private double standByLocation, minX, maxX;
	
	private static final double PRINCESS_DEFAULT_MAXHEALTH = 100;
	
	public Princess(String banner, double xPosition, double yPosition, double standByLocation, 
			double minX, double maxX){
		this(PRINCESS_DEFAULT_MAXHEALTH, banner, xPosition, yPosition, standByLocation, minX, maxX);
	}
	
	public Princess(double maxHealth, String banner, double xPosition, double yPosition, 
			double standByLocation, double minX, double maxX) {
		super(maxHealth, banner, Graphics.createPrincess("Idle", "Right", 0), xPosition, yPosition);
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
			if(getX() - 3 >= minX){
				setX(getX() - 3);
				setImageLocation();
			}
			else{
				reverse();
				setX(getX() + 3);
				setImageLocation();
				return;
			}
		}
		else{
			if(getX() + 3 <= maxX){
				setX(getX() + 3);
				setImageLocation();
			}
			else{
				reverse();
				setX(getX() - 3);
				setImageLocation();
				return;
			}
		}
		
		if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Walking")){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Left_1")){
					getCharacterPortrait().setImage(Graphics.createPrincess("Walking", "Left", 2));
				}
				else{
					getCharacterPortrait().setImage(Graphics.createPrincess("Walking", "Left", 1));
				}
			}
			else{
				getCharacterPortrait().setImage(Graphics.createPrincess("Walking", "Left", 1));
			}
		}
		else{
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Walking")){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Right_1")){
					getCharacterPortrait().setImage(Graphics.createPrincess("Walking", "Right", 2));
				}
				else{
					getCharacterPortrait().setImage(Graphics.createPrincess("Walking", "Right", 1));
				}
			}
			else{
				getCharacterPortrait().setImage(Graphics.createPrincess("Walking", "Right", 1));
			}
		}
	}
	
	public void reverse(){
		if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Walking")){
				getCharacterPortrait().setImage(Graphics.createPrincess("Walking", "Right", 1));
			}
			else{
				getCharacterPortrait().setImage(Graphics.createPrincess("Idle", "Right", 0));
			}
		}
		else{
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Walking")){
				getCharacterPortrait().setImage(Graphics.createPrincess("Walking", "Left", 1));
			}
			else{
				getCharacterPortrait().setImage(Graphics.createPrincess("Idle", "Left", 0));
			}
		}
	}
	
	public void retreat(){
		if(getX() > standByLocation){
			if(!getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
				getCharacterPortrait().setImage(Graphics.createPrincess("Walking", "Left", 1));
			}
			
			if(getX() - 3 > standByLocation){
				setX(getX() - 3);
			}
			else{
				setX(standByLocation);
				getCharacterPortrait().setImage(Graphics.createPrincess("Idle", "Right", 0));
			}
		}
		else if(getX() < standByLocation){
			if(!getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
				getCharacterPortrait().setImage(Graphics.createPrincess("Walking", "Right", 1));
			}
			
			if(getX() + 3 < standByLocation){
				setX(getX() + 3);
			}
			else{
				setX(standByLocation);
				getCharacterPortrait().setImage(Graphics.createPrincess("Idle", "Left", 0));
			}
		}
		
		setImageLocation();
	}
}
