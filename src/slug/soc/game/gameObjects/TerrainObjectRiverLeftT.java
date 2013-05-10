package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileLeftTLine;

public class TerrainObjectRiverLeftT extends TerrainObjectAbstractRiver {

	public TerrainObjectRiverLeftT() {
		super(new TileLeftTLine(Color.BLUE));
	}

	public String toString(){
		return "River";
	}
	
	public String getTypeString(){
		return "river";
	}
}
