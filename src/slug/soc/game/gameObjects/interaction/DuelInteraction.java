package slug.soc.game.gameObjects.interaction;

import slug.soc.game.GameCalendar;
import slug.soc.game.GameCalendarEvent;
import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.GameObjectPerson;
import slug.soc.game.gameObjects.tasks.DuelTask;

public class DuelInteraction extends AbstractInteraction {

	public DuelInteraction(GameObjectPerson owner) {
		super(owner);
	}

	@Override
	public void interact(GameObjectPerson other) {
		((GameObjectPerson) other).setTask(new DuelTask(other, owner));
		other.getTask().act();
	}
	
	public String toString(){
		return "Have a duel with ";
	}

}
