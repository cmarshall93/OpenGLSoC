package slug.soc.game.gameObjects.interaction;

import slug.soc.game.GameCalendar;
import slug.soc.game.GameCalendarEvent;
import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.GameObjectPerson;

public class DuelInteraction extends AbstractInteraction {

	public DuelInteraction(GameObjectPerson owner) {
		super(owner);
	}

	@Override
	public void interact(GameObjectPerson other) {
		if(((GameObjectPerson) owner).getFightingSkill() > other.getFightingSkill()){
			other.kill();
			String out = owner.getName() + " fought and killed " + other.getName();
			GameCalendar.getInstance().getCurrentDate().addEvent(new GameCalendarEvent(out, owner));
		}
		else{
			((GameObjectPerson) owner).kill();
			String out = owner.getName() + " fought and was killed by " + other.getName();
			GameCalendar.getInstance().getCurrentDate().addEvent(new GameCalendarEvent(out,owner));
		}
	}
	
	public String toString(){
		return "Have a duel with ";
	}

}
