package slug.soc.game.gameState;

import java.awt.Color;
import java.awt.Font;
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
import slug.soc.game.HouseSigilGenerator;
import slug.soc.game.RandomProvider;
import slug.soc.game.TerrianGenerator;
import slug.soc.game.gameObjects.Faction;
import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.GameObjectArmy;
import slug.soc.game.gameObjects.GameObjectCursor;
import slug.soc.game.gameObjects.TerrainObject;
import slug.soc.game.gameObjects.TerrainObjectWater;

/**
 * Represents the current game.
 * @author slug
 *
 */
public class GameModeState implements IGameState, Runnable {

	private static final int UPDATE_RATE = 30;
	private static GameModeState instance;

	private long lastFPS;
	private int currentFPS;
	private int frames;

	private boolean viewHoldings;

	private TerrainObject[][] map;
	private TerrianGenerator terrianGenerator;
	private GameObjectCursor cursor = new GameObjectCursor();
	private Integer currentXPos;
	private Integer currentYPos;
	private float[] zoomScales = {
			1.0f, 0.5f	
	};

	private boolean cursorActive = false;
	private boolean loadedWorld = false;

	private String[] loadingString = {".", "..", "..."};
	private int currentLoadingString = 0;

	private int currentZoomIndex = 0;
	private int frameCounter;

	public static GameModeState getInstance(){
		if(instance == null){
			instance = new GameModeState();
		}
		return instance;
	}

	private GameModeState(){
		terrianGenerator = new TerrianGenerator();
		lastFPS = System.currentTimeMillis();
	}

	public void run(){
		generateWorld();
	}

	public void generateWorld(){
		long start = System.nanoTime();

		map = terrianGenerator.testGenerateMapMultiCont(100, 100);
		currentXPos = 50;
		currentYPos = 50;

		//**************faction testing*******************
		Faction faction = new Faction();
		System.out.println(faction.getSigil());
		int x = 50;
		int y = 50;
		boolean testBol = false;
		while(!testBol){
			if(!map[y][x].isBuildable()){
				x = RandomProvider.getInstance().nextInt(map.length);
				y = RandomProvider.getInstance().nextInt(map.length);
			}
			else{
				testBol = true;
			}
		}
		for(GameObject g : faction.getHoldings()){
			map[y][x].addGameObject(g);
			for(TerrainObject t: map[y][x].getBiome().getContents()){
				if(t != null){
					t.setOwner(faction);
				}
			}
			y++;
		}
		//************************************************

		long end = System.nanoTime();
		System.out.println("GenTime: " + (end - start)/1000000);
		loadedWorld = true;
	}

	public TerrainObject[][] getMap(){
		return map;
	}

