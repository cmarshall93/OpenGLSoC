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
import slug.soc.game.gameObjects.peopleFeatures.MouthPersonFeature;
import slug.soc.game.gameObjects.peopleFeatures.NosePersonFeature;
import slug.soc.game.gameObjects.peopleFeatures.PersonFaceFeatureSet;
import slug.soc.game.gameObjects.tasks.AbstractTask;
import slug.soc.game.gameObjects.tiles.faction.TilePerson;
import slug.soc.game.gameState.GameModeState;
import slug.soc.game.worldBuilding.WordGenerator;

public class GameObjectPerson extends GameObject {

	public static final int MOVEMENT_DISTANCE = 5;

	private GameObjectPerson mother;
	private GameObjectPerson father;
	private boolean isFemale;

	private String firstName;
	private String lastName;

	private ArrayList<AbstractPersonFeature> bodyFeatures;
	private PersonFaceFeatureSet faceFeatures;

	private Integer troopNumber;

	private AbstractTask task;
	private boolean hasTask;

	public GameObjectPerson(Color color, Faction owner, GameObjectPerson mother, GameObjectPerson father, int x, int y) {
		super(new TilePerson(color), owner, x, y);

		bodyFeatures = new ArrayList<AbstractPersonFeature>();
		faceFeatures = new PersonFaceFeatureSet();

		lastName = owner.toString();
		troopNumber= 1;
		if(RandomProvider.getInstance().nextInt(2) == 0){
			isFemale = true;
			firstName = WordGenerator.getInstance().getRandomFemaleFirstName();
		}
		else{
			isFemale = false;
			firstName =  WordGenerator.getInstance().getRandomMaleFirstName(); 
			bodyFeatures.add(new BeardPersonFeature());
		}

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

		calculateFeatues();

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

	public ArrayList<AbstractPersonFeature> getBodyFeatures(){
		return bodyFeatures;
	}

	public void setTask(AbstractTask task){
		this.task = task;
		hasTask = true;
	}
	
	public AbstractTask getTask(){
		return task;
	}

	public PersonFaceFeatureSet getFaceFeatures(){
		return faceFeatures;
	}

	public int getMovementDistance(){
		return MOVEMENT_DISTANCE;
	}

	public void act(){
		if(hasTask){
			if(task.isCompleted()){
				hasTask = false;
				hasOrders = false;
			}
			else{
				task.act();
			}
		}
		if(hasOrders){
			MovementOrderCoordinate mCoord = order.getFirstCoord();
			while(mCoord != null){
				if(GameModeState.getInstance().getMap()[yPos + mCoord.getY()][xPos + mCoord.getX()].isBuildable()){
					GameModeState.getInstance().moveFactionObject(mCoord.getX(), mCoord.getY(), this);
					hasOrders = false;
				}
				mCoord = mCoord.getNextCoord();
			}
		}
	}

	public boolean hasOrders(){
		return hasOrders;
	}

	public GameObjectPerson haveChild(GameObjectPerson person1, GameObjectPerson person2){
		if(person1.isFemale() && !person2.isFemale()){
			return new GameObjectPerson(owner.getFactionColor().getColor(), owner, person1, person2, xPos, yPos);
		}
		else if(person2.isFemale() && !person1.isFemale()){
			return new GameObjectPerson(owner.getFactionColor().getColor(), owner, person2, person1, xPos, yPos);
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
		for(AbstractPersonFeature a : bodyFeatures){
			out += secondPerson + " has " + a.getDesc();
		}
		out += secondPerson + " has " + faceFeatures.getNose().getDesc();
		out += secondPerson + " has " + faceFeatures.getHair().getDesc();
		out += secondPerson + " has " + faceFeatures.getEyes().getDesc();
		out += secondPerson + " has " + faceFeatures.getEars().getDesc();
		out += secondPerson + " has " + faceFeatures.getMouth().getDesc();
		return out;
	}

	private void calculateFeatues(){
		if(mother != null && father != null){
			if(RandomProvider.getInstance().nextBoolean()){
				faceFeatures.setNose(mother.getFaceFeatures().getNose());
			}
			else{
				faceFeatures.setNose(father.getFaceFeatures().getNose());
			}

			if(RandomProvider.getInstance().nextBoolean()){
				faceFeatures.setHair(mother.getFaceFeatures().getHair());
			}
			else{
				faceFeatures.setHair(father.getFaceFeatures().getHair());
			}

			if(RandomProvider.getInstance().nextBoolean()){
				faceFeatures.setEyes(mother.getFaceFeatures().getEyes());
			}
			else{
				faceFeatures.setEyes(father.getFaceFeatures().getEyes());
			}

			if(RandomProvider.getInstance().nextBoolean()){
				faceFeatures.setEars(mother.getFaceFeatures().getEars());
			}
			else{
				faceFeatures.setEars(father.getFaceFeatures().getEars());
			}

			if(RandomProvider.getInstance().nextBoolean()){
				faceFeatures.setMouth(mother.getFaceFeatures().getMouth());
			}
			else{
				faceFeatures.setMouth(father.getFaceFeatures().getMouth());
			}
		}
		else{
			faceFeatures.setNose(new NosePersonFeature());
			faceFeatures.setHair(new HairPersonFeature());
			faceFeatures.setEyes(new EyePersonFeature());
			faceFeatures.setEars(new EarPersonFeature());
			faceFeatures.setMouth(new MouthPersonFeature());

			bodyFeatures.add(new BodyPersonFeature());

		}
	}

}
