package slug.soc.game;

public class GameCalendarMonth {

	private final int DAYS_IN_MONTH;
	private int currentDay;
	private String name;
	
	private GameCalendarMonth nextMonth;
	
	public GameCalendarMonth(String name, int daysInMonth){
		DAYS_IN_MONTH = daysInMonth;
		this.name = name;
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
			return nextMonth;
		}
	}
	
	public String toString(){
		return currentDay + " " + name;
	}
}
