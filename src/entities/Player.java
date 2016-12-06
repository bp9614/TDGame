package entities;

import java.util.ArrayList;

import application.PrimeLocations;

public class Player {
	private int numberOfCoins, totalNumberOfCoins;
	private ArrayList<Fighter> army_Invade, army_DefendFirstFloor, army_DefendSecondFloor, 
		army_DefendThirdFloor;
	private ArrayList<GameObject> royality, listOfDeadCharacters;
	private String banner;
	
	public Player(){
		numberOfCoins = 0;
		totalNumberOfCoins = 0;
		banner = "Player";
		
		army_Invade = new ArrayList<>();
		army_DefendFirstFloor = new ArrayList<>();
		army_DefendSecondFloor = new ArrayList<>();
		army_DefendThirdFloor = new ArrayList<>();
		royality = new ArrayList<>();
		listOfDeadCharacters = new ArrayList<>();
		
		royality.add(new King("Player", PrimeLocations.PLAYER_ROYALITY_DEFAULT_X, 
				PrimeLocations.THIRD_FLOOR_Y, PrimeLocations.PLAYER_ROYALITY_THIRD_FLOOR_STANDBYLOCATION_X, 
				PrimeLocations.PLAYER_ROYALITY_MIN_X, PrimeLocations.PLAYER_ROYALITY_MAX_X));
		royality.add(new Queen("Player", PrimeLocations.PLAYER_ROYALITY_DEFAULT_X, 
				PrimeLocations.THIRD_FLOOR_Y, PrimeLocations.PLAYER_ROYALITY_THIRD_FLOOR_STANDBYLOCATION_X, 
				PrimeLocations.PLAYER_ROYALITY_MIN_X, PrimeLocations.PLAYER_ROYALITY_MAX_X));
		royality.add(new Princess("Player", PrimeLocations.PLAYER_ROYALITY_DEFAULT_X, 
				PrimeLocations.THIRD_FLOOR_Y, PrimeLocations.PLAYER_ROYALITY_THIRD_FLOOR_STANDBYLOCATION_X, 
				PrimeLocations.PLAYER_ROYALITY_MIN_X, PrimeLocations.PLAYER_ROYALITY_MAX_X));
		royality.add(new Prince("Player", PrimeLocations.PLAYER_ROYALITY_DEFAULT_X, 
				PrimeLocations.THIRD_FLOOR_Y, PrimeLocations.PLAYER_ROYALITY_THIRD_FLOOR_STANDBYLOCATION_X, 
				PrimeLocations.PLAYER_ROYALITY_MIN_X, PrimeLocations.PLAYER_ROYALITY_MAX_X));
	}
	
	public Player(double defaultX, double defaultY, double standByLocation, double minX,  double maxX,
			String specialBanner){
		numberOfCoins = 0;
		totalNumberOfCoins = 0;
		banner = specialBanner;
		
		army_Invade = new ArrayList<>();
		army_DefendFirstFloor = new ArrayList<>();
		army_DefendSecondFloor = new ArrayList<>();
		army_DefendThirdFloor = new ArrayList<>();
		royality = new ArrayList<>();
		listOfDeadCharacters = new ArrayList<>();
		
		royality.add(new King(specialBanner, defaultX, defaultY, standByLocation, minX, maxX));
		royality.add(new Queen(specialBanner, defaultX, defaultY, standByLocation, minX, maxX));
		royality.add(new Princess(specialBanner, defaultX, defaultY, standByLocation, minX, maxX));
		royality.add(new Prince(specialBanner, defaultX, defaultY, standByLocation, minX, maxX));
	}
	
	public int getNumberOfCoins(){return numberOfCoins;}
	public int getNumberOfDeadSoldiers(){return listOfDeadCharacters.size();}
	public int getTotalNumberOfCoins(){return totalNumberOfCoins;}
	public ArrayList<GameObject> getRoyality(){return royality;}
	public ArrayList<GameObject> getListOfDeadCharacters(){return listOfDeadCharacters;}
	public ArrayList<Fighter> getInvadingArmy(){return army_Invade;}
	public ArrayList<Fighter> getFirstFloorDefendingArmy(){return army_DefendFirstFloor;}
	public ArrayList<Fighter> getSecondFloorDefendingArmy(){return army_DefendSecondFloor;}
	public ArrayList<Fighter> getThirdFloorDefendingArmy(){return army_DefendThirdFloor;}
	
