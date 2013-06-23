package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.GameTile;
import slug.soc.game.gameObjects.tiles.faction.TileBoat;

public class GameObjectBoat extends GameObject {

	public GameObjectBoat(Color c, Faction owner, int x, int y) {
		super(new TileBoat(c), owner, x, y);
	}

	@Override
	public String getName() {
		return "fishing boat";
	}

	@Override
	public String[] getStringDesc() {
		String[] out = new String[1];
		out[0] = "fishing boat";
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
		return "fishing boat";
	}
	

}
