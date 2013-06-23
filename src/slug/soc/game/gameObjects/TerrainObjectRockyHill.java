package slug.soc.game.gameObjects;

import slug.soc.game.gameObjects.tiles.terrian.TileRockyHill;

public class TerrainObjectRockyHill extends TerrainObject {

	public TerrainObjectRockyHill(){
		super(new TileRockyHill(), true, true);
	}
	
	public String toString(){
		return ("The " + getBiomeString() + "Rocky Hills");
	}
}
