package slug.soc.game;

import slug.soc.game.gameObjects.GameObject;
/**
 * Models an event in a calendar, such as the death of a character.
 * @author charlie
 *
 */
public class GameCalendarEvent {

	private GameObject gameObj;
	private String desc;
	
	public GameCalendarEvent(String desc, GameObject obj){
		this.desc = desc;
		this.gameObj = obj;
	}
	
	public GameObject getGameObject(){
		return gameObj;
	}
	
	public String toString(){
		return desc;
	}
	
}
