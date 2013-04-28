package slug.soc.game.gameObjects;

import slug.soc.game.gameObjects.tiles.terrian.TileGrassHill;

public class TerrainObjectGrassHill extends TerrainObject {

	public TerrainObjectGrassHill(){
		super(new TileGrassHill(), true);
	}
	
	public String toString(){
		return ("The " + getBiomeString() + "Hills");
	}

	@Override
	public String getDesc() {
		String out = "";
		if(hasResources){
			out = "This forest is said to contain elk";
		}
		return out;
	}
}
