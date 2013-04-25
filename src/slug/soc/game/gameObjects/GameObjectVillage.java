package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.GameCalendar;
import slug.soc.game.GameCalendarEvent;
import slug.soc.game.RandomProvider;
import slug.soc.game.gameObjects.tiles.faction.TileVillage;
import slug.soc.game.worldBuilding.WordGenerator;

public class GameObjectVillage extends GameObject {
	
	private String name = "NAME_GOES_HERE";
	private int troops;
	private int population;

	public GameObjectVillage(Color color, Faction owner, int x, int y){
		super(new TileVillage(color), owner, x, y);
		troops = RandomProvider.getInstance().nextInt(40) + 5;
		population = RandomProvider.getInstance().nextInt(100) + 20;
		name = WordGenerator.getInstance().getRandomPlaceName();
		
		dateCreated.addEvent(new GameCalendarEvent("The founding of " + name, this));
		GameCalendar.getInstance().addKeyDate(dateCreated);
	}

	@Override
	public String[] getStringDesc() {
		String[] desc = new String[3];
		desc[0] = "Village of " + name;
		desc[1] = "Property of the " + owner.toString() + " family";
		desc[2] = "Population : " + (population + troops);
		return desc;
	}

	public void act(){
		
	}
	
	@Override
	public String getDetailedDesc() {
		String string = "The village of " + name + " is owned by the " + owner + " family(i)." + " It is located in " + location + ". " +
				"The village was founded on " + dateCreated.toString() +". It has a population of " + (population + troops) + " with a militia of " + troops + ".";
		return string;
	}
	
	public String toString(){
		return "Village";
	}

}
