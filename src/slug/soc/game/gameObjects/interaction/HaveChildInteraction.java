package slug.soc.game.gameObjects.interaction;

import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.GameObjectPerson;
import slug.soc.game.gameState.GameModeState;

//maily for testing
public class HaveChildInteraction extends AbstractInteraction {

	public HaveChildInteraction(GameObject owner) {
		super(owner);
	}

	@Override
	public void interact(GameObjectPerson other) {
		GameModeState.getInstance().addNewFactionObject(other.getX(), other.getY(),other.haveChild(other, (GameObjectPerson)owner));
	}
	
	public String toString(){
		return "Have child with";
	}

}
