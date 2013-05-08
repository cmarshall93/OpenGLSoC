package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.GameTile;
import slug.soc.game.gameObjects.tiles.roadAndRiver.TileBottomLeftCornerLine;

public class TerrainObjectRiverBottomLeftCorner extends TerrainObject {

	public TerrainObjectRiverBottomLeftCorner() {
		super(new TileBottomLeftCornerLine(Color.BLUE), true);
	}

	public String toString(){
		return "River";
	}
	
	public String getTypeString(){
		return "river";
	}
}
