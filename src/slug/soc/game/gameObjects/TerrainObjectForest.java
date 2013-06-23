package slug.soc.game.gameObjects;

import slug.soc.game.RandomProvider;
import slug.soc.game.gameObjects.tiles.GameTile;
import slug.soc.game.gameObjects.tiles.terrian.TileForest;

public class TerrainObjectForest extends TerrainObject {

	public TerrainObjectForest() {
		super(new TileForest(), true, true);
	}

	public String toString(){
		return ("The " + getBiomeString() + "Forest");
	}
	
	public String getTypeString(){
		return "forest";
	}
}