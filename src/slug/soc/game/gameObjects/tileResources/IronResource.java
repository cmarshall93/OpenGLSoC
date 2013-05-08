package slug.soc.game.gameObjects.tileResources;

import slug.soc.game.RandomProvider;

public class IronResource extends AbstractResource {

	public IronResource(){
		super("Iron", RandomProvider.getInstance().nextInt(25000) + 1000);
	}

	@Override
	public String getDesc() {
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
		
		out +=" a " + amount +" amount of iron";
		return out;
	}
	
}

