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

		GameCalendarMonth jan = new GameCalendarMonth("First Moon", 31);
		GameCalendarMonth feb = new GameCalendarMonth("Frosts Decline", 28);
		GameCalendarMonth march = new GameCalendarMonth("Flowers Rise", 31);
		GameCalendarMonth april = new GameCalendarMonth("Flowers Reigh", 30);
		GameCalendarMonth may = new GameCalendarMonth("Suns Rise", 30);
		GameCalendarMonth june = new GameCalendarMonth("Suns Reigh", 30);
		GameCalendarMonth july = new GameCalendarMonth("Suns Heat", 30);
		GameCalendarMonth august = new GameCalendarMonth("Suns Decline", 30);
		GameCalendarMonth sept = new GameCalendarMonth("Leafs Fall", 30);
		GameCalendarMonth oct = new GameCalendarMonth("Last Harvest", 30);
		GameCalendarMonth nov = new GameCalendarMonth("Frosts Rise", 30);
		GameCalendarMonth dec = new GameCalendarMonth("Frosts Reigh", 30);

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
		keyDates.put(date, date);
	}

	public HashMap<GameCalendarDate, GameCalendarDate> getKeyDates(){
		return keyDates;
	}

}
