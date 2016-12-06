package entities;

import java.util.ArrayList;

import application.PrimeLocations;
import effects.Graphics;

public class Tank extends Fighter{
	private double standByLocation;
	
	private static final double DEFAULT_MAX_HEALTH = 300;//This is the Tanks Max Health. JL
	private static final double DEFAULT_ATTACK_POWER = 40;//This is the Tanks attack power. JL
	private static final double DEFAULT_COOLDOWN_RATE = 6;//This is the cool down of how long before the tank can attack again. JL
	private static final double DEFAULT_ATTACK_RANGE = 15;//This is the range the Tank can attack from. JL
	
	public Tank(String banner, double xPosition, double yPosition, double standByLocation) {
		super(DEFAULT_MAX_HEALTH, DEFAULT_ATTACK_POWER, DEFAULT_COOLDOWN_RATE, DEFAULT_ATTACK_RANGE, 
				banner, Graphics.createTank("Idle", "Right", 0), xPosition, yPosition);
		this.standByLocation = standByLocation;
	}

	@Override
	public void onDeath() {//This tells when the take is dead. JL
		death();
		
	}

	@Override
	public void attack(GameObject toAttack) {//This function deals with when the Tank is attacking. JL
		setInAttackAnimation(true);//This sets the Tank into the attack animation.
		toAttack.onHit(getAttackPower());
		setCooldown(100);//This sets the Tanks attack cool down to 100. JL
	}

	@Override
	public void move(ArrayList<Fighter> enemiesOnPath) {
		if(getInAttackAnimation()){
			animation();
			return;
		}
		else{
			reduceCooldown();
		}
		
		for(Fighter enemy: enemiesOnPath){//This is to check for enemies who are in attack range. JL
			if(enemy.getY() == getY()){
				if(enemy.getX() - getX() < 0 && getCharacterPortrait().getImage().impl_getUrl().contains("Right")){//This tells the Tank to face right if there is a enemy to the right and he is facing left. JL
					reverse();
				}
				else if(enemy.getX() - getX() > 0 && getCharacterPortrait().getImage().impl_getUrl().contains("Left")){//This tells the Tank to face left if there is a enemy to the left and he is facing Right. JL
					reverse();
				}
				
				if(enemy.getX() - getX() <= getAttackRange() && enemy.getX() - getX() >= -getAttackRange()){//This tells the Tank if there is a enemy and the cool down is 0 then attack. JL
					if(getCooldown() == 0){
						attack(enemy);
					}
				}
				else if(enemy.getX() - getX() > getAttackRange() || enemy.getX() - getX() < -getAttackRange()){//If the enemy is not within attack range then move towards enemy. JL
					if(enemy.getX() > getX()){
						setX(getX() + .75);
						animation();
					}
					else{
						setX(getX() - .75);
						animation();
					}
				}
				setImageLocation();
				return;
			}
		}
		
		if(getBanner().equals("Player")){//This tells the Tank to move towards the doors to ascend to the top.
			if(getY() == PrimeLocations.FIRST_FLOOR_Y){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
					reverse();
				}
				
				if(getX() + .75 < PrimeLocations.ENEMY_RIGHT_DOOR_X){//If the Tank is not at the enemy door move towards the door. JL
					setX(getX() + .75);
				}
				else{
					setX(PrimeLocations.ENEMY_RIGHT_DOOR_X);//If the Tank is at the enemy door go into the door to the second floor. JL
					ascendStairs();
				}
			}
			else if(getY() == PrimeLocations.SECOND_FLOOR_Y){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
					reverse();
				}
				
				if(getX() - .75 > PrimeLocations.ENEMY_LEFT_DOOR_X){
					setX(getX() - .75);
				}
				else{
					setX(PrimeLocations.ENEMY_LEFT_DOOR_X);
					ascendStairs();
				}
			}
			else{
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
					reverse();
				}
				
