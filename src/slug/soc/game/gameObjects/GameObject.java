package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.GameCalendar;
import slug.soc.game.GameCalendarDate;
import slug.soc.game.gameObjects.tiles.GameTile;

/**
 * 
 * @author slug
 *
 */
public abstract class GameObject implements GameDrawable {

	protected GameTile tile;
	protected Faction owner;
	protected GameCalendarDate dateCreated;
	
	public GameObject(GameTile tile, Faction owner){
		this.tile = tile;	
		this.owner = owner;
		dateCreated = GameCalendar.getInstance().getCurrentDate();
	}
	
	public GameCalendarDate getDateCreated(){
		return dateCreated;
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

