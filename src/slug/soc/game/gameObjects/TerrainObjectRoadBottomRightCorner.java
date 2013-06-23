package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileBottomRightCornerLine;

public class TerrainObjectRoadBottomRightCorner extends TerrainObjectAbstractRoad {

	public TerrainObjectRoadBottomRightCorner() {
		super(new TileBottomRightCornerLine(new Color(99, 33, 00)));
	}

	public String toString() {
		return "Road";
	}

	@Override
	public String getTypeString() {
		return "road";
	}

}
