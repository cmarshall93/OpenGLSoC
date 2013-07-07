package slug.soc.game.gameObjects.peopleFeatures;

import slug.soc.game.gameObjects.GameObjectPerson;

public class PersonBodyFeatureSet {

	private ArmsPersonFeature arms;
	private BodyPersonFeature body;
	
	private GameObjectPerson owner;
	
	public PersonBodyFeatureSet(GameObjectPerson owner){
		arms = new ArmsPersonFeature();
		body = new BodyPersonFeature();
		this.owner = owner;
	}
	
	public void wound(){
		arms.wound();
	}
	
	public AbstractPersonFeature getArms(){
		return arms;
	}
	
	public AbstractPersonFeature getBody(){
		return body;
	}
	
	public String getDesc(){
		String out = "";
		String secondPerson = "He";
		if(owner.isFemale()){
			secondPerson = "She";
		}
		out += secondPerson + " has " + body.getDesc();
		out += secondPerson + " has " + arms.getDesc();
		
		return out;
	}
	
}
