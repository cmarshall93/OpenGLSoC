package slug.soc.game.gameObjects.tileResources;

import slug.soc.game.RandomProvider;

public class LobsterResource extends AbstractResource {

	public LobsterResource() {
		super("Lobster", RandomProvider.getInstance().nextInt(10000) + 1000);
	}
	
	public String getDesc(){
		String amount;
		if(count < 1000){
			amount = "small";
		}
		else if(count < 5000){
			amount = "average";
		}
		else if(count < 9000){
			amount = "good";
		}
		else{
			amount = "vast";
		}
		return "a " + amount + " amount of lobsters";
	}

}
