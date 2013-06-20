package slug.soc.game;

public class GameCalendarMonth {

	private final int DAYS_IN_MONTH;
	private int currentDay;
	private String name;
	
	private GameCalendarMonth nextMonth;
	
	public GameCalendarMonth(String name, int daysInMonth){
		DAYS_IN_MONTH = daysInMonth;
		currentDay = 1;
		this.name = name;
	}
	
	public GameCalendarMonth(GameCalendarMonth month){
		DAYS_IN_MONTH = 31;
		currentDay = month.currentDay;
		name = month.name;
	}
	
	public void setNext(GameCalendarMonth nextMonth){
		this.nextMonth =  nextMonth;
	}
	
	public GameCalendarMonth advanceDay(){
		if(currentDay + 1 <= DAYS_IN_MONTH){
			currentDay++;
			return this;
		}
		else{
			currentDay = 1;
			return nextMonth;
		}
	}
	
	public String toString(){
		return currentDay + " " + name;
	}
}
