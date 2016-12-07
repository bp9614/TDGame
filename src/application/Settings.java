package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Settings {
    private int speed; //speed is an integer because speed is controlled by a number like 
	private int volume; //declaring volume as a number 
	private boolean isMute; //is mute is a boolean because it can only be a true or a false, not a number 1x
	
	private static final int DEFAULT_VOLUME = 100; //volume is set to 100 as a default in case the user didn't know there is music with the game
	private static final boolean DEFAULT_ISMUTE = false; //is mute is set to false because the volume is defaulted to 100 so the music cannot be mute 
	private static final int DEFAULT_SPEED = 1; //setting the default speed to 1 so the game will go slowly enough for the user to see 
	
	public Settings(){
		this(DEFAULT_VOLUME, DEFAULT_ISMUTE, DEFAULT_SPEED);
	}
	
	public Settings(int volume, boolean isMute, int speed){ 
		this.volume = volume;
		this.isMute = isMute;
		this.speed = speed;
	}
	
	public int getVolume(){return volume;}
	public boolean isMute(){return isMute;}
	public int getSpeed(){return speed;}
	
	public void setVolume(int volume){
		if(volume >= 0 && volume <= 100){
			this.volume = volume; //if the user chose a volume number between 0 and 100, then set the volume to that number
		}
		else if(volume < 0){
			this.volume = 0; //if the user tried to change the volume number to less than 0, then automatically set the volume to 0 
		} 
		else{
			this.volume = 100; //default volume is 100 
		}
	}
	
	public void setSpeed(int speed){
		if(speed > 0){
			this.speed = speed; //if the user chooses a speed from the options, then set the speed to the chosen option
		}
		else{
			this.speed = 1; //set the default speed to 1 
		}
	}
	
	
	public void increaseVolume(){
		if(volume < 100){
			volume++; //allow the user to only decrease the volume number, users can't go past a volume of 100 which is the maximum
		}
	}
	
	public void decreaseVolume(){
		if(volume > 0){
			volume--; //only decrease the number until the volume reaches to 0 
		}
	}
	
	public void changeMute(){
		if(isMute){
			isMute = false; //can set to false if the user chooses the option of mute
		}
		else{
			isMute = true; //if the volume isn't muted, then it is automatically set to mute
		}
	}
	
	public void loadSettings(){
		try {
			Scanner settingsFile = new Scanner(new File("Settings.txt"));
			volume = settingsFile.nextInt();
			isMute = settingsFile.nextBoolean();
			speed = settingsFile.nextInt();
			settingsFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("No default settings.");
			try {
				PrintWriter newSettings = new PrintWriter(new FileWriter(new File("Settings.txt")));
				newSettings.println(volume + "\n" + isMute + "\n" + speed);
				newSettings.close();
			} catch (IOException e1) {
				System.out.println("Something went wrong.");
			}
		}
	}
	
	public void saveSettings(){
		try {
			PrintWriter newSettings = new PrintWriter(new FileWriter(new File("Settings.txt")));
			newSettings.println(volume + "\n" + isMute + "\n" + speed);
			newSettings.close();
		} catch (IOException e1) {
			System.out.println("Something went wrong.");
		}
	}
	
	@Override
	public String toString(){
		return "Volume: " + volume + ", Mute: " + isMute + ", Speed: " + speed;
	}
}