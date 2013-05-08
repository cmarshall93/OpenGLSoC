package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileHorizontalLine;

public class TerrainObjectRiverHorizontal extends TerrainObject {

	public TerrainObjectRiverHorizontal() {
		super(new TileHorizontalLine(Color.BLUE),true);
	}

	public String toString(){
		return "River";
	}
	
	public String getTypeString(){
		return "river";
	}
}
