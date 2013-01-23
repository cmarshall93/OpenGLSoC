package slug.soc.game.gameObjects;

import java.util.ArrayList;

import slug.soc.game.worldBuilding.ColorFactory;
import slug.soc.game.worldBuilding.FactionColor;
import slug.soc.game.worldBuilding.HouseSigilGenerator;
import slug.soc.game.worldBuilding.WordGenerator;

public class Faction {

	private FactionColor factionColor;
	private String sigil;
	private ArrayList<GameObject> holdings;
	private String name;

	private GameObjectPerson headOfFamily;

	public Faction(){
		factionColor = ColorFactory.getInstance().getRandomFactionColor();
		sigil = HouseSigilGenerator.getInstance().createNewSigilString(factionColor);
		name = WordGenerator.getInstance().getRandomFactionName();
		headOfFamily = new GameObjectPerson(factionColor.getColor(), this, null, null);

		/*		 
		 * This is faction testing stuff
		 * ----------------------------------------------------------------------------------------------
		 * */
		holdings = new ArrayList<GameObject>();
		GameObjectPerson person = new GameObjectPerson(factionColor.getColor(), this, null, null);
		holdings.add(new GameObjectHoldfast(factionColor.getColor(), this));
		holdings.add(headOfFamily);
		holdings.add(new GameObjectTown(factionColor.getColor(), this));
		holdings.add(person);

		GameObjectPerson child =headOfFamily.haveChild(headOfFamily, person); 
		if(child != null){
			holdings.add(child);
		}
		/*
		 * ----------------------------------------------------------------------------------------------
		 */
	}

	public ArrayList<GameObject> getHoldings(){
		return holdings;
	}

	public String getSigil(){
		return sigil;
	}

	public FactionColor getFactionColor(){
		return factionColor;
	}

	public GameObjectPerson getHeadOfFamily(){
		return headOfFamily;
	}

	public String toString(){
		return name;
	}
}
