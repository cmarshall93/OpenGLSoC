package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileTopRightCornerLine;

public class TerrainObjectRiverTopRightCorner extends TerrainObject {

	public TerrainObjectRiverTopRightCorner(){
		super(new TileTopRightCornerLine(Color.BLUE));
	}
	
	public String toString(){
		return "River";
	}
}
