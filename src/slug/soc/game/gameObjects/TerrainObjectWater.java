package slug.soc.game.gameObjects;

import slug.soc.game.gameObjects.tiles.GameTile;
import slug.soc.game.gameObjects.tiles.terrian.TileWater;

public class TerrainObjectWater extends TerrainObject {

	public TerrainObjectWater() {
		super(new TileWater(), false);
	}
	
	public String toString(){
		return (getBiomeString() + "Water");
	}

	@Override
	public String getDesc() {
		String out = "";
		if(hasResources){
			out += "This body of water is said to contain rare fish.";
		}
		return out;
	}

}