				setX(getX() + .75);
			}
		}
		else{
			if(getY() == PrimeLocations.FIRST_FLOOR_Y){//If the enemy Tank is at your door it will walk towards the door and ascend the stairs onto the second floor. JL
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
					reverse();
				}
				
				if(getX() - .75 > PrimeLocations.PLAYER_LEFT_DOOR_X){
					setX(getX() - .75);
				}
				else{
					setX(PrimeLocations.PLAYER_LEFT_DOOR_X);
					ascendStairs();
				}
			}
			else if(getY() == PrimeLocations.SECOND_FLOOR_Y){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
					reverse();
				}
				
				if(getX() + .75 < PrimeLocations.PLAYER_RIGHT_DOOR_X){
					setX(getX() + .75);
				}
				else{
					setX(PrimeLocations.PLAYER_RIGHT_DOOR_X);
					ascendStairs();
				}
			}
			else{
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
					reverse();
				}
				
				setX(getX() - .75);
			}
		}
		
		animation();
		setImageLocation();
	}
	
	@Override
	public void move(ArrayList<Fighter> enemiesOnPath, ArrayList<GameObject> royality) {//This tells the tanks to move and attack on the third floor of the castle with the focus order being enemies then the royal family. JL
		if(getInAttackAnimation()){
			animation();
			return;
		}
		else{
			reduceCooldown();
		}
		
		for(Fighter enemy: enemiesOnPath){//This tells the Tanks to face a certain direction if he is not already. JL
			if(enemy.getX() - getX() < 0 && getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
				reverse();
			}
			else if(enemy.getX() - getX() > 0 && getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
				reverse();
			}
			
			if(enemy.getX() - getX() <= getAttackRange() && enemy.getX() - getX() >= -getAttackRange()){//Tells the Tank to attack if there is a enemy and cool down is 0. JL
				if(getCooldown() == 0){
					attack(enemy);
				}
			}
			else if(enemy.getX() - getX() > getAttackRange() || enemy.getX() - getX() < -getAttackRange()){//If enemy is not near move towards and attack enemy. JL
				if(enemy.getX() > getX()){
					setX(getX() + .75);
					animation();
				}
				else{
					setX(getX() - .75);
					animation();
				}
			}
			setImageLocation();
			return;
		}
		
		for(GameObject royalFamily: royality){//This is the same attack above except instead of attack enemy soldiers it is attack the royality. JL
			if(royalFamily.getX() - getX() < 0 && getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
				reverse();
			}
			else if(royalFamily.getX() - getX() > 0 && getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
				reverse();
			}
			
			if(royalFamily.getX() - getX() <= getAttackRange() && royalFamily.getX() - getX() >= -getAttackRange()){
				if(getCooldown() == 0){
					attack(royalFamily);
				}
			}
			else if(royalFamily.getX() - getX() > getAttackRange() || royalFamily.getX() - getX() < -getAttackRange()){
				if(royalFamily.getX() > getX()){
					setX(getX() + .75);
					animation();
				}
				else{
					setX(getX() - .75);
					animation();
				}
			}
			setImageLocation();
			return;
		}
		
		if(getBanner().equals("Player")){
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
				reverse();
			}
			
			setX(getX() + .75);
		}
		else{
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
				reverse();
			}
				
			setX(getX() - .75);
		}
		
		animation();
		setImageLocation();
	}
	
	@Override
	public void defend(ArrayList<Fighter> enemiesOnPath) {//This function tells the tank to attack the enemy if they are on the same floor. JL
		if(getInAttackAnimation()){
			animation();
			return;
		}
		else{
			reduceCooldown();
		}
		
		for(Fighter enemy: enemiesOnPath){
			if(enemy.getY() == getY()){//This tells the enemy to attack soldiers that are on the same floor as them. JL
				if(enemy.getX() < PrimeLocations.BATTLEFIELD_STARTING_X || enemy.getX() > PrimeLocations.BATTLEFIELD_ENDING_X){
					if(enemy.getX() - getX() < 0 && getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
						reverse();
					}
					else if(enemy.getX() - getX() > 0 && getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
						reverse();//If the enemy is in range but you are facing the wrong way, it reverse directions. JL
					}
					
					if(enemy.getX() - getX() <= getAttackRange() && enemy.getX() - getX() >= -getAttackRange()){//Attacks enemy if Tank is in range and cool down is 0. JL
						if(getCooldown() == 0){
							attack(enemy);
						}
					}
					else if(enemy.getX() - getX() > getAttackRange() || enemy.getX() - getX() < -getAttackRange()){ //if enemy isn't within range, move towards them. JL
						if(enemy.getX() > getX()){
							setX(getX() + .75);
							animation();
						}
						else{
							setX(getX() - .75);
							animation();
						}
					}
					setImageLocation();
					return;
				}
			}
		}
		
		retreat();
	}

	@Override
	public void retreat() {//Tells the tank that if there are no more enemies on the floor, then move back to the starting place. JL
		if(getX() != standByLocation){
			if(getX() > standByLocation){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
					reverse();
				}
				
				if(getX() - .75 > standByLocation){
					setX(getX() - .75);
				}
				else{
					setX(standByLocation);
					getCharacterPortrait().setImage((Graphics.createTank("Idle", "Right", 0)));
				}
			}
			else{
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
					reverse();
				}
				
				if(getX() + .75 < standByLocation){
					setX(getX() + .75);
				}
				else{
					setX(standByLocation);
					getCharacterPortrait().setImage((Graphics.createTank("Idle", "Left", 0)));
				}
			}
		}
		
		setImageLocation();
	}

	@Override
	public void reverse() {//This tells the Tanks to turn around when needed. JL
		if(getCharacterPortrait().getImage().impl_getUrl().contains("Idle")){
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
				getCharacterPortrait().setImage((Graphics.createTank("Idle", "Left", 0)));
			}
			else{
				getCharacterPortrait().setImage((Graphics.createTank("Idle", "Right", 0)));
			}
		}
		else if(getCharacterPortrait().getImage().impl_getUrl().contains("Walking")){
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
				getCharacterPortrait().setImage((Graphics.createTank("Walking", "Left", 0)));
			}
			else{
				getCharacterPortrait().setImage((Graphics.createTank("Walking", "Right", 0)));
			}
		}
		else{
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("_1.png")){
					getCharacterPortrait().setImage((Graphics.createTank("Attacking", "Left", 1)));
				}
				else if(getCharacterPortrait().getImage().impl_getUrl().contains("_2.png")){
					getCharacterPortrait().setImage((Graphics.createTank("Attacking", "Left", 2)));
				}
				else{
					getCharacterPortrait().setImage((Graphics.createTank("Attacking", "Left", 3)));
				}
			}
			else{
				if(getCharacterPortrait().getImage().impl_getUrl().contains("_1.png")){
					getCharacterPortrait().setImage((Graphics.createTank("Attacking", "Right", 1)));
				}
				else if(getCharacterPortrait().getImage().impl_getUrl().contains("_2.png")){
					getCharacterPortrait().setImage((Graphics.createTank("Attacking", "Right", 2)));
				}
				else{
					getCharacterPortrait().setImage((Graphics.createTank("Attacking", "Right", 3)));
				}
			}
		}
	}

	@Override
	public void ascendStairs() {
		if(getY() == PrimeLocations.FIRST_FLOOR_Y){
			setY(PrimeLocations.SECOND_FLOOR_Y);
		}
		else if(getY() == PrimeLocations.SECOND_FLOOR_Y){
			setY(PrimeLocations.THIRD_FLOOR_Y);
		}
	}

	@Override
	public void move(){}

	@Override
	public void animation() {
		if(getInAttackAnimation()){
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Idle") ||
					getCharacterPortrait().getImage().impl_getUrl().contains("Walking")){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
					getCharacterPortrait().setImage((Graphics.createTank("Attacking", "Right", 1)));
				}
				else{
					getCharacterPortrait().setImage((Graphics.createTank("Attacking", "Left", 1)));
				}
			}
			else if(getCharacterPortrait().getImage().impl_getUrl().contains("Attacking")){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("_1.png")){
					if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
						getCharacterPortrait().setImage((Graphics.createTank("Attacking", "Right", 2)));
					}
					else{
						getCharacterPortrait().setImage((Graphics.createTank("Attacking", "Left", 2)));
					}
				}
				else if(getCharacterPortrait().getImage().impl_getUrl().contains("_2.png")){
					if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
						getCharacterPortrait().setImage((Graphics.createTank("Attacking", "Right", 3)));
					}
					else{
						getCharacterPortrait().setImage((Graphics.createTank("Attacking", "Left", 3)));
					}
				}
				else{
					if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
						getCharacterPortrait().setImage((Graphics.createTank("Idle", "Right", 0)));
					}
					else{
						getCharacterPortrait().setImage((Graphics.createTank("Idle", "Left", 0)));
					}
					setInAttackAnimation(false);
				}
			}
		}
		else{
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Idle")){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
					getCharacterPortrait().setImage((Graphics.createTank("Walking", "Right", 0)));
				}
				else{
					getCharacterPortrait().setImage((Graphics.createTank("Walking", "Left", 0)));
				}
			}
			else if(getCharacterPortrait().getImage().impl_getUrl().contains("Walking")){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
					getCharacterPortrait().setImage((Graphics.createTank("Idle", "Right", 0)));
				}
				else{
					getCharacterPortrait().setImage((Graphics.createTank("Idle", "Left", 0)));
				}
			}
		}
	}
}