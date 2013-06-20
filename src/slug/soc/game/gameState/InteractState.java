package slug.soc.game.gameState;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import slug.soc.game.Game;
import slug.soc.game.GameCalendar;
import slug.soc.game.GameCalendarDate;
import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.GameObjectPerson;
import slug.soc.game.gameObjects.interaction.AbstractInteraction;
import slug.soc.game.gameObjects.interaction.MoveInteraction;
import slug.soc.game.rendering.TextRenderer;

public class InteractState implements IGameState {

	private static InteractState instance;
	
	private ArrayList<AbstractInteraction> interactions;
	private GameObject obj1;
	private GameObject obj2;
	private int interactionIndex;
	
	private InteractState(){
		
	}
	
	public static InteractState getInstance(){
		if(instance == null){
			instance = new InteractState();
		}
		return instance;
	}
	
	public void setInteractions(ArrayList<AbstractInteraction> interactions, GameObject obj1, GameObject obj2){
		this.interactions = interactions;
		this.obj1 = obj1;
		this.obj2 = obj2;
		interactionIndex = 0;
	}
	
	@Override
	public void createImage() {
		GL11.glPushMatrix();
		GL11.glTranslatef(0, 20, 0);
			GL11.glPushMatrix();
				GL11.glColor3f(1f, 1f, 1f);
				TextRenderer.getInstance().drawString("Would should " + obj1.getName() + " do?"
					, 16, Display.getDisplayMode().getWidth());
			GL11.glPopMatrix();
		GL11.glTranslatef(0, 32f, 0);
		int i = 0;
		for(AbstractInteraction m : interactions){
			GL11.glPushMatrix();
			if(interactionIndex == i){
				GL11.glColor3f(1f, 1f, 1f);
			}
			else{
				GL11.glColor3f(0.6f, 0.6f, 0.6f);
			}
			TextRenderer.getInstance().drawString(m.toString() + " " + obj2.getName(), 16, Display.getDisplayMode().getWidth());
			GL11.glPopMatrix();
			GL11.glTranslatef(0, 16f, 0);
			i++;
		}
	GL11.glPopMatrix();
	}

	@Override
	public void checkInput() {
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){//up
			nextInteract(-1);
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
			interactions.get(interactionIndex).interact((GameObjectPerson)obj1);
			Game.getInstance().changeToPreviousGameState();
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_BACK)){//back
			Game.getInstance().changeToPreviousGameState();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){//esc
			Game.getInstance().changeToMainScreen();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){//down
			nextInteract(1);
		}
	}

	private void nextInteract(int i){
		if(interactionIndex + i < interactions.size() && interactionIndex + i > -1){
			interactionIndex += i;
		}
		else{
			interactionIndex = 0;
		}
	}
}