	public void changeNumberOfCoins(int coins){
		numberOfCoins+=coins;
		if(coins > 0){
			increaseTotalNumberOfCoins(coins);
		}
	}
	
	private void increaseTotalNumberOfCoins(int coins){totalNumberOfCoins+=coins;}
	
	public void moveAllCharacters(Player player){
		for(int i = 0; i < royality.size(); i++){
			boolean shouldRetreat = false;
			for(int k = 0; k < player.getInvadingArmy().size(); k++){
				if(player.getInvadingArmy().get(k).getY() == PrimeLocations.THIRD_FLOOR_Y){
					shouldRetreat = true;
					break;
				}
			}
			
			if(shouldRetreat){
				royality.get(i).retreat();
			}
			else{
				royality.get(i).move();
			}
		}
		
		for(Fighter invader: army_Invade){
			if(invader.getY() == PrimeLocations.FIRST_FLOOR_Y){
				if(invader.getX() >= PrimeLocations.BATTLEFIELD_STARTING_X 
						&& invader.getX() <= PrimeLocations.BATTLEFIELD_ENDING_X){
					invader.move(player.getInvadingArmy());
				}
				else{
					invader.move(player.getFirstFloorDefendingArmy());
				}
			}
			else if(invader.getY() == PrimeLocations.SECOND_FLOOR_Y){
				invader.move(player.getSecondFloorDefendingArmy());
			}
			else{
				invader.move(player.getThirdFloorDefendingArmy(), player.getRoyality());
			}
		}
		
		for(Fighter defending: army_DefendFirstFloor){
			defending.defend(player.getInvadingArmy());
		}
		
		for(Fighter defending: army_DefendSecondFloor){
			defending.defend(player.getInvadingArmy());
		}
		
		for(Fighter defending: army_DefendThirdFloor){
			defending.defend(player.getInvadingArmy());
		}
	}
	
	public void removeDeadCharacters(){
		for(int a = army_Invade.size() - 1; a >= 0; a--){
			if(army_Invade.get(a).getIsDead()){
				listOfDeadCharacters.add(army_Invade.remove(a));
			}
		}
		
		for(int b = army_DefendFirstFloor.size() - 1; b >= 0; b--){
			if(army_DefendFirstFloor.get(b).getIsDead()){
				listOfDeadCharacters.add(army_DefendFirstFloor.remove(b));
			}
		}
		
		for(int c = army_DefendSecondFloor.size() - 1; c >= 0; c--){
			if(army_DefendSecondFloor.get(c).getIsDead()){
				listOfDeadCharacters.add(army_DefendSecondFloor.remove(c));
			}
		}
		
		for(int d = army_DefendThirdFloor.size() - 1; d >= 0; d--){
			if(army_DefendThirdFloor.get(d).getIsDead()){
				listOfDeadCharacters.add(army_DefendThirdFloor.remove(d));
			}
		}
		
		for(int e = royality.size() - 1; e >= 0; e--){
			if(royality.get(e).getIsDead()){
				listOfDeadCharacters.add(royality.remove(e));
			}
		}
	}
	