	public void checkInput(){
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){//up
			if(currentYPos > 0){
				if(cursorActive){
					map[currentYPos][currentXPos].removeGameObject(cursor);
					currentYPos--;
					map[currentYPos][currentXPos].addGameObject(cursor);
				}
				else{
					currentYPos--;
				}
			}
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){//down
			if(currentYPos < getMap().length - 1){
				if(cursorActive){
					map[currentYPos][currentXPos].removeGameObject(cursor);
					currentYPos++;
					map[currentYPos][currentXPos].addGameObject(cursor);
				}
				else{
					currentYPos++;
				}
			}
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			if(currentXPos < getMap().length -1){ 
				if(cursorActive){
					map[currentYPos][currentXPos].removeGameObject(cursor);
					currentXPos++;
					map[currentYPos][currentXPos].addGameObject(cursor);
				}
				else{
					currentXPos++;
				}
			}
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){//left
			if(currentXPos > 0){
				if(cursorActive){
					map[currentYPos][currentXPos].removeGameObject(cursor);
					currentXPos--;
					map[currentYPos][currentXPos].addGameObject(cursor);
				}
				else{
					currentXPos--;
				}
			}
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_LBRACKET)){//open bracket
			if(currentZoomIndex - 1 > -1){
				currentZoomIndex--;
			}
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_RBRACKET)){//close bracket
			if(currentZoomIndex + 1 < zoomScales.length){
				currentZoomIndex++;
			}
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_C)){//c
			if(!cursorActive){
				map[currentYPos][currentXPos].addGameObject(cursor);
				cursorActive = true;
			}
			else{
				cursorActive = false;
				map[currentYPos][currentXPos].removeGameObject(cursor);
			}
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){//esc
			Game.getInstance().setCurrentGameState(PauseGameState.getInstance());
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_H)){//h
			viewHoldings = !viewHoldings;
		}
	}

	public void createImage(){
		frameCounter++;
		//Image gameImage = new BufferedImage(1000,500, BufferedImage.TYPE_INT_RGB);
		//Graphics g = gameImage.getGraphics();
		if(loadedWorld){
			drawMap();
			//g.setColor(Color.WHITE);
			GL11.glColor3f(1f, 1f, 1f);
			//g.drawLine(500, 0, 500, 500);
			drawInfo();
		}
		else{
			drawLoading();
		}

		if(frameCounter >= UPDATE_RATE ){
			frameCounter = 0;
		}
	}	

	private void drawMap(){
		//int gy = 30;
		//int gx;
		GL11.glPushMatrix();
		
		GL11.glTranslatef(0f, Display.getDisplayMode().getHeight() - 30f, 0f);
		//g.setFont(FontProvider.getInstance().getFont());
		//g.setFont(FontProvider.getInstance().getFont().deriveFont((float)Math.floor(19 * zoomScales[currentZoomIndex])));
		//GL11.glScalef(zoomScales[currentZoomIndex], zoomScales[currentZoomIndex], 0f);
		for(int y = currentYPos - 12 * (int) (1/zoomScales[currentZoomIndex]), my = 0; my < (25 * 1/zoomScales[currentZoomIndex]); y++,my++){
			//gx = 15;
			GL11.glPushMatrix();
			for(int x = currentXPos - 12 * (int) (1/zoomScales[currentZoomIndex]), mx = 0; mx < (25 * 1/zoomScales[currentZoomIndex]) ; x++, mx++){
				if(x < 0 || y < 0 || x >= getMap().length || y >= getMap().length ){
					//g.setColor(Color.BLACK);
					GL11.glColor3f(1f,1f,1f);
					//g.drawString(" ", gx, gy);
					drawCharacter("a");
				}
				else{
					if(frameCounter >= UPDATE_RATE){
						getMap()[y][x].nextTile();
					}
					if(viewHoldings && map[y][x].getOwner() != null){ //check if player wants to see owners of tiles
						//g.setColor(map[y][x].getOwner().getFactionColor().getColor());
						Color color = map[y][x].getOwner().getFactionColor().getColor();
						//GL11.glColor3f(color.getRed()/256, color.getGreen()/256, color.getBlue()/256);
						GL11.glColor3f(1f, 1f, 1f);
					}
					else{//default viewing
						//g.setColor(getMap()[y][x].getTile().getColor());
						Color color = map[y][x].getTile().getColor();
						GL11.glColor3f(1f, 1f, 1f);
						//GL11.glColor3f(color.getRed()/256, color.getGreen()/256, color.getBlue()/256);
					}
					//g.drawString(getMap()[y][x].getTile().getSymbol().toString(), gx, gy);
					//drawCharacter('a');
					System.out.println(getMap()[y][x].getTile().getSymbol());
					drawCharacter(getMap()[y][x].getTile().getSymbol());
				}
				//gx += g.getFont().getSize();
				GL11.glTranslatef(20 * zoomScales[currentZoomIndex], 0, 0);

			}
			//gy += g.getFont().getSize();
			GL11.glPopMatrix();
			GL11.glTranslatef(0, - 20 * zoomScales[currentZoomIndex], 0);

		}
		
		GL11.glPopMatrix();
	}

	private void drawInfo(){

		/**int gx = 510;
		int gy = 30;

		g.setFont(FontProvider.getInstance().getFont().deriveFont(10f));
		if(currentYPos > 0 && currentXPos > 0 && currentYPos < map.length && currentXPos < map.length){
			g.drawString(getMap()[currentYPos][currentXPos].toString(),gx, gy);
		}
		gy += 11;
		g.drawString("X: " + currentXPos.toString(), gx, gy);
		gx += 50;
		g.drawString("Y: " + currentYPos.toString(), gx, gy);
		gx -= 50;
		gy += 20;
		if(getMap()[currentYPos][currentXPos].getOwner() != null){
			g.drawString("Property of the " + getMap()[currentYPos][currentXPos].getOwner().toString() + " family", gx, gy);
		}
		else{
			g.drawString("Unclaimed land", gx, gy);
		}

		if(viewHoldings){
			gy = 480;
			gx = 520;
			g.drawString("Holdings View", gx, gy);
		}

		gy = 480;
		gx = 980;
		if(System.currentTimeMillis() - lastFPS > 1000){
			currentFPS = frames;
			frames = 0;
			lastFPS += 1000;
		}
		Integer f = currentFPS;
		g.drawString(f.toString(), gx, gy);
		frames++;*/
	}

	private void drawLoading(){
		/**int gy = 30;
		int gx = 30;
		g.setFont(Fon``tProvider.getInstance().getFont());
		g.drawString("Generating world", gx, gy);
		gy += 20;
		for(String s: getGenStatus()){
			g.drawString(s, gx, gy);
			gy += 20;
		}
		gy = 480;
		gx = 500;
		if(frameCounter >= UPDATE_RATE){
			if(currentLoadingString + 1 < loadingString.length){
				currentLoadingString++  ;
			}
			else{
				currentLoadingString = 0;
			}
		}
		g.drawString(loadingString[currentLoadingString], gx, gy);*/
	}

	private String[] getGenStatus(){
		String[] status = new String[4];
		status[0] = "Terrain Generation......" + terrianGenerator.getGenStatus().toString() + "%";
		status[1] = "test";
		status[2] = "test";
		status[3] = "test";
		return status;
	}
	
	private void drawCharacter(String c){
		Texture tex = AsciiTextureGenerator.getInstance().getCharacterTexture(c);
		if(tex != null){
			tex.bind();
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(0,0);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(0+20,0);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(0+20,0+20);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(0,0+20);
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
	}
}
