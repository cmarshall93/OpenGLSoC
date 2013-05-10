package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileHorizontalLine;

public class TerrainObjectRiverHorizontal extends TerrainObjectAbstractRiver {

	public TerrainObjectRiverHorizontal() {
		super(new TileHorizontalLine(Color.BLUE));
	}

	public String toString(){
		return "River";
	}
	
	public String getTypeString(){
		return "river";
	}
}
