package entities;

import effects.Graphics;

public class Queen extends GameObject{
	private double standByLocation, minX, maxX;
	
	private static final double QUEEN_DEFAULT_MAXHEALTH = 120;
	
	public Queen(String banner, double xPosition, double yPosition, double standByLocation, 
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
	public void onDeath() {
		death();
	}
	
	public void move(){
		if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
			if(getX() - 2 >= minX){
				setX(getX() - 2);
			}
			else{
				reverse();
				setX(getX() + 2);
			}
		}
		else{
			if(getX() + 2 <= maxX){
				setX(getX() + 2);
			}
			else{
				reverse();
				setX(getX() - 2);
			}
		}
		
		setImageLocation();
	}
	
	public void reverse(){
		if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
			getCharacterPortrait().setImage(Graphics.createQueen("Right"));
		}
		else{
			getCharacterPortrait().setImage(Graphics.createQueen("Left"));
		}
	}
	
	public void retreat(){
		if(getX() > standByLocation){
			if(!getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
				getCharacterPortrait().setImage(Graphics.createQueen("Left"));
			}
			
			if(getX() - 2 > standByLocation){
				setX(getX() - 2);
			}
			else{
				setX(standByLocation);
				getCharacterPortrait().setImage(Graphics.createQueen("Right"));
			}
		}
		else if(getX() < standByLocation){
			if(!getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
				getCharacterPortrait().setImage(Graphics.createQueen("Right"));
			}
			
			if(getX() + 2 < standByLocation){
				setX(getX() + 2);
			}
			else{
				setX(standByLocation);
				getCharacterPortrait().setImage(Graphics.createQueen("Left"));
			}
		}
		
		setImageLocation();
	}
}
