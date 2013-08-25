package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.faction.TileFarm;
import slug.soc.game.worldBuilding.WordGenerator;

public class GameObjectItem extends GameObject {
	
	private String name;
	private String item;

	public GameObjectItem(int x, int y) {
		super(new TileFarm(Color.WHITE), null, x, y);
		name = "The " + WordGenerator.getInstance().getRandomAdjective() + " " + WordGenerator.getInstance().getRandomNoun();
		item = WordGenerator.getInstance().getRandomItem();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String[] getStringDesc() {
		return null;
	}

	@Override
	public String getDetailedDesc() {
		return "'" + name +"' a " + item;
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSpecialCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
