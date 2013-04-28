package slug.soc.game.gameState;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import slug.soc.game.Game;
import slug.soc.game.gameObjects.Faction;
import slug.soc.game.rendering.AsciiTextureGenerator;
import slug.soc.game.rendering.TextRenderer;

public class FactionInformationState implements IGameState {

	private static final float DEFAULT_TEXT_SIZE = 20;
	private static FactionInformationState instance;

	private Faction faction;

	private FactionInformationState(){

	}

	public static FactionInformationState getInstance(){
		if(instance == null){
			instance = new FactionInformationState();
		}
		return instance;
	}

	public void setFactionToDispaly(Faction faction){
		this.faction = faction;
	}

	@Override
	public void createImage() {
		//int gx = 20;
		//int gy = 30;
		GL11.glPushMatrix();
		//Image gameImage = new BufferedImage(1000,500, BufferedImage.TYPE_INT_RGB);
		//Graphics g = gameImage.getGraphics();
		GL11.glTranslatef(0, 20, 0);
		//g.setFont(FontProvider.getInstance().getFont().deriveFont(27f));
		GL11.glPushMatrix();
		
		String string = "The Great House Of " + faction.toString();
		TextRenderer.getInstance().drawString(string, DEFAULT_TEXT_SIZE, Display.getDisplayMode().getWidth());
		GL11.glPopMatrix();
		GL11.glTranslatef(0, DEFAULT_TEXT_SIZE * 2f, 0);
		//gy += 30;
		
		GL11.glPushMatrix();
		
		String out = "The great house of " + faction.toString() + " flys " + faction.getSigil().toLowerCase() + " as their sigil.";
		out += " The head of the family is " + faction.getHeadOfFamily().getName() + ".";
		TextRenderer.getInstance().drawString(out, DEFAULT_TEXT_SIZE - 2, Display.getDisplayMode().getWidth());
		GL11.glPopMatrix();
		//return gameImage;
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
//		else if(Keyboard.isKeyDown(Keyboard.KEY_I)){
//			GameObjectInformationState.getInstance().setObjectToDetail(faction.getHeadOfFamily());
//			Game.getInstance().changeToNextGameState(GameObjectInformationState.getInstance());
//		}
	}
}
