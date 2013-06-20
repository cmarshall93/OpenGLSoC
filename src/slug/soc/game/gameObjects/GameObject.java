package slug.soc.game.gameObjects;

import java.awt.Color;
import java.util.ArrayList;

import slug.soc.game.GameCalendar;
import slug.soc.game.GameCalendarDate;
import slug.soc.game.gameObjects.interaction.AbstractInteraction;
import slug.soc.game.gameObjects.interaction.MoveInteraction;
import slug.soc.game.gameObjects.tiles.GameTile;

/**
 * 
 * @author slug
 *
 */
public abstract class GameObject implements GameDrawable {

	public static final int MOVEMENT_DISTANCE = 5;
	
	protected boolean hasSpecialCondition;
	
	protected GameTile tile;
	protected Faction owner;
	protected String location;
	protected GameCalendarDate dateCreated;
	protected int xPos;
	protected int yPos;
	
	protected boolean hasOrders;
	protected MovementOrder order;
	
	protected ArrayList<AbstractInteraction> interactions;
	
	public GameObject(GameTile tile, Faction owner, int x, int y){
		this.tile = tile;	
		this.owner = owner;
		dateCreated = GameCalendar.getInstance().getCurrentDate();
		xPos = x;
		yPos = y;
		
		interactions = new ArrayList<AbstractInteraction>();
		interactions.add(new MoveInteraction(this));
	}
	
	public GameCalendarDate getDateCreated(){
		return dateCreated;
	}
	
	public ArrayList<AbstractInteraction> getInteractions(){
		return interactions;
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
	
	public abstract String getName();
	
	public abstract String[] getStringDesc();
	
	public abstract String getDetailedDesc();
	
	public abstract void act();

	public boolean hasSpecialCondition() {
		return hasSpecialCondition;
	}
	
	public abstract String getSpecialCondition();
}

