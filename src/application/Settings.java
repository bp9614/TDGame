package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Settings {
	private int volume; //declaring volume as a number 
	private boolean isMute;
	private int speed;
	
	private static final int DEFAULT_VOLUME = 100;
	private static final boolean DEFAULT_ISMUTE = false;
	private static final int DEFAULT_SPEED = 1;
	
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
			this.volume = volume;
		}
		else if(volume < 0){
			this.volume = 0;
		}
		else{
			this.volume = 100;
		}
	}
	
	public void setSpeed(int speed){
		if(speed > 0){
			this.speed = speed;
		}
		else{
			this.speed = 1;
		}
	}
	
	
	public void increaseVolume(){
		if(volume < 100){
			volume++;
		}
	}
	
	public void decreaseVolume(){
		if(volume > 0){
			volume--;
		}
	}
	
	public void changeMute(){
		if(isMute){
			isMute = false;
		}
		else{
			isMute = true;
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
