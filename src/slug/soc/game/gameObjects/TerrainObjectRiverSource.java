package slug.soc.game.gameObjects;

import slug.soc.game.gameObjects.tiles.GameTile;
import slug.soc.game.gameObjects.tiles.roadAndRiver.TileRiverSource;

public class TerrainObjectRiverSource extends TerrainObjectAbstractRiver {

	public TerrainObjectRiverSource() {
		super(new TileRiverSource());
	}

	public String toString(){
		return "River";
	}
	
	public String getTypeString(){
		return "river";
	}
}
