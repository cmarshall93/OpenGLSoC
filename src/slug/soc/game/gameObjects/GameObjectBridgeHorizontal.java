package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.GameTile;
import slug.soc.game.gameObjects.tiles.faction.TileBridgeHorizontal;
import slug.soc.game.gameState.GameModeState;

public class GameObjectBridgeHorizontal extends GameObject {

	public GameObjectBridgeHorizontal(Color color, Faction owner, int x, int y) {
		super(new TileBridgeHorizontal(),owner,x,y);
		if(x >= 0 && y >= 0){
			GameModeState.getInstance().getMap()[y][x].setIsTravelable(true);
		}
	}

	@Override
	public String getName() {
		return "Bridge";
	}

	@Override
	public String[] getStringDesc() {
		String[] out = new String[1];
		out[0] = "This is a bridge";
		return out;
	}

	@Override
	public String getDetailedDesc() {
		return "This is a bridge";
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
		return "bridge";
	}

}
