package slug.soc.game.gameState;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import slug.soc.game.AsciiTextureGenerator;
import slug.soc.game.FontProvider;
import slug.soc.game.Game;

public class AboutState implements IGameState {

	private static AboutState instance;

	private static final String[] text = {"Songs Of Conquest is an ascii tile based game written entirely by me (Charlie Marshall)."
		,"Think of it as Medieval Total War crossed with the politics/backstabbing/drama from "
		,"Game Of Thrones."} ;

	public static AboutState getInstance(){
		if(instance == null){
			instance = new AboutState();
		}
		return instance;
	}

	private AboutState(){

	}

	@Override
	public void checkInput() {
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){//escape
			Game.getInstance().setCurrentGameState(MainMenuState.getInstance());
		}
	}

	@Override
	public void createImage() {
		/**Image gameImage = new BufferedImage(1000,500, BufferedImage.TYPE_INT_RGB);
		Graphics g = gameImage.getGraphics();
		int gx = 30;
		int gy = 30;
		g.setFont(FontProvider.getInstance().getFont());
		for(String line: text.split("\n")){
			g.drawString(line, gx, gy += g.getFontMetrics().getHeight());
		}
		return gameImage;**/

		GL11.glPushMatrix();
		GL11.glColor3f(1f, 1f, 1f);
		GL11.glTranslatef(10, Display.getDisplayMode().getHeight() + 60, 0);
		for(String s : text){
			GL11.glPushMatrix();
			for(Character c : s.toCharArray()){
				Texture tex = AsciiTextureGenerator.getInstance().getCharacterTexture(c);
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
				GL11.glTranslated(16, 0, 0);
			}
			GL11.glPopMatrix();
			GL11.glTranslatef(0, -16, 0);
		}
		GL11.glPopMatrix();
	}
}
