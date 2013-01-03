package slug.soc.game.gameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
	public void processKey(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			options[currentOption].act();
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			nextOption();
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP){
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
		GL11.glTranslatef(30, Display.getDisplayMode().getHeight() - 30, 0);
		for(int i = 0;i < options.length; i++){
			GL11.glColor3f(0.5f,0.5f,0.5f);
			if(i == currentOption){
				GL11.glColor3f(0.8f,0.8f,0.8f);
			}
			
			GL11.glPushMatrix();
			for(Character c: options[i].getDesc().toCharArray()){
				
				GL11.glBegin(GL11.GL_QUADS);
				AsciiTextureGenerator.getInstance().getCharacterTexture(c).bind();
				GL11.glTexCoord2f(0,0);
				GL11.glVertex2f(0,0);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex2f(0+16,0);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex2f(0+16,0+16);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex2f(0,0+16);
				GL11.glEnd();
				GL11.glTranslatef(17, 0, 0);
			}
			GL11.glPopMatrix();
			GL11.glTranslatef(0,-17,0);
		}
		GL11.glPopMatrix();
	}
}
