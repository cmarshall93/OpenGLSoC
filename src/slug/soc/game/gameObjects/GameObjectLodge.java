package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.GameTile;
import slug.soc.game.gameObjects.tiles.faction.TileLodge;

public class GameObjectLodge extends GameObject {

	public GameObjectLodge(Color c, Faction owner, int x, int y) {
		super(new TileLodge(c), owner, x, y);
	}

	@Override
	public String getName() {
		return "Hunting lodge";
	}

	@Override
	public String[] getStringDesc() {
		String[] out = new String[1];
		out[0] = "Hunting lodge";
		return out;
	}

	@Override
	public String getDetailedDesc() {
		return "";
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSpecialCondition() {
		return "";
	}
	
	public String toString(){
		return "Hunting lodge";
	}

}
