package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.GameTile;

/**
 * 
 * @author slug
 *
 */
public abstract class GameObject implements GameDrawable {

	protected GameTile tile;
	protected Faction owner;
	
	public GameObject(GameTile tile, Faction owner){
		this.tile = tile;	
		this.owner = owner;
	}
	
	public GameTile getTile(){
		return tile;
	}
	
	public Faction getOwner(){
		return owner;
	}
	
	public abstract String[] getStringDesc();
	
	public abstract String getDetailedDesc();
	
	public abstract void act();
}

