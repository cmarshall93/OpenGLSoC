package slug.soc.game.gameState;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import slug.soc.game.FontProvider;
import slug.soc.game.Game;
import slug.soc.game.rendering.AsciiTextureGenerator;
import slug.soc.game.rendering.TextRenderer;

public class AboutState implements IGameState {

	private static AboutState instance;

	private static final String text =
		"Songs Of Conquest is an ascii tile based game written entirely by me " +
		".Think of it as Medieval Total War crossed with the politics/backstabbing/drama " +
		"from Game Of Thrones";

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
			Game.getInstance().changeToPreviousGameState();
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
		TextRenderer.getInstance().drawString(text, 20, Display.getDisplayMode().getWidth());
		GL11.glPopMatrix();
	}
}
