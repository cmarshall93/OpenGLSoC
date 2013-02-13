package slug.soc.game.gameState;

import java.util.Collection;
import java.util.HashMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import slug.soc.game.Game;
import slug.soc.game.GameCalendar;
import slug.soc.game.GameCalendarDate;
import slug.soc.game.rendering.TextRenderer;

public class DatesListState implements IGameState {

	private static final float DEFAULT_TEXT_SIZE = 16;
	
	private static DatesListState instance;
	

	private HashMap<GameCalendarDate, GameCalendarDate> dates;
	private int currentDateIndex;
		
	public static DatesListState getInstance(){
		if(instance == null){
			instance = new DatesListState();
		}
		instance.dates = GameCalendar.getInstance().getKeyDates();
		instance.currentDateIndex = 0; 
		return instance;
	}
	
	@Override
	public void createImage() {
		GL11.glPushMatrix();
		GL11.glTranslatef(0, Display.getDisplayMode().getHeight() + 80, 0);
		GL11.glPushMatrix();
			GL11.glColor3f(1f, 1f, 1f);
			TextRenderer.getInstance().drawString("A History Of Events", 20, Display.getDisplayMode().getWidth());
		GL11.glPopMatrix();
		GL11.glTranslatef(0, -20f, 0);
		int i = 0;
			for(GameCalendarDate date: GameCalendar.getInstance().getKeyDates().keySet()){
				GL11.glPushMatrix();
				if(currentDateIndex == i){
					GL11.glColor3f(1f, 1f, 1f);
				}
				else{
					GL11.glColor3f(0.6f, 0.6f, 0.6f);
				}
				TextRenderer.getInstance().drawString(date.toString(), DEFAULT_TEXT_SIZE, Display.getDisplayMode().getWidth());
				GL11.glPopMatrix();
				GL11.glTranslatef(0, -16f, 0);
				i++;
			}
		GL11.glPopMatrix();
	}

	@Override
	public void checkInput() {
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){//up
			nextDate(-1);
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){//d
			DateInformationState.getInstance().setDateToDisplay((GameCalendarDate)dates.keySet().toArray()[currentDateIndex]);
			Game.getInstance().changeToNextGameState(DateInformationState.getInstance());
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_BACK)){//back
			Game.getInstance().changeToPreviousGameState();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){//esc
			Game.getInstance().changeToMainScreen();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){//down
			nextDate(1);
		}
	}
	
	private void nextDate(int i){
		if(currentDateIndex + i < dates.size() && currentDateIndex + i > -1){
			currentDateIndex += i;
		}
		else{
			currentDateIndex = 0;
		}
	}

}
