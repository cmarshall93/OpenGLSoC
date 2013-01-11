package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.WordGenerator;
import slug.soc.game.gameObjects.tiles.faction.TilePerson;

public class GameObjectPerson extends GameObject {

	private String firstName;
	private String lastName;
	private Integer troopNumber;
	
	public GameObjectPerson(Color color, Faction owner) {
		super(new TilePerson(color), owner);
		firstName =  WordGenerator.getInstance().getRandomMaleFirstName();
		lastName = owner.toString();
		troopNumber = 1;
	}

	@Override
	public String[] getStringDesc() {
		String[] desc = new String[2];
		desc[0] = firstName + " " + lastName;
		desc[1] = "Travels with a troop of " + troopNumber;
		return desc;
	}
	
	public String getName(){
		return firstName + " " + lastName;
	}
	
	public String toString(){
		return "Family Member";
	}

}
