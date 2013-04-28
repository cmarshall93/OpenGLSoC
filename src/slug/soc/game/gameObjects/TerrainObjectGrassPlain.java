package slug.soc.game.gameObjects;

import slug.soc.game.gameObjects.tiles.terrian.TileGrassPlain;

public class TerrainObjectGrassPlain extends TerrainObject {

	public TerrainObjectGrassPlain() {
		super(new TileGrassPlain(), true);
	}

	public String toString(){
		return ("The " + getBiomeString()+ "Plains");
	}

	@Override
	public String getDesc() {
		String out = "";
		if(hasResources){
			out = "This plain is said to contain deer.";
		}
		return out;
	}
}
