package slug.soc.game.gameState;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import slug.soc.game.Game;
import slug.soc.game.GameCalendarDate;
import slug.soc.game.GameCalendarEvent;
import slug.soc.game.rendering.TextRenderer;

public class DateInformationState implements IGameState {

	private static DateInformationState instance;
	
	private static final float DEFAULT_TEXT_SIZE  = 16f;  

	private GameCalendarDate date;
	private ArrayList<GameCalendarEvent> events;
	private int currentEventIndex;

	private DateInformationState(){
		date = null;
		events = null;
	}

	public static DateInformationState getInstance(){
		if(instance == null){
			instance = new DateInformationState();
		}
		return instance;
	}

	public void setDateToDisplay(GameCalendarDate date){
		this.date = date;
		events = date.getEvents();
		currentEventIndex = 0; 
	}

	@Override
	public void createImage() {
		GL11.glPushMatrix();
		GL11.glTranslatef(0, Display.getDisplayMode().getHeight() + 60, 0);
		GL11.glPushMatrix();
		GL11.glColor3f(1f, 1f, 1f);
		TextRenderer.getInstance().drawString(date.toString(), 24, Display.getDisplayMode().getWidth());
		GL11.glPopMatrix();
		GL11.glTranslatef(0, -24, 0);
		int i = 0;
		for(GameCalendarEvent e : date.getEvents()){
			GL11.glPushMatrix();
			if(currentEventIndex == i){
				GL11.glColor3f(1f, 1f, 1f);
				TextRenderer.getInstance().drawString(e.toString() + "(d)", DEFAULT_TEXT_SIZE, Display.getDisplayMode().getWidth());
			}
			else{
				GL11.glColor3f(0.6f,0.6f,0.6f);
				TextRenderer.getInstance().drawString(e.toString(), DEFAULT_TEXT_SIZE, Display.getDisplayMode().getWidth());
			}
			GL11.glPopMatrix();
			GL11.glTranslatef(0f, -DEFAULT_TEXT_SIZE, 0f);
			i++;
		}
		GL11.glPopMatrix();

	}

	@Override
	public void checkInput() {
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){//esc
			Game.getInstance().setCurrentGameState(GameObjectInformationState.getInstance());
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_D)){//d
			GameObjectInformationState.getInstance().setObjectToDetail(events.get(currentEventIndex).getGameObject());
			Game.getInstance().setCurrentGameState(GameObjectInformationState.getInstance());
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_UP)){//up
			nextEvent(-1);
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			nextEvent(1);
		}
	}
	
	private void nextEvent(int i){
		if(currentEventIndex + i < events.size() && currentEventIndex + i > -1){
			currentEventIndex += i;
		}
		else{
			currentEventIndex = 0;
		}
	}

}