package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileBottomTLine;

public class TerrainObjectRiverBottomT extends TerrainObject {

	public TerrainObjectRiverBottomT() {
		super(new TileBottomTLine(Color.BLUE));
	}

	public String toString(){
		return "River";
	}
}
