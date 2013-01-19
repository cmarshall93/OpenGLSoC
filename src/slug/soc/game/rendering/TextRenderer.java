package slug.soc.game.rendering;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class TextRenderer {

	private static TextRenderer instance;
	
	private TextRenderer(){
	}
	
	public static TextRenderer getInstance(){
		if(instance == null){
			instance = new TextRenderer();
		}
		return instance;
	}
	
	private void drawCharacter(Character c, float size){
		Texture tex = AsciiTextureGenerator.getInstance().getCharacterTexture(c.toString());
		if(c == '('){
			GL11.glColor3f(1f, 0f, 0f);
		}
		if(tex != null){
			tex.bind();
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(0,0);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(0+size,0);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(0+size,0+size);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(0,0+size);
			GL11.glEnd();
		}
		else{
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(0,0);
			GL11.glVertex2f(0+ size,0);
			GL11.glVertex2f(0+size,0+size);
			GL11.glVertex2f(0,0+size);
			GL11.glEnd();
		}
		if(c == ')'){
			GL11.glColor3f(1f, 1f, 1f);
		}

	}
	
	public void drawString(String s, float size, float screenWidth){
		char[] charArray = s.toCharArray();
		int i = 0;
		int charsperline = (int) (screenWidth/size) - 13;
		for(Character c : charArray){
			drawCharacter(c, size);
			if(i <= charsperline){
				GL11.glTranslatef(size, 0, 0);
				i++;
			}
			else{
				GL11.glTranslatef(-(size * i), -size, 0);
				i = 0;
			}
		}
	}
	
	public void drawSymbol(String symbol, float size){
		Texture tex = AsciiTextureGenerator.getInstance().getCharacterTexture(symbol);
		if(tex != null){
			tex.bind();
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(0,0);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(0+size,0);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(0+size,0+size);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(0,0+size);
			GL11.glEnd();
		}
		else{
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(0,0);
			GL11.glVertex2f(0+ size,0);
			GL11.glVertex2f(0+size,0+size);
			GL11.glVertex2f(0,0+size);
			GL11.glEnd();
		}	
	}	
}
