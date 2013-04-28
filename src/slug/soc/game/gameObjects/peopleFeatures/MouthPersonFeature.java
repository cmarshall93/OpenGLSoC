package slug.soc.game.gameObjects.peopleFeatures;

import slug.soc.game.worldBuilding.WordGenerator;

public class MouthPersonFeature implements AbstractPersonFeature {

	private String desc;
	
	public MouthPersonFeature(){
		desc = "a " + WordGenerator.getInstance().getRandomSize() + " mouth.";
	}
	
	@Override
	public String getDesc() {
		return desc;
	}

}
