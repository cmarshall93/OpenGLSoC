package slug.soc.game.gameObjects;

public class MovementOrderCoordinate {

	private int x;
	private int y;
	
	private MovementOrderCoordinate previousCoord;
	
	private MovementOrderCoordinate nextCoord;
	
	public MovementOrderCoordinate(int x, int y, MovementOrderCoordinate parent){
		this.x = x;
		this.y = y;
		this.previousCoord = parent;
		nextCoord = null;
	}
	
	public void setNextCoord(MovementOrderCoordinate nextCoord){
		this.nextCoord = nextCoord;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public MovementOrderCoordinate getNextCoord(){
		return nextCoord;
	}
	
	public MovementOrderCoordinate getPreviousCoord(){
		return previousCoord;
	}
}
