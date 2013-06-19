package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileLeftTLine;

public class TerrainObjectRoadLeftT extends TerrainObject {

	public TerrainObjectRoadLeftT() {
		super(new TileLeftTLine(new Color(99,33,00)), true);
	}

	public String toString(){
		return "Road";
	}

	@Override
	public String getTypeString() {
		return "road";
	}
}
