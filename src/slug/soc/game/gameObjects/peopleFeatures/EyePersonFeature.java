package slug.soc.game.gameObjects.peopleFeatures;

import slug.soc.game.worldBuilding.WordGenerator;

public class EyePersonFeature implements AbstractPersonFeature {

	private String desc;
	
	public EyePersonFeature(){
		desc = WordGenerator.getInstance().getRandomSize() + " " +  
				WordGenerator.getInstance().getRandomEyeColour() + " eyes.";
	}

	public String getDesc() {
		return desc;
	}

}
