package entities;

import java.util.ArrayList;

import javafx.scene.image.Image;

public abstract class Fighter extends GameObject{
	private double attackPower, cooldown, cooldownRate, attackRange;
	private boolean inAttackAnimation;
	
	public Fighter(double maxHealth, double attackPower, double cooldownRate, 
			double attackRange, String banner, Image characterPortrait, double xPostion, double yPosition){
		super(maxHealth, banner, characterPortrait, xPostion, yPosition);
		this.attackPower = attackPower;
		this.cooldownRate = cooldownRate;
		this.attackRange = attackRange;
		cooldown = 0;
		inAttackAnimation = false;
	}
	
	public double getAttackPower(){return attackPower;}
	public double getCooldown(){return cooldown;}
	public double getCooldownRate(){return cooldownRate;}
	public double getAttackRange(){return attackRange;}
	public boolean getInAttackAnimation(){return inAttackAnimation;}
	
	public void reduceCooldown(){
		if(this.cooldown - this.cooldownRate >= 0){
			this.cooldown-=this.cooldownRate;
		}
		else{
			cooldown = 0;
		}
	}
	
	public void setInAttackAnimation(boolean inAttackAnimation){this.inAttackAnimation = inAttackAnimation;}
	public void setCooldown(double cooldown){this.cooldown = cooldown;};
	
	public abstract void attack(GameObject toAttack);
	public abstract void move(ArrayList<Fighter> enemiesOnPath);
	public abstract void move(ArrayList<Fighter> enemiesOnPath, ArrayList<GameObject> royality);
	public abstract void defend(ArrayList<Fighter> enemiesOnPath);
	public abstract void ascendStairs();
	public abstract void animation();
}
