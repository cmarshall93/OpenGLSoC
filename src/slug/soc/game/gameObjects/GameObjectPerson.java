package slug.soc.game.gameObjects;

import java.awt.Color;
import java.util.ArrayList;

import slug.soc.game.GameCalendar;
import slug.soc.game.GameCalendarDate;
import slug.soc.game.GameCalendarEvent;
import slug.soc.game.GameCalendarMonth;
import slug.soc.game.RandomProvider;
import slug.soc.game.gameObjects.peopleFeatures.AbstractPersonFeature;
import slug.soc.game.gameObjects.peopleFeatures.BeardPersonFeature;
import slug.soc.game.gameObjects.peopleFeatures.BodyPersonFeature;
import slug.soc.game.gameObjects.peopleFeatures.EarPersonFeature;
import slug.soc.game.gameObjects.peopleFeatures.EyePersonFeature;
import slug.soc.game.gameObjects.peopleFeatures.HairPersonFeature;
import slug.soc.game.gameObjects.peopleFeatures.NosePersonFeature;
import slug.soc.game.gameObjects.tiles.faction.TilePerson;
import slug.soc.game.gameState.GameModeState;
import slug.soc.game.worldBuilding.WordGenerator;

public class GameObjectPerson extends GameObject {


	private GameObjectPerson mother;
	private GameObjectPerson father;
	private boolean isFemale;

	private String firstName;
	private String lastName;

	private ArrayList<AbstractPersonFeature> features;

	private Integer troopNumber;

	public GameObjectPerson(Color color, Faction owner, GameObjectPerson mother, GameObjectPerson father) {
		super(new TilePerson(color), owner);
		
		features = new ArrayList<AbstractPersonFeature>();
		
		lastName = owner.toString();
		troopNumber = 1;
		if(RandomProvider.getInstance().nextInt(2) == 0){
			isFemale = true;
			firstName = WordGenerator.getInstance().getRandomFemaleFirstName();
		}
		else{
			isFemale = false;
			firstName =  WordGenerator.getInstance().getRandomMaleFirstName(); 
			features.add(new BeardPersonFeature());
		}

		features.add(new NosePersonFeature());
		features.add(new HairPersonFeature());
		features.add(new EyePersonFeature());
		features.add(new BodyPersonFeature());
		features.add(new EarPersonFeature());
		
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
		
		dateCreated.addEvent(new GameCalendarEvent("The birth of " + getName() + ".", this));
		GameCalendar.getInstance().addKeyDate(dateCreated);
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

	public void act(){

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
		int currentYear = GameCalendar.getInstance().getCurrentYear();
		String out = firstName + " " + lastName + " is a " + gender + ". A member of the " + getOwner() + " family(i), "
				+ secondPerson.toLowerCase() + " was born on " + dateCreated.toString()  +" (b) and is " + (currentYear - dateCreated.getYear()) + " years old.  " + seconderPerson + " mother is " + motherString + " . " + seconderPerson + " father is " 
				+ fatherString + ". ";
		for(AbstractPersonFeature a : features){
			out += secondPerson + " has " + a.getDesc();
		}
		return out;
	}

}
