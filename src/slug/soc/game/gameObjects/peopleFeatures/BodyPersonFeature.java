package slug.soc.game.gameObjects.peopleFeatures;

import slug.soc.game.worldBuilding.WordGenerator;

public class BodyPersonFeature implements AbstractPersonFeature {

	private String desc;
	
	public BodyPersonFeature(){
		desc = "a " + WordGenerator.getInstance().getRandomBodyType() + " body.";
	}
	
	@Override
	public String getDesc() {
		return desc;
	}

}
