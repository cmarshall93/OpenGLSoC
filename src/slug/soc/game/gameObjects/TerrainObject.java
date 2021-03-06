package slug.soc.game.gameObjects;

import java.awt.Color;
import java.util.ArrayList;

import slug.soc.game.RandomProvider;
import slug.soc.game.gameObjects.tileResources.AbstractResource;
import slug.soc.game.gameObjects.tiles.GameTile;
import slug.soc.game.worldBuilding.ResourceFactory;

/**
 * 
 * @author slug
 *
 */
public abstract class TerrainObject implements GameDrawable{

	private int tileIndex;
	private GameTile baseTile;
	private GameTile currentTile;

	protected ArrayList<GameObject> gameObjects;
	private int numberOfGameObjects;
	private int gameObjectIndex;

	protected boolean hasResources;
	protected AbstractResource resource;

	private boolean isBiome;
	private Biome biome;
	private boolean isBuildable;
	private boolean isTravelable;

	private Faction owner;

	public TerrainObject(GameTile tile, boolean isBuildable, boolean isTravelable){
		baseTile = tile;	
		currentTile = baseTile;
		tileIndex = 0;
		isBiome = false;
		gameObjects = new ArrayList<GameObject>();
		gameObjectIndex = 0;

		this.isBuildable = isBuildable;
		this.isTravelable = isTravelable;

		owner = null;
		if(RandomProvider.getInstance().nextInt(20) == 1){
			hasResources = true;
			resource = ResourceFactory.getInstance().getRandomLandResource();
		}
	}

	public GameTile getTile(){
		return currentTile;
	}

	public void addGameObject(GameObject o){
		if(isBuildable || o.getOwner() == null){
			if(owner == null){
				gameObjects.add(o);
				if(o.getOwner() != null){
					owner = o.getOwner();
				}
			}
			else if(owner == o.getOwner() || o.getOwner() == null){
				gameObjects.add(o);
			}
			o.setLocation(toString());

			if(!(o instanceof GameObjectCursor)){
				numberOfGameObjects = gameObjects.size();
			}
		}
	}

	public void removeGameObject(GameObject o){
		gameObjects.remove(o);
		if(gameObjects.size() == 0 && o.getOwner() != null){
			owner = null;
		}
		gameObjectIndex = 0;
	}

	public GameObject getCurrentGameObject(){
		return gameObjects.get(gameObjectIndex);
	}

	public ArrayList<GameObject> getGameObjects(){
		return gameObjects;
	}

	public GameObject getNextGameObject(){
		if(gameObjectIndex + 1 < gameObjects.size()){
			gameObjectIndex += 1;
		}
		else{
			gameObjectIndex = 0;
		}
		return gameObjects.get(gameObjectIndex);
	}

	public void nextTile(){
		if(tileIndex < gameObjects.size()){
			currentTile = gameObjects.get(tileIndex).getTile();
			tileIndex++;
		}
		else{
			tileIndex = 0;
			currentTile = baseTile;
		}
	}

	public int getNumberOfGameObjects(){
		return numberOfGameObjects;
	}

	public boolean isBiome(){
		return isBiome;
	}

	public void addToBiome(Biome biome){
		this.biome = biome;
		isBiome = true;
	}

	public Biome getBiome(){
		return biome;
	}

	public String getBiomeString(){
		if(isBiome){
			return biome.toString();
		}
		return "";
	}

	public void setOwner(Faction owner){
		if(this.owner == null){
			this.owner = owner;
		}
	}

	public Faction getOwner(){
		return owner;
	}

	public boolean isBuildable(){
		return isBuildable;
	}
	
	public void setIsBuildable(boolean b){
		isBuildable = b;
	}
	
	public boolean isTravelable() {
		return isTravelable;
	}
	
	public void setIsTravelable(boolean b){
		isTravelable = b;
	}

	public String getDesc(){
		String out = "";
		if(hasResources){
			out += "This " + getTypeString() + " is said to contain " + resource.getDesc();
		}
		return out;
	}

	public boolean hasResources() {
		return hasResources;
	}
	
	public AbstractResource getResource(){
		return resource;
	}

	public GameTile getBaseTile() {
		return baseTile;
	}

	public ArrayList<GameObject> getPossibleBuildings(){
		ArrayList<GameObject> possibleBuildings = new ArrayList<GameObject>();
		possibleBuildings.add(new GameObjectTown(null,null,0,0));
		possibleBuildings.add(new GameObjectCastle(null,null,0,0));
		possibleBuildings.add(new GameObjectHoldfast(null,null,0,0));
		possibleBuildings.add(new GameObjectVillage(null,null,0,0));
		if(hasResources){
			if(resource.getBuilding().equals("mine")){
				possibleBuildings.add(new GameObjectMine(null,null,0,0));
			}
			else if(resource.getBuilding().equals("lodge")){
				possibleBuildings.add(new GameObjectLodge(null,null,0,0));
			}
			else if(resource.getBuilding().equals("farm")){
				possibleBuildings.add(new GameObjectFarm(null,null,0,0));
			}
		}
		return possibleBuildings;
	}

	public abstract String getTypeString();

}
