package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.GameTile;
import slug.soc.game.gameObjects.tiles.faction.TileCastle;

public class GameObjectCastle extends GameObject {

	public GameObjectCastle(Color color, Faction owner) {
		super(new TileCastle(color), owner);
	}

	public String[] getStringDesc(){
		String[] desc = new String[2];
		desc[0] = "Castle " + owner.toString();
		desc[1] = "Property of the " + owner.toString() + " family";
		return desc;
	}
	
	public String toString(){
		return "Castle";
	}

	@Override
	public String getDetailedDesc() {
		return "Castle " + owner.toString() + " belongs to the " + owner.toString() + " family.";
	}

}
