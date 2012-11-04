package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileBottomRightCornerLine;

public class TerrainObjectRiverBottomRightCorner extends TerrainObject {

	public TerrainObjectRiverBottomRightCorner() {
		super(new TileBottomRightCornerLine(Color.BLUE));
	}
	
	public String toString(){
		return "River";
	}

}
