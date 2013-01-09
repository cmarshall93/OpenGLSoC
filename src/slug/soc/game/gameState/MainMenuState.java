package slug.soc.game.gameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import slug.soc.game.AsciiTextureGenerator;
import slug.soc.game.FontProvider;
import slug.soc.game.menu.AbstractMenuOption;
import slug.soc.game.menu.ExitProgramOption;
import slug.soc.game.menu.AboutOption;
import slug.soc.game.menu.RunGameOption;

public class MainMenuState implements IGameState {

	private static MainMenuState instance;

	private AbstractMenuOption[] options;
	private int currentOption;

	public static MainMenuState getInstance(){
		if(instance == null){
			instance = new MainMenuState();
		}
		return instance;
	}

	private MainMenuState(){
		options = new AbstractMenuOption[3];
		options[0] = new RunGameOption();
		options[1] = new AboutOption();
		options[2] = new ExitProgramOption();
		currentOption = 0;
	}

	@Override
	public void checkInput() {
		if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
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

		GL11.glPushMatrix();
		GL11.glTranslatef(10, Display.getDisplayMode().getHeight() + 60, 0);
		for(int i = 0;i < options.length; i++){
			GL11.glColor3f(0.5f,0.5f,0.5f);
			if(i == currentOption){
				GL11.glColor3f(0.8f,0.8f,0.8f);
			}

			GL11.glPushMatrix();
			for(Character c: options[i].getDesc().toCharArray()){
			Texture tex = AsciiTextureGenerator.getInstance().getCharacterTexture(c.toString());
				if(tex != null){
					tex.bind();
					GL11.glBegin(GL11.GL_QUADS);
					GL11.glTexCoord2f(0,0);
					GL11.glVertex2f(0,0);
					GL11.glTexCoord2f(1, 0);
					GL11.glVertex2f(0+16,0);
					GL11.glTexCoord2f(1, 1);
					GL11.glVertex2f(0+16,0+16);
					GL11.glTexCoord2f(0, 1);
					GL11.glVertex2f(0,0+16);
					GL11.glEnd();
				}
				else{
					GL11.glBegin(GL11.GL_QUADS);
					GL11.glVertex2f(0,0);
					GL11.glVertex2f(0+16,0);
					GL11.glVertex2f(0+16,0+16);
					GL11.glVertex2f(0,0+16);
					GL11.glEnd();
				}
				GL11.glTranslatef(16, 0, 0);
			}
			GL11.glPopMatrix();
			GL11.glTranslatef(0,-17,0);
		}
		GL11.glPopMatrix();
	}
}
