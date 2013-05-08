package slug.soc.game.gameObjects;

import slug.soc.game.RandomProvider;
import slug.soc.game.gameObjects.tiles.terrian.TileGrassHill;

public class TerrainObjectGrassHill extends TerrainObject {

	public TerrainObjectGrassHill(){
		super(new TileGrassHill(), true);
	}
	
	public String toString(){
		return ("The " + getBiomeString() + "Hills");
	}
	
	public String getTypeString(){
		return "hill";
	}
}
