package slug.soc.game.gameObjects.peopleFeatures;

import slug.soc.game.worldBuilding.WordGenerator;

public class EarPersonFeature implements AbstractPersonFeature {

	private String desc;
	
	public EarPersonFeature(){
		desc = WordGenerator.getInstance().getRandomSize() + " ears.";
	}
	
	@Override
	public String getDesc() {
		return desc;
	}

}
