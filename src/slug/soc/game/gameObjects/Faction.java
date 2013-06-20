package slug.soc.game.gameObjects;

import java.util.ArrayList;

import slug.soc.game.RandomProvider;
import slug.soc.game.gameState.GameModeState;
import slug.soc.game.worldBuilding.ColorFactory;
import slug.soc.game.worldBuilding.FactionColor;
import slug.soc.game.worldBuilding.HouseSigilGenerator;
import slug.soc.game.worldBuilding.WordGenerator;

public class Faction {

	private FactionColor factionColor;
	private String sigil;
	private ArrayList<GameObject> holdings;
	private String name;
	private long money;

	private boolean[][] fov;

	private GameObjectPerson headOfFamily;
	private GameObjectCastle capital;

	public Faction(){
		factionColor = ColorFactory.getInstance().getRandomFactionColor();
		sigil = HouseSigilGenerator.getInstance().createNewSigilString(factionColor);
		name = WordGenerator.getInstance().getRandomFactionName();
		money = RandomProvider.getInstance().nextInt(100000);
		fov = new boolean[GameModeState.getInstance().getMap().length][GameModeState.getInstance().getMap().length];
		holdings = new ArrayList<GameObject>();
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
	
	public GameObjectCastle getCapital(){
		return capital;
	}
	
	public void setCapital(GameObjectCastle c){
		capital = c;
	}

	public GameObjectPerson getHeadOfFamily(){
		return headOfFamily;
	}
	
	public void setHeadOfFamily(GameObjectPerson p){
		headOfFamily = p;
	}

	public long getMoney(){
		return money;
	}

	public boolean[][] getFov(){
		return fov;
	}

	public void updateFov(){

		fov = new boolean[fov.length][fov.length];

		for(GameObject g : holdings){

			int w = 7;
			int h = 7;
			int x = g.getX();
			int y = g.getY();

			for(int wi = (w/2) * -1 ;wi <= w/2 ;wi++){
				for(int hi = (h/2) * -1;hi <= h/2;hi++){
					if(y + hi >= 0 && y + hi < fov.length && x + wi >= 0 && x + wi < fov.length){
						if((hi != ((h/2) * -1) || wi != ((w/2) * -1)) && (hi != h/2 || wi != w/2) && (hi != h/2 || wi != ((w/2) * -1)) && (hi != ((h/2) * -1) || wi != w/2)){
							fov[y+hi][x+wi] = true;
						}
					}
				}
			}
		}
	}

	public String toString(){
		return name;
	}
}
