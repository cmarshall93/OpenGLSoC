package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileRightTLine;

public class TerrainObjectRiverRightT extends TerrainObjectAbstractRiver{

	public TerrainObjectRiverRightT() {
		super(new TileRightTLine(Color.BLUE));
	}
	
	public String toString(){
		return "River";
	}
	
	public String getTypeString(){
		return "river";
	}
}
