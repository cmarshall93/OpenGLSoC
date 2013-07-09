package slug.soc.game.gameObjects;

import java.util.ArrayList;

import slug.soc.game.gameObjects.tiles.GameTile;

public abstract class TerrainObjectAbstractRiver extends TerrainObject {

	public TerrainObjectAbstractRiver(GameTile tile){
		super(tile, true, false);
		hasResources = false;
		
	}
	
	public boolean isBuildable(){
		if(gameObjects.size() > 0){
			return false;
		}
		else return true;
	}
	
	public ArrayList<GameObject> getPossibleBuildings(){
		ArrayList<GameObject> out = new ArrayList<GameObject>();
		out.add(new GameObjectBridgeHorizontal(null,null,-1,-1));
		return out;
	}
	
}
