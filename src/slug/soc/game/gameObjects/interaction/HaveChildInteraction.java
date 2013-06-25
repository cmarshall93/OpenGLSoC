package slug.soc.game.gameObjects.interaction;

import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.GameObjectPerson;
import slug.soc.game.gameObjects.tasks.HaveChildTask;
import slug.soc.game.gameState.GameModeState;

//maily for testing
public class HaveChildInteraction extends AbstractInteraction {

	public HaveChildInteraction(GameObject owner) {
		super(owner);
	}

	@Override
	public void interact(GameObjectPerson other) {
		other.setTask(new HaveChildTask(other, owner));
		other.getTask().act();
	}

	public String toString(){
		return "Try for child with";
	}

}
