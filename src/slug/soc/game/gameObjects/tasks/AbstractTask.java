package slug.soc.game.gameObjects.tasks;

import slug.soc.game.gameObjects.GameObject;

public abstract class AbstractTask {

	protected GameObject target;
	protected GameObject owner;
	
	protected boolean isCompleted;
	
	public AbstractTask(GameObject owner , GameObject target){
		this.target = target;
		this.owner = owner;
		isCompleted = false;
	}
	
	public GameObject getTarget(){
		return target;
	}
	
	public boolean isCompleted(){
		return isCompleted;
	}
	
	public abstract void act();
}
