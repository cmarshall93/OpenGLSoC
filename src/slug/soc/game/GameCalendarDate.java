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
	
	public GameCalendarDate(GameCalendarDate date){
		this.date = new GameCalendarMonth(date.getMonth());
		events = new ArrayList<GameCalendarEvent>();
		events = date.getEvents();
		year = date.getYear();
	}
	
	public int getYear(){
		return year;
	}
	
	public GameCalendarMonth getMonth(){
		return date;
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