	public Archer addArcher(String location){
		Archer newArcher = null;
		if(location.equals("Third Floor")){
			if(banner.equals("Player")){
				newArcher = new Archer(banner, PrimeLocations.PLAYER_SOLDIER_THIRD_FLOOR_DEFAULT_X,
						PrimeLocations.THIRD_FLOOR_Y, PrimeLocations.PLAYER_SOLDIER_THIRD_FLOOR_DEFAULT_X);
			}
			else{
				newArcher = new Archer(banner, PrimeLocations.ENEMY_SOLDIER_THIRD_FLOOR_DEFAULT_X,
						PrimeLocations.THIRD_FLOOR_Y, PrimeLocations.ENEMY_SOLDIER_THIRD_FLOOR_DEFAULT_X);
			}
			army_DefendThirdFloor.add(newArcher);
		}
		else if(location.equals("Second Floor")){
			if(banner.equals("Player")){
				newArcher = new Archer(banner, PrimeLocations.PLAYER_SOLDIER_SECOND_FLOOR_DEFAULT_X,
						PrimeLocations.SECOND_FLOOR_Y, PrimeLocations.PLAYER_SOLDIER_SECOND_FLOOR_DEFAULT_X);
			}
			else{
				newArcher = new Archer(banner, PrimeLocations.ENEMY_SOLDIER_SECOND_FLOOR_DEFAULT_X,
						PrimeLocations.SECOND_FLOOR_Y, PrimeLocations.ENEMY_SOLDIER_SECOND_FLOOR_DEFAULT_X);
			}

			army_DefendSecondFloor.add(newArcher);
		}
		else if(location.equals("First Floor")){
			if(banner.equals("Player")){
				newArcher = new Archer(banner, PrimeLocations.PLAYER_SOLDIER_FIRST_FLOOR_DEFAULT_X,
						PrimeLocations.FIRST_FLOOR_Y, PrimeLocations.PLAYER_SOLDIER_FIRST_FLOOR_DEFAULT_X);
			}
			else{
				newArcher = new Archer(banner, PrimeLocations.ENEMY_SOLDIER_FIRST_FLOOR_DEFAULT_X,
						PrimeLocations.FIRST_FLOOR_Y, PrimeLocations.ENEMY_SOLDIER_FIRST_FLOOR_DEFAULT_X);
			}
			
			army_DefendFirstFloor.add(newArcher);
		}
		else{
			if(banner.equals("Player")){
				newArcher = new Archer(banner, PrimeLocations.PLAYER_SOLDIER_OUTSIDE_DEFAULT_X,
						PrimeLocations.FIRST_FLOOR_Y, PrimeLocations.PLAYER_SOLDIER_OUTSIDE_DEFAULT_X);
			}
			else{
				newArcher = new Archer(banner, PrimeLocations.ENEMY_SOLDIER_OUTSIDE_DEFAULT_X,
						PrimeLocations.FIRST_FLOOR_Y, PrimeLocations.ENEMY_SOLDIER_OUTSIDE_DEFAULT_X);
			}
			army_Invade.add(newArcher);
		}
		
		return newArcher;
	}
	
	public Warrior addWarrior(String location){
		Warrior newWarrior = null;
		if(location.equals("Third Floor")){
			if(banner.equals("Player")){
				newWarrior = new Warrior(banner, PrimeLocations.PLAYER_SOLDIER_THIRD_FLOOR_DEFAULT_X,
						PrimeLocations.THIRD_FLOOR_Y, PrimeLocations.PLAYER_SOLDIER_THIRD_FLOOR_DEFAULT_X);
			}
			else{
				newWarrior = new Warrior(banner, PrimeLocations.ENEMY_SOLDIER_THIRD_FLOOR_DEFAULT_X,
						PrimeLocations.THIRD_FLOOR_Y, PrimeLocations.ENEMY_SOLDIER_THIRD_FLOOR_DEFAULT_X);
			}
			army_DefendThirdFloor.add(newWarrior);
		}
		else if(location.equals("Second Floor")){
			if(banner.equals("Player")){
				newWarrior = new Warrior(banner, PrimeLocations.PLAYER_SOLDIER_SECOND_FLOOR_DEFAULT_X,
						PrimeLocations.SECOND_FLOOR_Y, PrimeLocations.PLAYER_SOLDIER_SECOND_FLOOR_DEFAULT_X);
			}
			else{
				newWarrior = new Warrior(banner, PrimeLocations.ENEMY_SOLDIER_SECOND_FLOOR_DEFAULT_X,
						PrimeLocations.SECOND_FLOOR_Y, PrimeLocations.ENEMY_SOLDIER_SECOND_FLOOR_DEFAULT_X);
			}

			army_DefendSecondFloor.add(newWarrior);
		}
		else if(location.equals("First Floor")){
			if(banner.equals("Player")){
				newWarrior = new Warrior(banner, PrimeLocations.PLAYER_SOLDIER_FIRST_FLOOR_DEFAULT_X,
						PrimeLocations.FIRST_FLOOR_Y, PrimeLocations.PLAYER_SOLDIER_FIRST_FLOOR_DEFAULT_X);
			}
			else{
				newWarrior = new Warrior(banner, PrimeLocations.ENEMY_SOLDIER_FIRST_FLOOR_DEFAULT_X,
						PrimeLocations.FIRST_FLOOR_Y, PrimeLocations.ENEMY_SOLDIER_FIRST_FLOOR_DEFAULT_X);
			}
			
			army_DefendFirstFloor.add(newWarrior);
		}
		else{
			if(banner.equals("Player")){
				newWarrior = new Warrior(banner, PrimeLocations.PLAYER_SOLDIER_OUTSIDE_DEFAULT_X,
						PrimeLocations.FIRST_FLOOR_Y, PrimeLocations.PLAYER_SOLDIER_OUTSIDE_DEFAULT_X);
			}
			else{
				newWarrior = new Warrior(banner, PrimeLocations.ENEMY_SOLDIER_OUTSIDE_DEFAULT_X,
						PrimeLocations.FIRST_FLOOR_Y, PrimeLocations.ENEMY_SOLDIER_OUTSIDE_DEFAULT_X);
			}
			army_Invade.add(newWarrior);
		}
		
		return newWarrior;
	}
	
