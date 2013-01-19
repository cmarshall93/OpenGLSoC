package slug.soc.game.gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import slug.soc.game.FontProvider;
import slug.soc.game.Game;
import slug.soc.game.RandomProvider;
import slug.soc.game.gameObjects.Faction;
import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.GameObjectCursor;
import slug.soc.game.gameObjects.TerrainObject;
import slug.soc.game.gameObjects.TerrainObjectWater;
import slug.soc.game.rendering.AsciiTextureGenerator;
import slug.soc.game.rendering.TextRenderer;
import slug.soc.game.worldBuilding.HouseSigilGenerator;
import slug.soc.game.worldBuilding.TerrianGenerator;

/**
 * Represents the current game.
 * @author slug
 *
 */
public class GameModeState implements IGameState, Runnable {


	private static final int MAP_UPDATE_RATE = 30;
	private static final int INFO_UPDATE_RATE = 120;
	private static final float DEFAULT_TILE_SIZE = 19;
	private static final float DEFAULT_TEXT_SIZE = 10;
	private static GameModeState instance;

	private long lastFPS;
	private int currentFPS;
	private int frames;

	private boolean viewHoldings;

	private TerrainObject[][] map;
	private Integer year;
	private ArrayList<GameObject> gameObjects;
	
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

	private int mapFrameCounter;
	private int infoFrameCounter;
	
	public static GameModeState getInstance(){
		if(instance == null){
			instance = new GameModeState();
		}
		return instance;
	}

	private GameModeState(){
		terrianGenerator = new TerrianGenerator();
		lastFPS = System.currentTimeMillis();
		gameObjects = new ArrayList<GameObject>();
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
		year = 100;
		
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
			gameObjects.add(g);
			for(TerrainObject t: map[y][x].getBiome().getContents()){
				if(t != null){
					t.setOwner(faction);
				}
			}
			y++;
		}
		
		
		for(int i = 0; i < 51; i++){
			advanceStep();
		}
		//************************************************

