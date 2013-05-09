package slug.soc.game.gameObjects.tileResources;

import slug.soc.game.RandomProvider;

public class SapphireResource extends AbstractResource {

	public SapphireResource() {
		super("Sapphire", RandomProvider.getInstance().nextInt(5000) + 500);
	}
	
	public String getDesc(){
		String amount;
		if(count < 500){
			amount = "small";
		}
		else if(count < 2500){
			amount = "average";
		}
		else if(count < 4000){
			amount = "good";
		}
		else{
			amount = "vast";
		}
		return "a " + amount + " amount of sapphires";
	}

}
