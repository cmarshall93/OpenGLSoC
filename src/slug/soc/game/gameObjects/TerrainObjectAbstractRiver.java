package slug.soc.game.gameObjects;

import slug.soc.game.gameObjects.tiles.GameTile;

public abstract class TerrainObjectAbstractRiver extends TerrainObject {

	public TerrainObjectAbstractRiver(GameTile tile){
		super(tile, false, false);
		hasResources = false;
	}
	
}
