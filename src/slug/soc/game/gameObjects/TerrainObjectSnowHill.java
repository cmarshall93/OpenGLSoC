package slug.soc.game.gameObjects;

import slug.soc.game.gameObjects.tiles.terrian.TileSnowHill;

public class TerrainObjectSnowHill extends TerrainObject {

	public TerrainObjectSnowHill() {
		super(new TileSnowHill(), true, true);
	}
	
	public String toString(){
		return "Hills";
	}

}
