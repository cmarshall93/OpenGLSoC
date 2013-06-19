package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileCrossLine;

public class TerrainObjectRoadCross extends TerrainObject {

	public TerrainObjectRoadCross() {
		super(new TileCrossLine(new Color(99,33,00)), true);
	}

	public String toString(){
		return "Road";
	}

	@Override
	public String getTypeString() {
		return "road";
	}
}
