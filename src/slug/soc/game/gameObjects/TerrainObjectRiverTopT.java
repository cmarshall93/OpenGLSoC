package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileTopTLine;

public class TerrainObjectRiverTopT extends TerrainObject {

	public TerrainObjectRiverTopT() {
		super(new TileTopTLine(Color.BLUE), true);
	}

	public String toString(){
		return "River";
	}
	
	public String getTypeString(){
		return "river";
	}
}
