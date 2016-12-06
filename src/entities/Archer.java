package entities;

import java.util.ArrayList;

import application.PrimeLocations;
import effects.Graphics;

public class Archer extends Fighter{
	private double standByLocation;
	
	private static final double DEFAULT_MAX_HEALTH = 80;
	private static final double DEFAULT_ATTACK_POWER = 20;
	private static final double DEFAULT_COOLDOWN_RATE = 8;
	private static final double DEFAULT_ATTACK_RANGE = 80;
	
	public Archer(String banner, double xPostion, double yPosition, double standByLocation) {
		super(DEFAULT_MAX_HEALTH, DEFAULT_ATTACK_POWER, DEFAULT_COOLDOWN_RATE, DEFAULT_ATTACK_RANGE, 
				banner, Graphics.createArcher("Idle", "Right", 0), xPostion, yPosition);
		this.standByLocation = standByLocation;
	}

	@Override
	public void onDeath() {
		death();
	}

	@Override
	public void attack(GameObject toAttack) {
		setInAttackAnimation(true);
		toAttack.onHit(getAttackPower());
		setCooldown(100);
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
		
		for(Fighter enemy: enemiesOnPath){
			if(enemy.getY() == getY()){
				if(enemy.getX() - getX() < 0 && getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
					reverse();
				}
				else if(enemy.getX() - getX() > 0 && getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
					reverse();
				}
				
				if(enemy.getX() - getX() <= getAttackRange() && enemy.getX() - getX() >= -getAttackRange()){
					if(getCooldown() == 0){
						attack(enemy);
					}
				}
				else if(enemy.getX() - getX() > getAttackRange() || enemy.getX() - getX() < -getAttackRange()){
					if(enemy.getX() > getX()){
						setX(getX() + 2);
						animation();
					}
					else{
						setX(getX() - 2);
						animation();
					}
				}
				setImageLocation();
				return;
			}
		}
		
		if(getBanner().equals("Player")){
			if(getY() == PrimeLocations.FIRST_FLOOR_Y){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
					reverse();
				}
				
				if(getX() + 2 < PrimeLocations.ENEMY_RIGHT_DOOR_X){
					setX(getX() + 2);
				}
				else{
					setX(PrimeLocations.ENEMY_RIGHT_DOOR_X);
					ascendStairs();
				}
			}
			else if(getY() == PrimeLocations.SECOND_FLOOR_Y){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
					reverse();
				}
				
				if(getX() - 2 > PrimeLocations.ENEMY_LEFT_DOOR_X){
					setX(getX() - 2);
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
				
				setX(getX() + 2);
			}
		}
		else{
			if(getY() == PrimeLocations.FIRST_FLOOR_Y){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
					reverse();
				}
				
				if(getX() - 2 > PrimeLocations.PLAYER_LEFT_DOOR_X){
					setX(getX() - 2);
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
				
				if(getX() + 2 < PrimeLocations.PLAYER_RIGHT_DOOR_X){
					setX(getX() + 2);
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
				
				setX(getX() - 2);
			}
		}
		
		animation();
		setImageLocation();
	}
	
	@Override
	public void move(ArrayList<Fighter> enemiesOnPath, ArrayList<GameObject> royality) {
		if(getInAttackAnimation()){
			animation();
			return;
		}
		else{
			reduceCooldown();
		}
		
		for(Fighter enemy: enemiesOnPath){
			if(enemy.getX() - getX() < 0 && getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
				reverse();
			}
			else if(enemy.getX() - getX() > 0 && getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
				reverse();
			}
			
			if(enemy.getX() - getX() <= getAttackRange() && enemy.getX() - getX() >= -getAttackRange()){
				if(getCooldown() == 0){
					attack(enemy);
				}
			}
			else if(enemy.getX() - getX() > getAttackRange() || enemy.getX() - getX() < -getAttackRange()){
				if(enemy.getX() > getX()){
					setX(getX() + 2);
					animation();
				}
				else{
					setX(getX() - 2);
					animation();
				}
			}
			setImageLocation();
			return;
		}
		
		for(GameObject royalFamily: royality){
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
					setX(getX() + 2);
					animation();
				}
				else{
					setX(getX() - 2);
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
			
			setX(getX() + 2);
		}
		else{
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
				reverse();
			}
				
			setX(getX() - 2);
		}
		
