package slug.soc.game.gameObjects.tileResources;

import slug.soc.game.RandomProvider;

public class DeerResource extends AbstractResource {

	public DeerResource(){
		super("Deer", RandomProvider.getInstance().nextInt(5000) + 2000);
	}

	@Override
	public String getDesc() {
		String out = "";
		String amount;
		if(count < 500){
			amount = "small";
		}
		else if(count < 2000){
			amount = "average";
		}
		else if(count < 5000){
			amount = "good";
		}
		else{
			amount = "vast";
		}
		
		out +=" a " + amount +" amount of deer";
		return out;
	}
}
	
