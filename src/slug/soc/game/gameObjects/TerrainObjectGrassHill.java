package slug.soc.game.gameObjects;

import slug.soc.game.RandomProvider;
import slug.soc.game.gameObjects.tiles.terrian.TileGrassHill;
import slug.soc.game.worldBuilding.ResourceFactory;

public class TerrainObjectGrassHill extends TerrainObject {

	public TerrainObjectGrassHill(){
		super(new TileGrassHill(), true, true);
		if(hasResources){
			resource = ResourceFactory.getInstance().getRandomMountainResource();
		}
	}
	
	public String toString(){
		return ("The " + getBiomeString() + "Hills");
	}
	
	public String getTypeString(){
		return "hill";
	}
}
