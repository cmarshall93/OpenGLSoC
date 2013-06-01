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

	public static final int MOVEMENT_DISTANCE = 5;
	
	protected GameTile tile;
	protected Faction owner;
	protected String location;
	protected GameCalendarDate dateCreated;
	protected int xPos;
	protected int yPos;
	
	protected boolean hasOrders;
	protected MovementOrder order;
	
	public GameObject(GameTile tile, Faction owner, int x, int y){
		this.tile = tile;	
		this.owner = owner;
		dateCreated = GameCalendar.getInstance().getCurrentDate();
		xPos = x;
		yPos = y;
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
	
	public void setLocation(String location){
		this.location =  location;
	}
	
	public void setXAndY(int x, int y){
		xPos = x;
		yPos = y;
	}
	
	public int getX(){
		return xPos;
	}
	
	public int getY(){
		return yPos;
	}
	
	public boolean hasOrders(){
		return hasOrders;
	}
	
	public void giveOrders(MovementOrder order){
		hasOrders = true;
		this.order = order;
	}
	
	public MovementOrder getOrder(){
		return order;
	}
	
	public abstract String[] getStringDesc();
	
	public abstract String getDetailedDesc();
	
	public abstract void act();
}

