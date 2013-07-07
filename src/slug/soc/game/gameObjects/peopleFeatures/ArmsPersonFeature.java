package slug.soc.game.gameObjects.peopleFeatures;

import slug.soc.game.RandomProvider;
import slug.soc.game.worldBuilding.WordGenerator;

public class ArmsPersonFeature implements AbstractPersonFeature {

	private String desc;
	private String tattoo;
	private boolean wounded;
	
	public ArmsPersonFeature(){
		desc = WordGenerator.getInstance().getRandomBodyType() + " arms";
		tattoo = null;
		if(RandomProvider.getInstance().nextInt(2) == 1){ 
			tattoo = WordGenerator.getInstance().getRandomNoun().toLowerCase();
		}
	}
	
	public void wound(){
		wounded = true;
	}
	
	public String getDesc(){
		String out = desc;
		if(tattoo != null){
			out += " with a tattooed image of a " + tattoo + ". ";
			if(wounded){
				out += ", there is a large scar.";
			}
		}
		else if(wounded){
			out += " with a large scar.";
		}
		else{
			out += ".";
		}
		return out;
	}
}
