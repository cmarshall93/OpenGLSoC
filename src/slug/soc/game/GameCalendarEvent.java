package slug.soc.game;

import java.util.ArrayList;

import slug.soc.game.gameObjects.GameObject;
/**
 * Models an event in a calendar, such as the death of a character.
 * @author charlie
 *
 */
public class GameCalendarEvent {

	private ArrayList<GameObject> gameObjs;
	private String shortDesc;
	private String longDesc;
	
	public GameCalendarEvent(String shortDesc,String longDesc, ArrayList<GameObject> objs){
		this.shortDesc = shortDesc;
		this.longDesc = longDesc;
		this.gameObjs = objs;
	}
	
	public ArrayList<GameObject> getGameObjects(){
		return gameObjs;
	}
	
	public String getDesc(){
		return longDesc;
	}
	
	public String toString(){
		return shortDesc;
	}
	
}
