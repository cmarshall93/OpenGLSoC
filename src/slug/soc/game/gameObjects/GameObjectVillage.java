package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.RandomProvider;
import slug.soc.game.WordGenerator;
import slug.soc.game.gameObjects.tiles.faction.TileVillage;

public class GameObjectVillage extends GameObject {
	
	private String name = "NAME_GOES_HERE";
	private int troops;
	private int population;

	public GameObjectVillage(Color color, Faction owner) {
		super(new TileVillage(color), owner);
		troops = RandomProvider.getInstance().nextInt(40) + 5;
		population = RandomProvider.getInstance().nextInt(100) + 20;
		name = WordGenerator.getInstance().getRandomPlaceName();
	}

	@Override
	public String[] getStringDesc() {
		String[] desc = new String[3];
		desc[0] = "Village of " + name;
		desc[1] = "Property of the " + owner.toString() + " family";
		desc[2] = "Population : " + (population + troops);
		return desc;
	}

	@Override
	public String getDetailedDesc() {
		String string = "The village of " + name + " is owned by the " + owner + " family(i)." +
				" It has a population of " + (population + troops) + " of which " + troops + " are able to fight.";
		return string;
	}
	
	public String toString(){
		return "Village";
	}

}
