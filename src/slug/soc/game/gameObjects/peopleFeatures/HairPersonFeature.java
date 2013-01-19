package slug.soc.game.gameObjects.peopleFeatures;

import slug.soc.game.worldBuilding.WordGenerator;

public class HairPersonFeature implements AbstractPersonFeature {

	private String desc;
	
	public HairPersonFeature(){
		desc = WordGenerator.getInstance().getRandomLength() + " " 
					+ WordGenerator.getInstance().getRandomHairColour() + " hair.";
	}
	
	@Override
	public String getDesc() {
		return desc;
	}

}