	public Tank addTank(String location){
		Tank newTank = null;
		if(location.equals("Third Floor")){
			if(banner.equals("Player")){
				newTank = new Tank(banner, PrimeLocations.PLAYER_SOLDIER_THIRD_FLOOR_DEFAULT_X,
						PrimeLocations.THIRD_FLOOR_Y, PrimeLocations.PLAYER_SOLDIER_THIRD_FLOOR_DEFAULT_X);
			}
			else{
				newTank = new Tank(banner, PrimeLocations.ENEMY_SOLDIER_THIRD_FLOOR_DEFAULT_X,
						PrimeLocations.THIRD_FLOOR_Y, PrimeLocations.ENEMY_SOLDIER_THIRD_FLOOR_DEFAULT_X);
			}
			army_DefendThirdFloor.add(newTank);
		}
		else if(location.equals("Second Floor")){
			if(banner.equals("Player")){
				newTank = new Tank(banner, PrimeLocations.PLAYER_SOLDIER_SECOND_FLOOR_DEFAULT_X,
						PrimeLocations.SECOND_FLOOR_Y, PrimeLocations.PLAYER_SOLDIER_SECOND_FLOOR_DEFAULT_X);
			}
			else{
				newTank = new Tank(banner, PrimeLocations.ENEMY_SOLDIER_SECOND_FLOOR_DEFAULT_X,
						PrimeLocations.SECOND_FLOOR_Y, PrimeLocations.ENEMY_SOLDIER_SECOND_FLOOR_DEFAULT_X);
			}

			army_DefendSecondFloor.add(newTank);
		}
		else if(location.equals("First Floor")){
			if(banner.equals("Player")){
				newTank = new Tank(banner, PrimeLocations.PLAYER_SOLDIER_FIRST_FLOOR_DEFAULT_X,
						PrimeLocations.FIRST_FLOOR_Y, PrimeLocations.PLAYER_SOLDIER_FIRST_FLOOR_DEFAULT_X);
			}
			else{
				newTank = new Tank(banner, PrimeLocations.ENEMY_SOLDIER_FIRST_FLOOR_DEFAULT_X,
						PrimeLocations.FIRST_FLOOR_Y, PrimeLocations.ENEMY_SOLDIER_FIRST_FLOOR_DEFAULT_X);
			}
			
			army_DefendFirstFloor.add(newTank);
		}
		else{
			if(banner.equals("Player")){
				newTank = new Tank(banner, PrimeLocations.PLAYER_SOLDIER_OUTSIDE_DEFAULT_X,
						PrimeLocations.FIRST_FLOOR_Y, PrimeLocations.PLAYER_SOLDIER_OUTSIDE_DEFAULT_X);
			}
			else{
				newTank = new Tank(banner, PrimeLocations.ENEMY_SOLDIER_OUTSIDE_DEFAULT_X,
						PrimeLocations.FIRST_FLOOR_Y, PrimeLocations.ENEMY_SOLDIER_OUTSIDE_DEFAULT_X);
			}
			army_Invade.add(newTank);
		}
		
		return newTank;
	}
}