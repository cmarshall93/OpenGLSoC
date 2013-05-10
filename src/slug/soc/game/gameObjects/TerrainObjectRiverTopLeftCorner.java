package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileTopLeftCornerLine;

public class TerrainObjectRiverTopLeftCorner extends TerrainObjectAbstractRiver {

	public TerrainObjectRiverTopLeftCorner() {
		super(new TileTopLeftCornerLine(Color.BLUE));
	}

	public String toString(){
		return "River";
	}
	
	public String getTypeString(){
		return "river";
	}
}
