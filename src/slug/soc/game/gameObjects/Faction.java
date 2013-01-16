package slug.soc.game.gameObjects;

import java.util.ArrayList;

import slug.soc.game.ColorFactory;
import slug.soc.game.FactionColor;
import slug.soc.game.HouseSigilGenerator;
import slug.soc.game.WordGenerator;

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
		
		headOfFamily.setIsFemale(true);
		holdings = new ArrayList<GameObject>();
		GameObjectPerson person = new GameObjectPerson(factionColor.getColor(), this, null, null);
		person.setIsFemale(false);
		holdings.add(new GameObjectCastle(factionColor.getColor(), this));
		holdings.add(headOfFamily);
		holdings.add(new GameObjectVillage(factionColor.getColor(), this));
		holdings.add(person);
		holdings.add(headOfFamily.haveChild(headOfFamily, person));
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
