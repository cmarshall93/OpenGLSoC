package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.faction.TileMine;
import slug.soc.game.gameState.GameModeState;

public class GameObjectMine extends GameObject {

	private String name;

	public GameObjectMine(Color c,Faction f, int x, int y){
		super(new TileMine(c),f,x,y);
		if(f != null){
			name = GameModeState.getInstance().getMap()[y][x].getResource().getName() + " mine";
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String[] getStringDesc() {
		String[] out = new String[1];
		out[0] = name;
		return out;
	}

	@Override
	public String getDetailedDesc() {
		String out = " This is a " + name + ".";
		out += "It is owned by the" + owner.toString() + " family.";
		return out;
	}

	@Override
	public void act() {
	}

	@Override
	public String getSpecialCondition() {
		return "";
	}

	public String toString(){
		return "Mine";
	}

}
