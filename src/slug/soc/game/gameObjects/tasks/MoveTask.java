package slug.soc.game.gameObjects.tasks;

import slug.soc.game.Pathfinder;
import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.GameObjectPerson;
import slug.soc.game.gameObjects.MovementOrder;
import slug.soc.game.gameObjects.MovementOrderCoordinate;

public class MoveTask extends AbstractTask{

	private MovementOrder path;
	
	public MoveTask(GameObject owner, GameObject target) {
		super(owner, target);
		path = Pathfinder.getInstance().generatePath(owner, target);
	}

	@Override
	public void act() {
		owner.giveOrders(new MovementOrder(owner.MOVEMENT_DISTANCE));
		for(int i = 0;i < owner.MOVEMENT_DISTANCE;i++){
			MovementOrderCoordinate point = path.getFirstCoord();
			path.removeFirst();
			owner.getOrder().getLastCoord().setNextCoord(point);
		}
	}

}
