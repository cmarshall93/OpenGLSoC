package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.GameTile;
import slug.soc.game.gameObjects.tiles.faction.TileCastle;

public class GameObjectCastle extends GameObject {

	public GameObjectCastle(Color color, Faction owner) {
		super(new TileCastle(color), owner);
	}

	public String[] getStringDesc(){
		String[] desc = new String[1];
		desc[0] = "Property of the " + owner.toString() + " family";
		return desc;
	}
	
	public String toString(){
		return "Castle";
	}

}
