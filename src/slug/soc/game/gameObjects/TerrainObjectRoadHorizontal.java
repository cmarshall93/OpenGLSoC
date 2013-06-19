package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileHorizontalLine;

public class TerrainObjectRoadHorizontal extends TerrainObject {

	public TerrainObjectRoadHorizontal() {
		super(new TileHorizontalLine(new Color(99,33,00)), true);
	}
	
	public String toString(){
		return "Road";
	}

	@Override
	public String getTypeString() {
		return "road";
	}

}
