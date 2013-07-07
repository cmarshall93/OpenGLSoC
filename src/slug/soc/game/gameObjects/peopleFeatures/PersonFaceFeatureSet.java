package slug.soc.game.gameObjects.peopleFeatures;

import slug.soc.game.gameObjects.GameObjectPerson;

public class PersonFaceFeatureSet {

	private AbstractPersonFeature ears;
	private AbstractPersonFeature eyes;
	private AbstractPersonFeature hair;
	private AbstractPersonFeature nose;
	private AbstractPersonFeature mouth;
	private AbstractPersonFeature beard;

	private GameObjectPerson owner;

	public PersonFaceFeatureSet(GameObjectPerson owner){
		this.owner = owner;
	}

	public AbstractPersonFeature getEars() {
		return ears;
	}

	public void setEars(AbstractPersonFeature ears) {
		this.ears = ears;
	}

	public AbstractPersonFeature getEyes() {
		return eyes;
	}

	public void setEyes(AbstractPersonFeature eyes) {
		this.eyes = eyes;
	}

	public AbstractPersonFeature getHair() {
		return hair;
	}

	public void setHair(AbstractPersonFeature hair) {
		this.hair = hair;
	}

	public AbstractPersonFeature getNose() {
		return nose;
	}

	public void setNose(AbstractPersonFeature nose) {
		this.nose = nose;
	}

	public AbstractPersonFeature getMouth() {
		return mouth;
	}

	public void setMouth(AbstractPersonFeature mouth) {
		this.mouth = mouth;
	}

	public void setBeard(AbstractPersonFeature beard){
		this.beard = beard;
	}

	public AbstractPersonFeature getBeard(){
		return beard;
	}

	public String getDesc(){
		String out = "";
		String secondPerson = "He";
		if(owner.isFemale()){
			secondPerson = "She";
		}
		out += secondPerson + " has " + getNose().getDesc();
		out += secondPerson + " has " + getHair().getDesc();
		out += secondPerson + " has " + getEyes().getDesc();
		out += secondPerson + " has " + getEars().getDesc();
		out += secondPerson + " has " + getMouth().getDesc();
		if(beard != null){
			out += secondPerson + " has " + getBeard().getDesc();
		}

		return out;
	}

}
