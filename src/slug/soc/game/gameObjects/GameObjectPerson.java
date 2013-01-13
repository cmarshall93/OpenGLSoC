package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.RandomProvider;
import slug.soc.game.WordGenerator;
import slug.soc.game.gameObjects.tiles.faction.TilePerson;

public class GameObjectPerson extends GameObject {

	private boolean isFemale;

	private String firstName;
	private String lastName;
	private Integer troopNumber;
	
	public GameObjectPerson(Color color, Faction owner) {
		super(new TilePerson(color), owner);
		firstName =  WordGenerator.getInstance().getRandomMaleFirstName();
		lastName = owner.toString();
		troopNumber = 1;
		if(RandomProvider.getInstance().nextInt(2) == 0){
			isFemale = true;
		}
		else{
			isFemale = false;
		}
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

	@Override
	public String getDetailedDesc() {
		String gender;
		if(isFemale){
			gender = "female";
		}
		else{
			gender = "male";
		}
		String out = firstName + " " + lastName + " is a " + gender;
		return out;
	}

}
