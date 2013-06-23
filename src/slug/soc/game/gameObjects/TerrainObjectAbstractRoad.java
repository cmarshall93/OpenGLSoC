package slug.soc.game.gameObjects;

import slug.soc.game.gameObjects.tiles.GameTile;

public abstract class TerrainObjectAbstractRoad extends TerrainObject {

	public TerrainObjectAbstractRoad(GameTile tile){
		super(tile,true,true);
	}
	
}
