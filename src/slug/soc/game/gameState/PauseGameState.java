package slug.soc.game.gameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import slug.soc.game.menu.AbstractMenuOption;
import slug.soc.game.menu.ContinueGameOption;
import slug.soc.game.menu.ExitProgramOption;
import slug.soc.game.rendering.AsciiTextureGenerator;
import slug.soc.game.rendering.TextRenderer;

public class PauseGameState implements IGameState {

	private static final float DEFAULT_TEXT_SIZE = 16;
	
	private static PauseGameState instance;

	private AbstractMenuOption[] options;
	private int currentOption;

	public static PauseGameState getInstance(){
		if(instance == null){
			instance = new PauseGameState();
		}
		return instance;
	}

	public PauseGameState(){
		currentOption = 0;
		options = new AbstractMenuOption[2];
		options[0] = new ContinueGameOption();
		options[1] = new ExitProgramOption();
	}

	@Override
	public void checkInput() {
		if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){//enter
			options[currentOption].act();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){//down
			nextOption();
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_UP)){//up
			previousOption();
		}
	}

	public void nextOption(){
		if(currentOption + 1 < options.length){
			currentOption++;
		}
		else{
			currentOption = 0;
		}
	}

	public void previousOption(){
		if(currentOption - 1 >= 0){
			currentOption--;
		}
		else{
			currentOption = options.length - 1;
		}
	}

	@Override
	public void createImage() {
		//Image gameImage = new BufferedImage(1000,500, BufferedImage.TYPE_INT_RGB);
		//Graphics g = gameImage.getGraphics();
		//int gx = 30;
		//int gy = 30;
		//g.setFont(FontProvider.getInstance().getFont());
		GL11.glPushMatrix();
		GL11.glTranslatef(0,20, 0);
		for(int i = 0;i < options.length; i++){
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			if(i == currentOption){
				GL11.glColor3f(1f, 1f, 1f);
			}
			GL11.glPushMatrix();
			TextRenderer.getInstance().drawString(options[i].getDesc(), 16, Display.getDisplayMode().getWidth());
			GL11.glPopMatrix();
			GL11.glTranslatef(0, DEFAULT_TEXT_SIZE, 0);
			//g.drawString(options[i].getDesc(), gx, gy);
			//gy += 30;
		}
		GL11.glPopMatrix();
		//return gameImage;
	}
}

