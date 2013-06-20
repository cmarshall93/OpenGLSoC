package slug.soc.game.gameObjects.interaction;

import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.GameObjectPerson;

public abstract class AbstractInteraction {

	//object that interaction is with
	protected GameObject owner;
	
	public AbstractInteraction(GameObject owner){
		this.owner = owner;
	}
	
	public abstract void interact(GameObjectPerson other);
}
