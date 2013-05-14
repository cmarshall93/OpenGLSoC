package slug.soc.game.gameObjects;

public class MovementOrder {

	private int distanceX;
	private int distanceY;
	
	private int distanceTravelled;
	
	private int previousX;
	private int previousY;
	
	public MovementOrder(){
		distanceX = 0;
		distanceY = 0;
		distanceTravelled = 0;
	}
	
	public void moveX(int d){
		distanceX += d;
		distanceTravelled++;
	}
	
	public void moveY(int d){
		distanceY += d;
		distanceTravelled++;
	}
	
	public int getXDistance(){
		return distanceX;
	}
	
	public int getYDistance(){
		return distanceY;
	}
	
	public int getDistanceTravelled(){
		return distanceTravelled;
	}
	
}
