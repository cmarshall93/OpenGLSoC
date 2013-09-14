package slug.soc.game.gameObjects.personality;

import slug.soc.game.gameObjects.GameObjectPerson;

public class ControlPersonality extends AbstractPersonality {

	@Override
	public GameObjectPerson reactTryChild(GameObjectPerson person1,
			GameObjectPerson person2) {
		if(person1.isFemale() && !person2.isFemale()){
			GameObjectPerson child = new GameObjectPerson(person1.getOwner().getFactionColor().getColor(), person1.getOwner(), person1, person2, person1.getX(), person1.getY());
			return child;
		}
		else if(person2.isFemale() && !person1.isFemale()){
			GameObjectPerson child = new GameObjectPerson(person1.getOwner().getFactionColor().getColor(), person1.getOwner(), person1, person2, person1.getX(), person1.getY());
			return child;
		}
		return null;
	}

	public String toString(){
		return "a well rounded person.";
	}
	
	
}
