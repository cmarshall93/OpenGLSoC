package slug.soc.game.gameObjects;

import slug.soc.game.RandomProvider;
import slug.soc.game.gameObjects.tiles.terrian.TileGrassHill;

public class TerrainObjectGrassHill extends TerrainObject {

	public TerrainObjectGrassHill(){
		super(new TileGrassHill(), true);
		amountOfResources = RandomProvider.getInstance().nextInt(25000) + 1000;
	}
	
	public String toString(){
		return ("The " + getBiomeString() + "Hills");
	}

	@Override
	public String getDesc() {
		String out = "";
		if(hasResources){
			String amount = "";
			if(amountOfResources < 1000){
				amount = "small";
			}
			else if(amountOfResources < 10000){
				amount = "average";
			}
			else if(amountOfResources < 20000){
				amount = "good";
			}
			else{
				amount = "vast";
			}
			out += "This hill is said to contain a " + amount + "amount of chalk";
		}
		return out;
	}
}
