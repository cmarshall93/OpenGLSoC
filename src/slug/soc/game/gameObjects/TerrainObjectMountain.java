package slug.soc.game.gameObjects;

import slug.soc.game.gameObjects.tiles.terrian.TileMountain;

public class TerrainObjectMountain extends TerrainObject {

	public TerrainObjectMountain() {
		super(new TileMountain(), true);
	}
	
	public String toString(){
		return (getBiomeString() + "Mountain");
	}
	
	public String getDesc(){
		String out = "";
		if(hasResources){
			out +="This mountain is said to contain iron";
		}
		return out;
	}

}
