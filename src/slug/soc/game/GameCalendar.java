package slug.soc.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class GameCalendar {

	private static GameCalendar instance;
	//holds a linked list to represent the months, int to store current year.
	private GameCalendarMonth startOfYear;
	private GameCalendarMonth currentMonth;
	private GameCalendarDate currentDate;

	private HashMap<GameCalendarDate, GameCalendarDate> keyDates;

	private int year;

	private GameCalendar(){
		keyDates = new LinkedHashMap<GameCalendarDate, GameCalendarDate>();

		GameCalendarMonth jan = new GameCalendarMonth("January", 31);//first moon
		GameCalendarMonth feb = new GameCalendarMonth("Febuary", 28);//frosts decline
		GameCalendarMonth march = new GameCalendarMonth("March", 31);//flowers rise
		GameCalendarMonth april = new GameCalendarMonth("April", 30);//flowers reigh
		GameCalendarMonth may = new GameCalendarMonth("May", 31);//suns rise
		GameCalendarMonth june = new GameCalendarMonth("June", 30);//suns reigh
		GameCalendarMonth july = new GameCalendarMonth("July", 31);//suns heat
		GameCalendarMonth august = new GameCalendarMonth("August", 30);//suns decline
		GameCalendarMonth sept = new GameCalendarMonth("September", 31);//last fall
		GameCalendarMonth oct = new GameCalendarMonth("October", 30);//last harvest
		GameCalendarMonth nov = new GameCalendarMonth("November", 31);//frosts rise
		GameCalendarMonth dec = new GameCalendarMonth("December", 30);//frosts reigh

		year = 0;
		startOfYear = jan;
		currentMonth = startOfYear;

		jan.setNext(feb);
		feb.setNext(march);
		march.setNext(april);
		april.setNext(may);
		may.setNext(june);
		june.setNext(july);
		july.setNext(august);
		august.setNext(sept);
		sept.setNext(oct);
		oct.setNext(nov);
		nov.setNext(dec);
		dec.setNext(jan);

		currentDate = new GameCalendarDate(year, currentMonth);
	}

	public static GameCalendar getInstance(){
		if(instance == null){
			instance = new GameCalendar();
		}
		return instance;
	}

	public void advanceDay(){
		GameCalendarMonth nextMonth = currentMonth.advanceDay();
		if(nextMonth.equals(startOfYear) && !currentMonth.equals(startOfYear)){
			year++;
		}
		currentMonth = nextMonth;
		currentDate = new GameCalendarDate(year, currentMonth);
	}

	public GameCalendarDate getCurrentDate(){
		return currentDate;
	}

	public String getCurrentDateAsString(){
		return currentMonth.toString() + " " + year;
	}

	public int getCurrentYear(){
		return year;
	}

	public void addKeyDate(GameCalendarDate date){
		GameCalendarDate eventDate = new GameCalendarDate(date);
		keyDates.put(eventDate, eventDate);
	}

	public HashMap<GameCalendarDate, GameCalendarDate> getKeyDates(){
		return keyDates;
	}

}
