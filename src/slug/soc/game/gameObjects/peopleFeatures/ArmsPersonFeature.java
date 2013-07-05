package slug.soc.game.gameObjects.peopleFeatures;

import slug.soc.game.RandomProvider;
import slug.soc.game.worldBuilding.WordGenerator;

public class ArmsPersonFeature implements AbstractPersonFeature {

	private String desc;
	private String tattoo;
	
	public ArmsPersonFeature(){
		desc = WordGenerator.getInstance().getRandomBodyType() + " arms.";
		tattoo = null;
		if(RandomProvider.getInstance().nextInt(50) == 1){ 
			tattoo = WordGenerator.getInstance().getRandomNoun();
		}
	}
	
	public String getDesc(){
		String out = desc;
		if(tattoo != null){
			out.toCharArray()[out.length() -1] = ' ';
			out += "with a tattooed image of a " + tattoo + ". ";
		}
		return out;
	}
}