		animation();
		setImageLocation();
	}
	
	@Override
	public void defend(ArrayList<Fighter> enemiesOnPath) {
		if(getInAttackAnimation()){
			animation();
			return;
		}
		else{
			reduceCooldown();
		}
		
		for(Fighter enemy: enemiesOnPath){
			if(enemy.getY() == getY()){
				if(enemy.getX() < PrimeLocations.BATTLEFIELD_STARTING_X || enemy.getX() > PrimeLocations.BATTLEFIELD_ENDING_X){
					if(enemy.getX() - getX() < 0 && getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
						reverse();
					}
					else if(enemy.getX() - getX() > 0 && getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
						reverse();
					}
					
					if(enemy.getX() - getX() <= getAttackRange() && enemy.getX() - getX() >= -getAttackRange()){
						if(getCooldown() == 0){
							attack(enemy);
						}
					}
					else if(enemy.getX() - getX() > getAttackRange() || enemy.getX() - getX() < -getAttackRange()){
						if(enemy.getX() > getX()){
							setX(getX() + 2);
							animation();
						}
						else{
							setX(getX() - 2);
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
	public void retreat() {
		if(getX() != standByLocation){
			if(getX() > standByLocation){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
					reverse();
				}
				
				if(getX() - 2 > standByLocation){
					setX(getX() - 2);
				}
				else{
					setX(standByLocation);
					getCharacterPortrait().setImage((Graphics.createArcher("Idle", "Right", 0)));
				}
			}
			else{
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Left")){
					reverse();
				}
				
				if(getX() + 2 < standByLocation){
					setX(getX() + 2);
				}
				else{
					setX(standByLocation);
					getCharacterPortrait().setImage((Graphics.createArcher("Idle", "Left", 0)));
				}
			}
		}
		
		setImageLocation();
	}

	@Override
	public void reverse() {
		if(getCharacterPortrait().getImage().impl_getUrl().contains("Idle")){
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
				getCharacterPortrait().setImage((Graphics.createArcher("Idle", "Left", 0)));
			}
			else{
				getCharacterPortrait().setImage((Graphics.createArcher("Idle", "Right", 0)));
			}
		}
		else if(getCharacterPortrait().getImage().impl_getUrl().contains("Walking")){
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
				getCharacterPortrait().setImage((Graphics.createArcher("Walking", "Left", 0)));
			}
			else{
				getCharacterPortrait().setImage((Graphics.createArcher("Walking", "Right", 0)));
			}
		}
		else{
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("_1.png")){
					getCharacterPortrait().setImage((Graphics.createArcher("Attacking", "Left", 1)));
				}
				else{
					getCharacterPortrait().setImage((Graphics.createArcher("Attacking", "Left", 2)));
				}
			}
			else{
				if(getCharacterPortrait().getImage().impl_getUrl().contains("_1.png")){
					getCharacterPortrait().setImage((Graphics.createArcher("Attacking", "Right", 1)));
				}
				else{
					getCharacterPortrait().setImage((Graphics.createArcher("Attacking", "Right", 2)));
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
					getCharacterPortrait().setImage((Graphics.createArcher("Attacking", "Right", 1)));
				}
				else{
					getCharacterPortrait().setImage((Graphics.createArcher("Attacking", "Left", 1)));
				}
			}
			else if(getCharacterPortrait().getImage().impl_getUrl().contains("Attacking")){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("_2.png")){
					if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
						getCharacterPortrait().setImage((Graphics.createArcher("Attacking", "Right", 2)));
					}
					else{
						getCharacterPortrait().setImage((Graphics.createArcher("Attacking", "Left", 2)));
					}
				}
				else{
					if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
						getCharacterPortrait().setImage((Graphics.createArcher("Idle", "Right", 0)));
					}
					else{
						getCharacterPortrait().setImage((Graphics.createArcher("Idle", "Left", 0)));
					}
					setInAttackAnimation(false);
				}
			}
		}
		else{
			if(getCharacterPortrait().getImage().impl_getUrl().contains("Idle")){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
					getCharacterPortrait().setImage((Graphics.createArcher("Walking", "Right", 0)));
				}
				else{
					getCharacterPortrait().setImage((Graphics.createArcher("Walking", "Left", 0)));
				}
			}
			else if(getCharacterPortrait().getImage().impl_getUrl().contains("Walking")){
				if(getCharacterPortrait().getImage().impl_getUrl().contains("Right")){
					getCharacterPortrait().setImage((Graphics.createArcher("Idle", "Right", 0)));
				}
				else{
					getCharacterPortrait().setImage((Graphics.createArcher("Idle", "Left", 0)));
				}
			}
		}
	}
}