package slug.soc.game.gameObjects;

import slug.soc.game.gameObjects.tiles.GameTile;
import slug.soc.game.gameObjects.tiles.terrian.TileWater;
import slug.soc.game.worldBuilding.ResourceFactory;

public class TerrainObjectWater extends TerrainObject {

	public TerrainObjectWater() {
		super(new TileWater(), false);
		if(hasResources){
			resource = ResourceFactory.getInstance().getRandomSeaResource();
		}
	}

	public String toString(){
		return (getBiomeString() + "Water");
	}

	public String getTypeString(){
		return "water";
	}

}
