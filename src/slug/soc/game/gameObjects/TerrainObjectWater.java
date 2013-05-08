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

	public String getTypeString(){
		return "water";
	}

}
