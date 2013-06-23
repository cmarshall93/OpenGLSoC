package slug.soc.game.gameState;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import slug.soc.game.Game;
import slug.soc.game.gameObjects.Faction;
import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.GameObjectCastle;
import slug.soc.game.gameObjects.GameObjectHoldfast;
import slug.soc.game.gameObjects.GameObjectPerson;
import slug.soc.game.gameObjects.GameObjectTown;
import slug.soc.game.gameObjects.GameObjectVillage;
import slug.soc.game.gameObjects.TerrainObject;
import slug.soc.game.gameObjects.interaction.AbstractInteraction;
import slug.soc.game.rendering.TextRenderer;

public class BuildModeState implements IGameState {


	private static BuildModeState instance;

	private ArrayList<GameObject> buildableObjects;
	private int buildIndex;

	private TerrainObject terrainObject;

	private BuildModeState(){
		buildIndex = 0;
	}

	public static BuildModeState getInstance(){
		if(instance == null){
			instance = new BuildModeState();
		}
		instance.buildIndex = 0;
		return instance;
	}

	public void setTerrainObject(TerrainObject obj){
		terrainObject = obj;
		buildableObjects =  obj.getPossibleBuildings();
	}

	@Override
	public void createImage() {
		GL11.glPushMatrix();
		GL11.glTranslatef(0, 20, 0);
		GL11.glPushMatrix();
		GL11.glColor3f(1f, 1f, 1f);
		TextRenderer.getInstance().drawString("What to build on the " + terrainObject.toString() + " ?"
				, 16, Display.getDisplayMode().getWidth());
		GL11.glPopMatrix();
		GL11.glTranslatef(0, 32f, 0);
		int i = 0;
		if(buildableObjects != null && buildableObjects.size() > 0){
			for(GameObject m : buildableObjects){
				GL11.glPushMatrix();
				if(buildIndex == i){
					GL11.glColor3f(1f, 1f, 1f);
				}
				else{
					GL11.glColor3f(0.6f, 0.6f, 0.6f);
				}
				TextRenderer.getInstance().drawString("Build a " + m.toString(), 16, Display.getDisplayMode().getWidth());
				GL11.glPopMatrix();
				GL11.glTranslatef(0, 16f, 0);
				i++;
			}
		}
		else{
			TextRenderer.getInstance().drawString("There is nothing to build!", 16, Display.getDisplayMode().getWidth());
		}
		GL11.glPopMatrix();
	}

	@Override
	public void checkInput() {
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){//up
			nextInteract(-1);
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
			if(buildableObjects != null && buildableObjects.size() > 0){
				try {
					GameObject obj = buildableObjects.get(buildIndex);
					Class<? extends GameObject> clazz = obj.getClass();
					Constructor<? extends GameObject> cons = clazz.getConstructor(Color.class,Faction.class,int.class,int.class);
					GameModeState.getInstance().addNewFactionObject(GameModeState.getInstance().getCurrentXPos(),
							GameModeState.getInstance().getCurrentYPos(), cons.newInstance(GameModeState.getInstance().getFaction().getFactionColor().getColor(), GameModeState.getInstance().getFaction(), GameModeState.getInstance().getCurrentXPos(), GameModeState.getInstance().getCurrentYPos()))
							;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
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
		if(buildIndex + i < buildableObjects.size() && buildIndex+ i > -1){
			buildIndex += i;
		}
		else{
			buildIndex = 0;
		}
	}

}
