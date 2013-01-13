package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.RandomProvider;
import slug.soc.game.WordGenerator;
import slug.soc.game.gameObjects.tiles.faction.TilePerson;

public class GameObjectPerson extends GameObject {

	private boolean isFemale;

	private String firstName;
	private String lastName;
	
	private String noseDesc;
	
	private String hairDesc;
	private String hairColour;
	
	private Integer troopNumber;
	
	public GameObjectPerson(Color color, Faction owner) {
		super(new TilePerson(color), owner);
		lastName = owner.toString();
		troopNumber = 1;
		if(RandomProvider.getInstance().nextInt(2) == 0){
			isFemale = true;
			firstName = WordGenerator.getInstance().getRandomFemaleFirstName();
		}
		else{
			isFemale = false;
			firstName =  WordGenerator.getInstance().getRandomMaleFirstName();
		}
		noseDesc = WordGenerator.getInstance().getRandomSize();
		hairDesc = WordGenerator.getInstance().getRandomLength();
		hairColour = WordGenerator.getInstance().getRandomHairColour();
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
		String secondPerson;
		if(isFemale){
			gender = "female";
			secondPerson = "She";
		}
		else{
			gender = "male";
			secondPerson = "He";
		}
		String out = firstName + " " + lastName + " is a " + gender + "." + secondPerson + " has a " + 
		noseDesc + " nose." + secondPerson + " has " + hairDesc + " " + hairColour + " hair.";
		return out;
	}

}
