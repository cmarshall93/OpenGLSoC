package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileVerticalLine;

public class TerrainObjectRiverVertical extends TerrainObjectAbstractRiver {

	public TerrainObjectRiverVertical() {
		super(new TileVerticalLine(Color.BLUE));
	}
	
	public String toString(){
		return "River";
	}
	
	public String getTypeString(){
		return "river";
	}

}
