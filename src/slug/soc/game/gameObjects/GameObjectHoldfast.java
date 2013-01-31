package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.RandomProvider;
import slug.soc.game.gameObjects.tiles.faction.TileHoldfast;

public class GameObjectHoldfast extends GameObject {
	
	private int troopPopulation;
	private int civPopulation;

	public GameObjectHoldfast(Color color, Faction owner) {
		super(new TileHoldfast(color), owner);
		troopPopulation = RandomProvider.getInstance().nextInt(101);
		civPopulation = RandomProvider.getInstance().nextInt(301);
		dateCreated.addEvent("The completion of work on " + owner.toString() + " holdfast.");
	}

	@Override
	public String[] getStringDesc() {
		String[] desc = new String[1];
		desc[0] = "Property of the " + owner.toString() + " family";
		return desc;
	}
	
	public String toString(){
		return "Holdfast";
	}

	@Override
	public String getDetailedDesc() {		
		return owner.toString()  + " Holdfast belongs to the " + owner.toString() + " family(i). " +
				"Work was completed on " + dateCreated.toString() + ". It has a total population of " + (troopPopulation + civPopulation) + ", consisting of " + troopPopulation +
			" soilders and " + civPopulation + " peasants.";
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}

}
