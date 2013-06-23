package slug.soc.game.gameObjects;

import slug.soc.game.RandomProvider;
import slug.soc.game.gameObjects.tiles.terrian.TileMountain;
import slug.soc.game.worldBuilding.ResourceFactory;

public class TerrainObjectMountain extends TerrainObject {

	public TerrainObjectMountain() {
		super(new TileMountain(), true, false);
		if(hasResources){
			resource = ResourceFactory.getInstance().getRandomMountainResource();
		}
	}
	
	public String toString(){
		return (getBiomeString() + "Mountain");
	}
	
	public String getTypeString(){
		return "mountain";
	}

}
