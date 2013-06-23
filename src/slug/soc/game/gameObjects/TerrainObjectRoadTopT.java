package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileTopTLine;

public class TerrainObjectRoadTopT extends TerrainObjectAbstractRoad {

	public TerrainObjectRoadTopT() {
		super(new TileTopTLine(new Color(99, 33, 00)));
	}

	public String toString() {
		return "Road";
	}

	@Override
	public String getTypeString() {
		return "road";
	}
}