package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.faction.TilePerson;

public class GameObjectArmy extends GameObject {

	public GameObjectArmy(Color color, Faction owner, int x, int y) {
		super(new TilePerson(color), owner, x, y);
	}

	@Override
	public String[] getStringDesc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDetailedDesc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}

}
