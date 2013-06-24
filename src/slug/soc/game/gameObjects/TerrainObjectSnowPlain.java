package slug.soc.game.gameObjects;

import slug.soc.game.gameObjects.tiles.terrian.TileSnowPlain;

public class TerrainObjectSnowPlain extends TerrainObject {

	public TerrainObjectSnowPlain() {
		super(new TileSnowPlain(), true, true);
	}
	
	public String toString(){
		return "The " + getBiomeString() + "plains";
	}

	@Override
	public String getTypeString() {
		return "Plains";
	}

}
