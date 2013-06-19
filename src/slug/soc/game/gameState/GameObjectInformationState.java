package slug.soc.game.gameState;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import slug.soc.game.Game;
import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.GameObjectPerson;
import slug.soc.game.rendering.TextRenderer;

public class GameObjectInformationState implements IGameState {

	private static GameObjectInformationState instance;

	private GameObject gameObject;

	private GameObjectInformationState(){
		gameObject = null;
	}

	public static GameObjectInformationState getInstance(){
		if(instance == null){
			instance = new GameObjectInformationState();
		}
		return instance;
	}

	public void setObjectToDetail(GameObject gameObject){
		this.gameObject = gameObject;
	}

	@Override
	public void createImage() {
		GL11.glPushMatrix();
		GL11.glTranslatef(0, 20, 0);
		if(gameObject != null){
			GL11.glPushMatrix();
			TextRenderer.getInstance().drawString(gameObject.getDetailedDesc(), 16, Display.getDisplayMode().getWidth() - 16);
			GL11.glPopMatrix();
			GL11.glTranslatef(0, 112, 0);
			if(gameObject.hasOrders() && gameObject.getClass().equals(GameObjectPerson.class)){
				GL11.glPushMatrix();
				GL11.glColor3f(1f, 1f, 0f);
				TextRenderer.getInstance().drawString((gameObject.getStringDesc()[0] + " is fosuced on " + ((GameObjectPerson) gameObject).getTask().getDesc()),
						16, Display.getDisplayMode().getWidth());
				GL11.glColor3f(1f,1f,1f);
				GL11.glPopMatrix();
			}
			if(gameObject.hasSpecialCondition()){
				GL11.glTranslatef(0, 32, 0);
				GL11.glPushMatrix();
				GL11.glColor3f(0,0,1f);
				TextRenderer.getInstance().drawString(gameObject.getSpecialCondition(), 16, Display.getDisplayMode().getWidth());
				GL11.glPopMatrix();
			}
			//TextRenderer.getInstance().drawString(gameObject.getX() + " : " + gameObject.getY(), 16, Display.getDisplayMode().getWidth() - 16);
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
		else if(Keyboard.isKeyDown(Keyboard.KEY_I)){//i
			FactionInformationState.getInstance().setFactionToDispaly(gameObject.getOwner());
			Game.getInstance().changeToNextGameState(FactionInformationState.getInstance());
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_B)){//b
			DateInformationState.getInstance().setDateToDisplay(gameObject.getDateCreated());
			Game.getInstance().changeToNextGameState(DateInformationState.getInstance());
		}
	}

}
