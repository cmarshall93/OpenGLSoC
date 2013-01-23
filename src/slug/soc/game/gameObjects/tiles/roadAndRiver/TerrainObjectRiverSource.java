package slug.soc.game.gameObjects.tiles.roadAndRiver;

import slug.soc.game.gameObjects.TerrainObject;
import slug.soc.game.gameObjects.tiles.GameTile;

public class TerrainObjectRiverSource extends TerrainObject {

	public TerrainObjectRiverSource(GameTile tile) {
		super(new TileRiverSource(), true);
	}
	
	public String toString(){
		return "River";
	}

}
