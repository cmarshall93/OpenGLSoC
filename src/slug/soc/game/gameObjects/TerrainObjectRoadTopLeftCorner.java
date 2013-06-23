package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileTopLeftCornerLine;

public class TerrainObjectRoadTopLeftCorner extends TerrainObjectAbstractRoad {

	public TerrainObjectRoadTopLeftCorner() {
		super(new TileTopLeftCornerLine(new Color(99, 33, 00)));
	}

	public String toString() {
		return "Road";
	}

	@Override
	public String getTypeString() {
		return "road";
	}
}
