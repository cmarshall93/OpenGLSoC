package slug.soc.game.gameObjects;

import slug.soc.game.gameObjects.tiles.GameTile;
import slug.soc.game.gameObjects.tiles.faction.TileBridgeHorizontal;

public class TerrainObjectBridgeHorizontal extends TerrainObject {

	public TerrainObjectBridgeHorizontal() {
		super(new TileBridgeHorizontal() , false, true);
	}

	@Override
	public String getTypeString() {
		return "bridge";
	}

}
