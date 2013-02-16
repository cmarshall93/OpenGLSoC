package slug.soc.game.gameObjects;

import java.awt.Color;
import java.util.ArrayList;

import slug.soc.game.gameObjects.tiles.GameTile;

/**
 * 
 * @author slug
 *
 */
public abstract class TerrainObject implements GameDrawable{

	private int tileIndex;
	private GameTile baseTile;
	private GameTile currentTile;
	
	private ArrayList<GameObject> gameObjects;
	private int numberOfGameObjects;
	private int gameObjectIndex;

	private boolean isBiome;
	private Biome biome;
	public boolean isBuildable;
	private Faction owner;

	public TerrainObject(GameTile tile, boolean isBuildable){
		baseTile = tile;	
		currentTile = baseTile;
		tileIndex = 0;
		isBiome = false;
		gameObjects = new ArrayList<GameObject>();
		gameObjectIndex = 0;
		this.isBuildable = isBuildable;
		owner = null;
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
}
