package slug.soc.game.gameObjects;

public class MovementOrder {

	private int distanceX;
	private int distanceY;
	
	private int distanceTravelled;
	
	private MovementOrderCoordinate firstCoord;
	
	public MovementOrder(){
		//distanceX = 0;
		//distanceY = 0;
		distanceTravelled = 0;
		firstCoord = new MovementOrderCoordinate(0,0,null);
	}
	
	public void moveX(int d){
		MovementOrderCoordinate lastCoord = getLastCoord();
		lastCoord.setNextCoord(new MovementOrderCoordinate(lastCoord.getX() + d, lastCoord.getY() + 0,lastCoord));
		distanceX = getLastCoord().getX();
		distanceTravelled = Math.abs(getLastCoord().getX() + getLastCoord().getY());
	}
	
	public void moveY(int d){
		MovementOrderCoordinate lastCoord = getLastCoord();
		lastCoord.setNextCoord(new MovementOrderCoordinate(lastCoord.getX() + 0,lastCoord.getY() + d,lastCoord));
		distanceY = getLastCoord().getY();
		distanceTravelled = Math.abs(getLastCoord().getX() + getLastCoord().getY());
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
	
	public MovementOrderCoordinate getFirstCoord(){
		return firstCoord;
	}
	
	private MovementOrderCoordinate getLastCoord(){
		boolean looking = true;
		MovementOrderCoordinate coord = firstCoord;
		while(looking){
			MovementOrderCoordinate nextCoord = coord.getNextCoord();
			if(nextCoord == null){
				break;
			}
			coord = nextCoord;
		}
		return coord;
	}
}
