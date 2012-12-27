package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileBottomTLine;

public class TerrainObjectRoadBottomT extends TerrainObject {

	public TerrainObjectRoadBottomT() {
		super(new TileBottomTLine(new Color(99,33,00)), true);
	}

	public String toString(){
		return "Road";
	}
}
