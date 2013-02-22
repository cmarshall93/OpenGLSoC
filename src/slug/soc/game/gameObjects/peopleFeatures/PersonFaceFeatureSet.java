package slug.soc.game.gameObjects.peopleFeatures;

public class PersonFaceFeatureSet {
	
	private AbstractPersonFeature ears;
	private AbstractPersonFeature eyes;
	private AbstractPersonFeature hair;
	private AbstractPersonFeature nose;
	
	public PersonFaceFeatureSet(){
		
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
}
