package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileTopLeftCornerLine;

public class TerrainObjectRiverTopLeftCorner extends TerrainObject {

	public TerrainObjectRiverTopLeftCorner() {
		super(new TileTopLeftCornerLine(Color.BLUE), true);
	}

	public String toString(){
		return "River";
	}
	
	public String getTypeString(){
		return "river";
	}
}
