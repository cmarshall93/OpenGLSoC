package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileBottomLeftCornerLine;

public class TerrainObjectRoadBottomLeftCorner extends TerrainObject {

	public TerrainObjectRoadBottomLeftCorner() {
		super(new TileBottomLeftCornerLine(new Color(99,33,00)));
	}
	
	public String toString(){
		return "Road";
	}

}
