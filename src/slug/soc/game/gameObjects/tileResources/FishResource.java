package slug.soc.game.gameObjects.tileResources;

import slug.soc.game.RandomProvider;

public class FishResource extends AbstractResource {

	public FishResource() {
		super("Fish", RandomProvider.getInstance().nextInt(25000) + 1000);
	}

	public String getDesc(){
		String out = "";
		String amount;
		if(count < 1000){
			amount = "small";
		}
		else if(count < 10000){
			amount = "average";
		}
		else if(count < 20000){
			amount = "good";
		}
		else{
			amount = "vast";
		}

		out +=" a " + amount +" amount of fish";
		return out;
	}
}
