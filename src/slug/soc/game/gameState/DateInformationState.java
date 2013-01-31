package slug.soc.game.gameState;

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

	private DateInformationState(){
		date = null;
	}

	public static DateInformationState getInstance(){
		if(instance == null){
			instance = new DateInformationState();
		}
		return instance;
	}

	public void setDateToDisplay(GameCalendarDate date){
		this.date = date;
	}

	@Override
	public void createImage() {
		GL11.glPushMatrix();
		GL11.glTranslatef(0, Display.getDisplayMode().getHeight() + 60, 0);
		GL11.glPushMatrix();
		TextRenderer.getInstance().drawString(date.toString(), 24, Display.getDisplayMode().getWidth());
		GL11.glPopMatrix();
		GL11.glTranslatef(0, -24, 0);
		for(GameCalendarEvent e : date.getEvents()){
			GL11.glPushMatrix();
			TextRenderer.getInstance().drawString(e.toString(), DEFAULT_TEXT_SIZE, Display.getDisplayMode().getWidth());
			GL11.glPopMatrix();
			GL11.glTranslatef(0f, -DEFAULT_TEXT_SIZE, 0f);
		}
		GL11.glPopMatrix();

	}

	@Override
	public void checkInput() {
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){//esc
			Game.getInstance().setCurrentGameState(GameObjectInformationState.getInstance());
		}
	}

}
