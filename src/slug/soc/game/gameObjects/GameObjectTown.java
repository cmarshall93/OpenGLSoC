package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.GameCalendarEvent;
import slug.soc.game.RandomProvider;
import slug.soc.game.gameObjects.tiles.faction.TileTown;
import slug.soc.game.worldBuilding.WordGenerator;

public class GameObjectTown extends GameObject {

	private int troops;
	private int population;
	private String name;

	public GameObjectTown(Color color, Faction owner) {
		super(new TileTown(color), owner);
		troops = RandomProvider.getInstance().nextInt(100) +30;
		population = RandomProvider.getInstance().nextInt(2000) + 200;
		name = WordGenerator.getInstance().getRandomPlaceName();
		dateCreated.addEvent(new GameCalendarEvent("The founding of " + name, this));
	}

	@Override
	public String[] getStringDesc() {	
		String[] desc = new String[3];
		desc[0] = "Town of " + name;
		desc[1] = "Property of the " + owner.toString() + " family";
		desc[2] = "Population : " + (population + troops);
		return desc;
	}
	
	public String toString(){
		return "Town";
	}

	@Override
	public String getDetailedDesc() {
		String string = "The town of " + name + " is owned by the " + owner + " family(i)." +
				"The town was founded on " + dateCreated.toString() + ". It has a population of " + (population + troops) + " of which " + troops + " are able to fight.";
		return string;
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub

	}

}
