package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileRightTLine;

public class TerrainObjectRoadRightT extends TerrainObjectAbstractRoad{

	public TerrainObjectRoadRightT() {
		super(new TileRightTLine(new Color(99, 33, 00)));
	}

	public String toString() {
		return "Road";
	}

	@Override
	public String getTypeString() {
		return "road";
	}
}
