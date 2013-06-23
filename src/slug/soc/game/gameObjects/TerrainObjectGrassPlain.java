package slug.soc.game.gameObjects;

import slug.soc.game.RandomProvider;
import slug.soc.game.gameObjects.tiles.terrian.TileGrassPlain;

public class TerrainObjectGrassPlain extends TerrainObject {

	public TerrainObjectGrassPlain() {
		super(new TileGrassPlain(), true, true);
	}

	public String toString(){
		return ("The " + getBiomeString()+ "Plains");
	}
	
	public String getTypeString(){
		return "plain";
	}
}
