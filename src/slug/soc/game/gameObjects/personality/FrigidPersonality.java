package slug.soc.game.gameObjects.personality;

import java.util.ArrayList;

import slug.soc.game.GameCalendar;
import slug.soc.game.GameCalendarEvent;
import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.GameObjectPerson;

public class FrigidPersonality extends AbstractPersonality {

	@Override
	public GameObjectPerson reactTryChild(GameObjectPerson person1,
			GameObjectPerson person2) {
		ArrayList<GameObject> tmp = new ArrayList<GameObject>();
		tmp.add(person1);
		tmp.add(person2);
		GameCalendar.getInstance().getCurrentDate().addEvent(new GameCalendarEvent("Attempted courting of " + person1.getName(),
			"Despite numerous attempts by " + person2.getName() + " to engage " + person1.getName()
			+" in the act of love-making." + person2.getName() + "was unsuccessful." ,tmp));
		return null;
	}
	
	public String toString(){
		return "scared of physical contact.";
	}


}
