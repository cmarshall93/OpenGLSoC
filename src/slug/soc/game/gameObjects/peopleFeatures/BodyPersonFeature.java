package slug.soc.game.gameObjects.peopleFeatures;

import slug.soc.game.RandomProvider;
import slug.soc.game.worldBuilding.WordGenerator;

public class BodyPersonFeature implements AbstractPersonFeature {

	private String desc;
	private String tattoo;
	
	public BodyPersonFeature(){
		desc = "a " + WordGenerator.getInstance().getRandomBodyType() + " body.";
		tattoo = null;
		if(RandomProvider.getInstance().nextInt(20) == 1){ 
			tattoo = WordGenerator.getInstance().getRandomNoun();
		}
	}
	
	@Override
	public String getDesc() {
		String out = desc;
		if(tattoo != null){
			out.replace('.', ' ');
			out += "with a tattooed image of a " + tattoo + ". ";
		}
		return out;
	}

}
