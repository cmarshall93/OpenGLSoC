package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileLeftTLine;

public class TerrainObjectRiverLeftT extends TerrainObject {

	public TerrainObjectRiverLeftT() {
		super(new TileLeftTLine(Color.BLUE), true);
	}

	public String toString(){
		return "River";
	}
	
	public String getTypeString(){
		return "river";
	}
}
