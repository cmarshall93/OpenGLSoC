package slug.soc.game;

import java.util.ArrayList;

public class GameCalendarDate {

	private ArrayList<String> events;
	
	private int year;
	private GameCalendarMonth date;
	
	public GameCalendarDate(int year, GameCalendarMonth month){
		date = month;
		events = new ArrayList<String>();
		this.year = year;
		
	}
	
	public int getYear(){
		return year;
	}
	
	public void addEvent(String event){
		events.add(event);
	}
	
	public ArrayList<String> getEvents(){
		return events;
	}
	
	public String toString(){
		return date.toString() + "  " + year;
	}	
}
