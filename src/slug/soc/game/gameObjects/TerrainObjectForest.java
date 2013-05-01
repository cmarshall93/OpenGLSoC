package slug.soc.game.gameObjects;

import slug.soc.game.gameObjects.tiles.GameTile;
import slug.soc.game.gameObjects.tiles.terrian.TileForest;

public class TerrainObjectForest extends TerrainObject {

	public TerrainObjectForest() {
		super(new TileForest(), true);
	}

	public String toString(){
		return ("The " + getBiomeString() + "Forest");
	}

	@Override
	public String getDesc() {
		String out = "";
		if(hasResources){
			out += "This forset is said to contain elk.";
		}
		return out;
	}
}
