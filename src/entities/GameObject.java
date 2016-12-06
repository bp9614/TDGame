package entities;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class GameObject implements Interactable{
	private double health, maxHealth, xPosition, yPosition;
	private ImageView characterPortrait;
	private boolean isDead;
	private String banner;
	
	public GameObject(double maxHealth, String banner, Image characterPortrait, double xPosition,
			double yPosition){
		this.health = maxHealth;
		this.maxHealth = maxHealth;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.banner = banner;
		this.characterPortrait = new ImageView(characterPortrait);
		this.isDead = false;
		
		setImageLocation();
	}
	
	public double getHealth(){return health;}
	public double getMaxHealth(){return maxHealth;}
	public double getX(){return xPosition;}
	public double getY(){return yPosition;}
	public String getBanner(){return banner;}
	public ImageView getCharacterPortrait(){return characterPortrait;}
	
	public void setX(double xPosition){this.xPosition = xPosition;}
	public void setY(double yPosition){this.yPosition = yPosition;}
	
	public boolean getIsDead(){return isDead;}
	public void death(){isDead = true;}
	public void revive(){isDead = false;}
	
	
	public void increaseHealth(double moreHealth){
		if(health + moreHealth <= maxHealth){
			health+=moreHealth;
		}
		else{
			health = maxHealth;
		}
	}
	
	public void onHit(double damage){
		if(health - damage >= 0){
			health-=damage;
		}
		else{
			health = 0;
			onDeath();
		}
	}
	
	public void setImageLocation(){
		characterPortrait.setX(xPosition);
		characterPortrait.setY(yPosition);
	}
	
	public abstract void move();
	public abstract void reverse();
	public abstract void retreat();
}
