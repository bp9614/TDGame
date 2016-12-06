package entities;

import javafx.scene.image.ImageView;

public class Gate extends GameObject{
	
	private static final double DEFAULT_MAX_HEALTH = 1000;
	
	public Gate(String banner, double xPosition, double yPosition){
		super(DEFAULT_MAX_HEALTH, banner, null, xPosition, yPosition);
		
	}

	@Override
	public void onDeath() {
		death();
	}

	@Override
	public void move(){}

	@Override
	public void retreat(){}

	@Override
	public void reverse(){}
}
