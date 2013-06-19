package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileVerticalLine;

public class TerrainObjectRoadVertical extends TerrainObject {

	public TerrainObjectRoadVertical() {
		super(new TileVerticalLine(new Color(99,33,00)), true);
	}

	public String toString(){
		return "Road";
	}

	@Override
	public String getTypeString() {
		return "road";
	}
}