		long end = System.nanoTime();
		System.out.println("GenTime: " + (end - start)/1000000);
		loadedWorld = true;		
	}

	public TerrainObject[][] getMap(){
		return map;
	}
	
	public int getYear(){
		return year;
	}
	
	public void advanceStep(){
		year++;
		for(GameObject o : gameObjects){
			o.act();
		}
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
		else if(Keyboard.isKeyDown(Keyboard.KEY_I)){
			if(getMap()[currentYPos][currentXPos].getOwner() != null){
				FactionInformationState.getInstance().setFactionToDispaly(getMap()[currentYPos][currentXPos].getOwner());
				Game.getInstance().setCurrentGameState(FactionInformationState.getInstance());
			}
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			if(getMap()[currentYPos][currentXPos].getCurrentGameObject() != null){
				GameObjectInformationState.getInstance().setObjectToDetail(getMap()[currentYPos][currentXPos].getCurrentGameObject());
				Game.getInstance().setCurrentGameState(GameObjectInformationState.getInstance());
			}
		}
	}

	public void createImage(){		
		mapFrameCounter++;
		infoFrameCounter++;
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

		if(mapFrameCounter >= MAP_UPDATE_RATE ){
			mapFrameCounter = 0;
		}
		if(infoFrameCounter >= INFO_UPDATE_RATE){
			infoFrameCounter = 0;
		}
	}	

	private void drawMap(){
		//int gy = 30;
		//int gx;
		GL11.glPushMatrix();

		GL11.glTranslatef(0f, Display.getDisplayMode().getHeight() + 80 , 0f);
		//g.setFont(FontProvider.getInstance().getFont());
		//g.setFont(FontProvider.getInstance().getFont().deriveFont((float)Math.floor(19 * zoomScales[currentZoomIndex])));
		for(int y = currentYPos - 12 * (int) (1/zoomScales[currentZoomIndex]), my = 0; my < (25 * 1/zoomScales[currentZoomIndex]); y++,my++){
			//gx = 15;
			GL11.glPushMatrix();
			for(int x = currentXPos - 12 * (int) (1/zoomScales[currentZoomIndex]), mx = 0; mx < (25 * 1/zoomScales[currentZoomIndex]) ; x++, mx++){
				if(x < 0 || y < 0 || x >= getMap().length || y >= getMap().length ){
					//g.setColor(Color.BLACK);
					GL11.glColor3f(1f,1f,1f);
					//g.drawString(" ", gx, gy);
					TextRenderer.getInstance().drawSymbol(" ", DEFAULT_TILE_SIZE * zoomScales[currentZoomIndex]);
				}
				else{
					if(mapFrameCounter >= MAP_UPDATE_RATE){
						getMap()[y][x].nextTile();
					}
					if(viewHoldings && map[y][x].getOwner() != null){ //check if player wants to see owners of tiles
						//g.setColor(map[y][x].getOwner().getFactionColor().getColor());
						Color color = map[y][x].getOwner().getFactionColor().getColor();
						GL11.glColor3f(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f);
					}
					else{//default viewing
						//g.setColor(getMap()[y][x].getTile().getColor());
						Color color = map[y][x].getTile().getColor();
						GL11.glColor3f(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f);
					}
					//g.drawString(getMap()[y][x].getTile().getSymbol().toString(), gx, gy);
					//drawCharacter('a');
					TextRenderer.getInstance().drawSymbol(getMap()[y][x].getTile().getSymbol(),
							DEFAULT_TILE_SIZE * zoomScales[currentZoomIndex]);
				}
				//gx += g.getFont().getSize();
				GL11.glTranslatef(DEFAULT_TILE_SIZE * zoomScales[currentZoomIndex], 0, 0);

			}
			//gy += g.getFont().getSize();
			GL11.glPopMatrix();
			GL11.glTranslatef(0, - DEFAULT_TILE_SIZE * zoomScales[currentZoomIndex], 0);

		}

		GL11.glPopMatrix();
	}

	private void drawInfo(){

		float textSpace = 425; 
				//Display.getDisplayMode().getWidth() - DEFAULT_TILE_SIZE * 25;
		
		System.out.println();
		//int gx = 510;
		//int gy = 30;
		GL11.glPushMatrix();
		GL11.glTranslatef(DEFAULT_TILE_SIZE * 25, Display.getHeight() + 80, 0);

		//g.setFont(FontProvider.getInstance().getFont().deriveFont(10f));
		if(currentYPos > 0 && currentXPos > 0 && currentYPos < map.length && currentXPos < map.length){
			//g.drawString(getMap()[currentYPos][currentXPos].toString(),gx, gy);
			GL11.glPushMatrix();
			TextRenderer.getInstance().drawString(getMap()[currentYPos][currentXPos].toString(), DEFAULT_TEXT_SIZE,
					textSpace);
			GL11.glPopMatrix();
		}

		GL11.glTranslatef(0 , -DEFAULT_TEXT_SIZE, 0);
		//gy += 11;
		GL11.glPushMatrix();
		String out = "X: " + currentXPos.toString();
		TextRenderer.getInstance().drawString(out, DEFAULT_TEXT_SIZE, textSpace);
		GL11.glPopMatrix();
		//gx += 50;
		//g.drawString("Y: " + currentYPos.toString(), gx, gy);
		GL11.glPushMatrix();
		GL11.glTranslatef(DEFAULT_TEXT_SIZE * 6, 0, 0);
		out = "Y: " + currentYPos.toString();
		TextRenderer.getInstance().drawString(out, DEFAULT_TEXT_SIZE, textSpace);
		GL11.glPopMatrix();
		//gx -= 50;
		//gy += 20;
		GL11.glTranslatef(0, - DEFAULT_TEXT_SIZE, 0);
		GL11.glPushMatrix();
		if(getMap()[currentYPos][currentXPos].getOwner() != null){
			out = "Property of the " + getMap()[currentYPos][currentXPos].getOwner().toString() + " family  (i)";
			TextRenderer.getInstance().drawString(out, DEFAULT_TEXT_SIZE, textSpace);
			GL11.glTranslatef(DEFAULT_TEXT_SIZE, 0, 0);
		}
		else{
			out = "Unclaimed land";
			TextRenderer.getInstance().drawString(out, DEFAULT_TEXT_SIZE, textSpace);
		}
		GL11.glPopMatrix();

		
		if(infoFrameCounter >= INFO_UPDATE_RATE && getMap()[currentYPos][currentXPos].getGameObjects().size() > 0){
			getMap()[currentYPos][currentXPos].getNextGameObject();
			if(getMap()[currentYPos][currentXPos].getCurrentGameObject() instanceof GameObjectCursor){
				getMap()[currentYPos][currentXPos].getNextGameObject();
			}
		}
		
		GL11.glTranslatef(0, -DEFAULT_TEXT_SIZE * 4, 0);
		GL11.glPushMatrix();
		if(getMap()[currentYPos][currentXPos].getGameObjects().size() > 0){
			GL11.glPushMatrix();
			out = (getMap()[currentYPos][currentXPos].getCurrentGameObject().toString() + " 1 of " 
						+ getMap()[currentYPos][currentXPos].getGameObjects().size() +" objects on this tile");
			TextRenderer.getInstance().drawString(out, DEFAULT_TEXT_SIZE, textSpace);
			GL11.glPopMatrix();
			GL11.glTranslatef(0, -DEFAULT_TEXT_SIZE * 3, 0);
			
			String [] desc = getMap()[currentYPos][currentXPos].getCurrentGameObject().getStringDesc();
			for(int i = 0; i < desc.length; i++){
				GL11.glPushMatrix();
				String string = desc[i];
				if(i == 0){
					string += "  (d)";
				}
				TextRenderer.getInstance().drawString(string, DEFAULT_TEXT_SIZE, textSpace);
				GL11.glPopMatrix();
				GL11.glTranslatef(0, -DEFAULT_TEXT_SIZE, 0);
			}
		}
		GL11.glPopMatrix();
	
		GL11.glPushMatrix();
		GL11.glTranslatef(0, -410 , 0);
		if(viewHoldings){
			GL11.glColor3f(1, 0, 0);
			//gy = 480;
			//gx = 520;
			//g.drawString("Holdings View", gx, gy);
			GL11.glPushMatrix();
			out = "Holdings View";
			GL11.glTranslatef(DEFAULT_TEXT_SIZE, 0, 0);
			TextRenderer.getInstance().drawString(out, DEFAULT_TEXT_SIZE, textSpace);
			GL11.glPopMatrix();

			GL11.glColor3f(1, 1, 1);
		}
		//gy = 480;
		//gx = 980;
		if(System.currentTimeMillis() - lastFPS > 1000){
			currentFPS = frames;
			frames = 0;
			lastFPS += 1000;
		}
		Integer f = currentFPS;
		GL11.glTranslatef(300, 0, 0);
			TextRenderer.getInstance().drawString(f.toString(), DEFAULT_TEXT_SIZE, textSpace);
		//g.drawString(f.toString(), gx, gy);
		frames++;
		GL11.glPopMatrix();
		
		GL11.glPopMatrix();
		
	}

	private void drawLoading(){
		//int gy = 30;
		//int gx = 30;
		GL11.glPushMatrix();
		GL11.glTranslatef(0, Display.getDisplayMode().getHeight() + 60, 0);
		//g.setFont(FontProvider.getInstance().getFont());
		//g.drawString("Generating world", gx, gy);
		GL11.glPushMatrix();
			TextRenderer.getInstance().drawString("Generating World", 16, Display.getDisplayMode().getWidth());
		GL11.glPopMatrix();
		//gy += 20;
		GL11.glPushMatrix();
		for(String s: getGenStatus()){
			GL11.glTranslatef(0, -30, 0);
			//g.drawString(s, gx, gy);
			GL11.glPushMatrix();
			TextRenderer.getInstance().drawString(s, 16, Display.getDisplayMode().getWidth());
			GL11.glPopMatrix();
			//gy += 20;
		}
		GL11.glPopMatrix();
		//gy = 480;
		//gx = 500;
		if(mapFrameCounter >= MAP_UPDATE_RATE){
			if(currentLoadingString + 1 < loadingString.length){
				currentLoadingString++  ;
			}
			else{
				currentLoadingString = 0;
			}
		}
		GL11.glPushMatrix();
		GL11.glTranslatef(500, 50, 0);
		TextRenderer.getInstance().drawString(loadingString[currentLoadingString], 16, Display.getDisplayMode().getHeight());
		GL11.glPopMatrix();
		//g.drawString(loadingString[currentLoadingString], gx, gy);*/
		GL11.glPopMatrix();
	}

	private String[] getGenStatus(){
		String[] status = new String[4];
		status[0] = "Terrain Generation......" + terrianGenerator.getGenStatus().toString() + "%";
		status[1] = "test";
		status[2] = "test";
		status[3] = "test";
		return status;
	}

	private void drawCharacter(String c, float size){
		Texture tex = AsciiTextureGenerator.getInstance().getCharacterTexture(c);
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
