package slug.soc.game.gameObjects;

import slug.soc.game.RandomProvider;
import slug.soc.game.gameObjects.tiles.terrian.TileGrassPlain;

public class TerrainObjectGrassPlain extends TerrainObject {

	public TerrainObjectGrassPlain() {
		super(new TileGrassPlain(), true);
		amountOfResources = RandomProvider.getInstance().nextInt(1000) + 100;
	}

	public String toString(){
		return ("The " + getBiomeString()+ "Plains");
	}

	@Override
	public String getDesc() {
		String out = "";
		if(hasResources){
			String amount = "";
			if(amountOfResources < 100){
				amount = "small";
			}
			else if(amountOfResources < 500){
				amount = "average";
			}
			else if(amountOfResources < 750){
				amount = "good";
			}
			else{
				amount = "vast";
			}
			out += "This plain is said to contain a " + amount + " amount of deer.";
		}
		return out;
	}
}
