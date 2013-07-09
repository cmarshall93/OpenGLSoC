package slug.soc.game.gameState;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import slug.soc.game.Game;
import slug.soc.game.GameCalendarEvent;
import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.rendering.TextRenderer;

public class EventInformationState implements IGameState {

	private static EventInformationState instance;
	
	private GameCalendarEvent event;
	private ArrayList<GameObject> objs;
	
	private EventInformationState(){
		event = null;
		objs = new ArrayList<GameObject>();
	}
	
	public static EventInformationState getInstance(){
		if(instance ==  null){
			instance = new EventInformationState();
		}
		return instance;
	}
	
	public void setEventToDisplay(GameCalendarEvent event){
		this.event = event;
		objs = this.event.getGameObjects();
	}
	
	@Override
	public void createImage() {
		GL11.glPushMatrix();
			GL11.glColor3f(1f,1f,1f);
			GL11.glTranslatef(0, 20, 0);
			GL11.glPushMatrix();
				TextRenderer.getInstance().drawString(event.toString(), 16, Display.getDisplayMode().getWidth());
			GL11.glPopMatrix();
			GL11.glTranslatef(0,32f,0);
			GL11.glPushMatrix();
				TextRenderer.getInstance().drawString(event.getDesc(), 16, Display.getDisplayMode().getWidth());
			GL11.glPopMatrix();
			GL11.glTranslatef(0, Display.getDisplayMode().getHeight() - (objs.size() * 16) - 64, 0);
			for(int i = 0;i < objs.size();i++){
				GL11.glPushMatrix();
					TextRenderer.getInstance().drawString(objs.get(i).getName() + " ("+(i + 1)+")", 16, Display.getDisplayMode().getWidth());
				GL11.glPopMatrix();
				GL11.glTranslatef(0, 18f, 0);
			}
		GL11.glPopMatrix();
	}

	@Override
	public void checkInput() {
		if (Keyboard.isKeyDown(Keyboard.KEY_BACK)){//back
			Game.getInstance().changeToPreviousGameState();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){//esc
			Game.getInstance().changeToMainScreen();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_1)){
			GameObjectInformationState.getInstance().setObjectToDetail(objs.get(0));
			Game.getInstance().changeToNextGameState(GameObjectInformationState.getInstance());
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_2) && objs.size() >= 2){
			GameObjectInformationState.getInstance().setObjectToDetail(objs.get(1));
			Game.getInstance().changeToNextGameState(GameObjectInformationState.getInstance());
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_3) && objs.size() >= 3){
			GameObjectInformationState.getInstance().setObjectToDetail(objs.get(2));
			Game.getInstance().changeToNextGameState(GameObjectInformationState.getInstance());
		}
	}
	
}
