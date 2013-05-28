package slug.soc.game.gameObjects;

public class MovementOrder {

	private int distanceX;
	private int distanceY;

	private int distanceTravelled;

	private int maxDistance;
	private boolean travelledMaxDistance;

	private MovementOrderCoordinate firstCoord;

	public MovementOrder(int maxDistance){
		//distanceX = 0;
		//distanceY = 0;
		this.maxDistance = maxDistance;
		distanceTravelled = 0;
		firstCoord = new MovementOrderCoordinate(0,0,null);
	}

	public void moveX(int d){
		MovementOrderCoordinate lastCoord = getLastCoord();
		if(lastCoord.getX()*-1 == d){
			//if doubling back
			lastCoord.getPreviousCoord().setNextCoord(null);
			distanceTravelled--;
		}
		else{
			lastCoord.setNextCoord(new MovementOrderCoordinate(d,0,lastCoord));
			distanceTravelled++;
		}
	}

	public void moveY(int d){
		MovementOrderCoordinate lastCoord = getLastCoord();
		if(lastCoord.getY()*-1 == d){
			//if doubling back
			lastCoord.getPreviousCoord().setNextCoord(null);
			distanceTravelled--;
		}
		else{
			lastCoord.setNextCoord(new MovementOrderCoordinate(0,d,lastCoord));
			distanceTravelled++;
		}
	}

	public boolean canMove(int dx, int dy){
		if(dx != 0 && dy == 0){
			if(getLastCoord().getX() * -1 == dx){
				return true;
			}
			else{
				if(distanceTravelled + Math.abs(dx) <= maxDistance){
					return true;
				}
				else{
					return false;
				}
			}
		}
		else if(dy != 0 && dx == 0){
			if(getLastCoord().getY() * -1 == dy){
				return true;
			}
			else{
				if(distanceTravelled + Math.abs(dy) <= maxDistance){
					return true;
				}
				else{
					return false;
				}
			}
		}
		else{
			if(distanceTravelled + dy + dx <= maxDistance){
				return true;
			}
			else{
				return false;
			}
		}
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
