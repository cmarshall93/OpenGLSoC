package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.GameTile;
import slug.soc.game.gameObjects.tiles.faction.TileLodge;
import slug.soc.game.gameState.GameModeState;

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
		String out = "This is a lodge. It is owned by the " + owner.toString() + " family. It produces 100 gold each turn.";
		out += " There is a" + GameModeState.getInstance().getMap()[yPos][xPos].getResource().getDesc();
		return out;}

	@Override
	public void act() {
		if(GameModeState.getInstance().getMap()[yPos][xPos].getResource().getCount() > 0){
			owner.changeMoney(100);
			GameModeState.getInstance().getMap()[yPos][xPos].getResource().decrementCount(1);
		}
	}
	

	@Override
	public String getSpecialCondition() {
		return "";
	}
	
	public String toString(){
		return "Hunting lodge";
	}

}
