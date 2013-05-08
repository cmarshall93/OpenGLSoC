package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileVerticalLine;

public class TerrainObjectRiverVertical extends TerrainObject {

	public TerrainObjectRiverVertical() {
		super(new TileVerticalLine(Color.BLUE), true);
	}
	
	public String toString(){
		return "River";
	}
	
	public String getTypeString(){
		return "river";
	}

}
