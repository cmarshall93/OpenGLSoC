package slug.soc.game.gameObjects.tasks;

import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;

import slug.soc.game.Pathfinder;
import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.GameObjectPerson;
import slug.soc.game.gameObjects.MovementOrder;
import slug.soc.game.gameObjects.MovementOrderCoordinate;

public class MoveTask extends AbstractTask{

	private MovementOrder path;

	public MoveTask(GameObject owner, GameObject target) {
		super(owner, target);
		Pathfinder finder = new Pathfinder(10000,false);
		Path tmpPath = finder.findPath(owner.getX(), owner.getY(), target.getX(), target.getY());
		if(tmpPath != null){
			path = new MovementOrder(tmpPath.getLength());
			for(int i = 0; i < tmpPath.getLength(); i++){
				if(i != 0){
					path.getLastCoord().setNextCoord(
							new MovementOrderCoordinate(tmpPath.getStep(i).getX() - tmpPath.getStep(i - 1).getX()
									,tmpPath.getStep(i).getY() -  tmpPath.getStep(i - 1).getY()
									, path.getLastCoord()));
				}
				else{
					path.getLastCoord().setNextCoord(
							new MovementOrderCoordinate(tmpPath.getStep(i).getX() - owner.getX()
									,tmpPath.getStep(i).getY() - owner.getY()
									, path.getLastCoord()));
				}
			}
		}else{
			isCompleted = true;
		}
	}

	//give the owner a path to travel along in the following turn
	@Override
	public void act() {
//		owner.giveOrders(new MovementOrder(owner.MOVEMENT_DISTANCE));
//		for(int i = 0;i < owner.MOVEMENT_DISTANCE;i++){
//			MovementOrderCoordinate point = path.getFirstCoord();
//			path.removeFirst();
//			owner.getOrder().getLastCoord().setNextCoord(point);
//		}
		
		//crap to ensure that people stop moving, as atm the whole path if travelled in one go
		//people will always stop moving after one go
		owner.giveOrders(path);
		if(path.getLastCoord().getNextCoord() == null || path == null){
			isCompleted = true;
		}
		else{
			owner.giveOrders(path);
		}
	}

	public String getDesc(){
		return "travelling to " + target.getStringDesc()[0];
	}

}
