package slug.soc.game.gameObjects.interaction;

import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.GameObjectPerson;
import slug.soc.game.gameObjects.tasks.MoveTask;

public class MoveInteraction extends AbstractInteraction {
	
	public MoveInteraction(GameObject owner){
		super(owner);
	}
	
	//other interacts with owner
	//in this case other is given a task of traveling to owner
	@Override
	public void interact(GameObjectPerson other){
			other.setTask(new MoveTask(other,owner));
			other.getTask().act();
	}
	
	public String toString(){
		return "Move to";
	}
	
}
