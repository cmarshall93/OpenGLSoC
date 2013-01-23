package slug.soc.game.gameObjects.peopleFeatures;

import slug.soc.game.worldBuilding.WordGenerator;

public class BeardPersonFeature implements AbstractPersonFeature {

	private String desc;
	
	public BeardPersonFeature(){
		desc = "a" + WordGenerator.getInstance().getRandomBeard() + ". ";
	}
	
	@Override
	public String getDesc() {
		return desc;
	}

}
