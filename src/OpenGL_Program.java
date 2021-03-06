import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import slug.soc.engine.GameEngine;


public class OpenGL_Program {

	private GameEngine eng;

	private int frameCounter;
	private boolean isCounting;

	public OpenGL_Program(){
		eng = new GameEngine();
		frameCounter = 0;
		try {
			Display.setDisplayMode(new DisplayMode(1000,500));
			Display.create();
			Keyboard.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		OpenGL_Program  prog = new OpenGL_Program();
		prog.run();
	}

	private void run(){
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		//GL11.glOrtho(0, 800, 0, 600, 1, -1);
		GL11.glOrtho(0.0f, 1000, 500, 0.0f, 0.0f, 1.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		System.out.println(GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE));

		eng.startGame();
		while(!Display.isCloseRequested()){
			if(isCounting == false){
				eng.checkInput();
				isCounting = true;
			}
			
			eng.renderGame();

			Display.update();
			Display.sync(60);
			if(isCounting == true){
				frameCounter++;
				if(frameCounter == 5){
					frameCounter = 0;
					isCounting = false;		
				}
			}

		}
		Display.destroy();
	}

}
