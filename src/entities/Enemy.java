package entities;

import application.PrimeLocations;

public class Enemy extends Player{
	private int nextDecision;
	
	public Enemy() {
		super(PrimeLocations.ENEMY_ROYALITY_DEFAULT_X, PrimeLocations.THIRD_FLOOR_Y, 
				PrimeLocations.ENEMY_ROYALITY_THIRDFLOOR_STANDBYLOCATION_X, PrimeLocations.ENEMY_ROYALITY_MIN_X, 
				PrimeLocations.ENEMY_ROYALITY_MAX_X, "Enemy");
		nextDecision = (int) (Math.random() * 3);
	}

	public void makeMove(Player otherPlayer){
		for(Fighter invaders: otherPlayer.getInvadingArmy()){
			if(invaders.getY() == PrimeLocations.THIRD_FLOOR_Y){
				if(getNumberOfCoins() >= 30){
					changeNumberOfCoins(-30);
					addTank("Third Floor");
				}
				return;
			}
		}
		
		for(Fighter invaders: otherPlayer.getInvadingArmy()){
			if(invaders.getY() == PrimeLocations.SECOND_FLOOR_Y){
				if(getNumberOfCoins() >= 30){
					changeNumberOfCoins(-30);
					addTank("Second Floor");
				}
				return;
			}
		}
		
		for(Fighter invaders: otherPlayer.getInvadingArmy()){
			if(invaders.getY() == PrimeLocations.FIRST_FLOOR_Y){
				if(getNumberOfCoins() >= 30){
					changeNumberOfCoins(-30);
					addTank("First Floor");
				}
				return;
			}
		}
		
		if(nextDecision == 0){
			if(getNumberOfCoins() >= 30){
				changeNumberOfCoins(-30);
				addTank("Outside");
				nextDecision = (int) (Math.random() * 3);
			}
		}
		else if(nextDecision == 1){
			if(getNumberOfCoins() >= 15){
				changeNumberOfCoins(-15);
				addWarrior("Outside");
				nextDecision = (int) (Math.random() * 3);
			}
		}
		else{
			if(getNumberOfCoins() >= 20){
				changeNumberOfCoins(-20);
				addArcher("Outside");
				nextDecision = (int) (Math.random() * 3);
			}
		}
		
	}
}
