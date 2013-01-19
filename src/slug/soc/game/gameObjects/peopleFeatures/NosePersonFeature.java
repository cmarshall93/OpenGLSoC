package slug.soc.game.gameObjects.peopleFeatures;

import slug.soc.game.worldBuilding.WordGenerator;

public class NosePersonFeature implements AbstractPersonFeature {

	private String desc;
	
	public NosePersonFeature(){ 
		desc = WordGenerator.getInstance().getRandomSize() + " nose.";
	}
	
	public String getDesc() {
		return desc;
	}

}
