package slug.soc.game;

import java.util.ArrayList;

public class GameCalendarDate {

	private ArrayList<GameCalendarEvent> events;
	
	private int year;
	private GameCalendarMonth date;
	
	public GameCalendarDate(int year, GameCalendarMonth month){
		date = month;
		events = new ArrayList<GameCalendarEvent>();
		this.year = year;
		
	}
	
	public int getYear(){
		return year;
	}
	
	public void addEvent(GameCalendarEvent event){
		events.add(event);
	}
	
	public ArrayList<GameCalendarEvent> getEvents(){
		return events;
	}
	
	public String toString(){
		return date.toString() + "  " + year;
	}	
}
