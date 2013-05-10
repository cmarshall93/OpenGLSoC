package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.roadAndRiver.TileCrossLine;

public class TerrainObjectRiverCross extends TerrainObjectAbstractRiver {

	public TerrainObjectRiverCross() {
		super(new TileCrossLine(Color.BLUE));
	}

	public String toString(){
		return "River";
	}
	
	public String getTypeString(){
		return "river";
	}
}
