package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.RandomProvider;
import slug.soc.game.WordGenerator;
import slug.soc.game.gameObjects.tiles.faction.TilePerson;

public class GameObjectPerson extends GameObject {


	private GameObjectPerson mother;
	private GameObjectPerson father;
	private boolean isFemale;

	private String firstName;
	private String lastName;

	private int age;

	private String noseDesc;

	private String hairDesc;
	private String hairColour;

	private String eyeColour;
	private String eyeDesc;

	private Integer troopNumber;

	public GameObjectPerson(Color color, Faction owner, GameObjectPerson mother, GameObjectPerson father) {
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
		eyeColour = WordGenerator.getInstance().getRandomEyeColour();
		eyeDesc = WordGenerator.getInstance().getRandomSize();
		age = RandomProvider.getInstance().nextInt(60) + 17;
		if(mother != null){
			if(mother.isFemale()){
				this.mother = mother;
			}else{
				mother = null;
			}
		}
		else{mother = null;}
		if(father != null){
			if(!father.isFemale()){
				this.father = father;
			}else{
				this.father = father;
			}
		}
		else{father = null;}
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

	public void setIsFemale(boolean isFemale){
		this.isFemale = isFemale;
	}
	
	public boolean isFemale(){
		return isFemale;
	}
	
	public GameObjectPerson haveChild(GameObjectPerson person1, GameObjectPerson person2){
		if(person1.isFemale() && !person2.isFemale()){
			return new GameObjectPerson(owner.getFactionColor().getColor(), owner, person1, person2);
		}
		else if(person2.isFemale() && !person1.isFemale()){
			return new GameObjectPerson(owner.getFactionColor().getColor(), owner, person2, person1);
		}
		else return null;
	}

	@Override
	public String getDetailedDesc() {
		String gender;
		String secondPerson;
		String seconderPerson;
		if(isFemale){
			gender = "female";
			secondPerson = "She";
			seconderPerson = "Her";
		}
		else{
			gender = "male";
			secondPerson = "He";
			seconderPerson = "His";
		}

		String motherString;
		String fatherString;
		if(mother == null){
			motherString = "unkown";
		}else{
			motherString = mother.getName();
		}
		if(father == null){
			fatherString = "unknown";
		}else{
			fatherString = father.getName();
		}
		String out = firstName + " " + lastName + " is a " + gender + ". A member of the " + getOwner() + " family(i), "
				+ secondPerson.toLowerCase() +" is " + age + " years old.  " + seconderPerson + " mother is " + motherString + " . " + seconderPerson + " father is " 
				+ fatherString + ". " + secondPerson + " has a " + noseDesc + " nose." + secondPerson + " has " + hairDesc + " " + hairColour + " hair. "
				+ secondPerson + " has " + eyeDesc + " " + eyeColour + " eyes.";
		return out;
	}

}
