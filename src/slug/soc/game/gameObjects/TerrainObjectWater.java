package slug.soc.game.gameObjects;

import java.util.ArrayList;

import slug.soc.game.gameObjects.tiles.GameTile;
import slug.soc.game.gameObjects.tiles.terrian.TileWater;
import slug.soc.game.worldBuilding.ResourceFactory;

public class TerrainObjectWater extends TerrainObject {

	public TerrainObjectWater() {
		super(new TileWater(), true, false);
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
	
	@Override
	public ArrayList<GameObject> getPossibleBuildings(){
		ArrayList<GameObject> possibleBuildings = new ArrayList<GameObject>();
		if(hasResources){
			possibleBuildings.add(new GameObjectBoat(null,null,0,0));
		}
		return possibleBuildings;
	}

}
