package slug.soc.game.gameState;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import slug.soc.game.Game;
import slug.soc.game.TextRenderer;
import slug.soc.game.gameObjects.GameObject;

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
		GL11.glTranslatef(0, Display.getDisplayMode().getHeight() + 60, 0);
		if(gameObject != null){
			TextRenderer.getInstance().drawString(gameObject.getDetailedDesc(), 16, Display.getDisplayMode().getWidth());
			GL11.glTranslatef(0, -16, 0);
		}
		GL11.glPopMatrix();
	}

	@Override
	public void checkInput() {
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){//esc
			Game.getInstance().setCurrentGameState(GameModeState.getInstance());
		}
	}

}
