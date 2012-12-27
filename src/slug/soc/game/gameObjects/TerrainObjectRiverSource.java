package slug.soc.game.gameObjects;

import slug.soc.game.gameObjects.tiles.GameTile;
import slug.soc.game.gameObjects.tiles.roadAndRiver.TileRiverSource;

public class TerrainObjectRiverSource extends TerrainObject {

	public TerrainObjectRiverSource() {
		super(new TileRiverSource(), true);
	}

	public String toString(){
		return "River";
	}
}
