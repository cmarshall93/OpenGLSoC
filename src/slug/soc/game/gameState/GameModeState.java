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

import slug.soc.game.Game;
import slug.soc.game.GameCalendar;
import slug.soc.game.RandomProvider;
import slug.soc.game.gameObjects.Faction;
import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.GameObjectCastle;
import slug.soc.game.gameObjects.GameObjectCursor;
import slug.soc.game.gameObjects.GameObjectVillage;
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
	private static final int DEFAULT_GRID_SIZE = 25;
	private static GameModeState instance;

	private long lastFPS;
	private int currentFPS;
	private int frames;

	private boolean viewHoldings;
	private boolean viewResources;
	private boolean confirmNextTurnDialog;

	private TerrainObject[][] map;
	private ArrayList<GameObject> gameObjects;
	private Faction faction;

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
		faction = new Faction();
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
			g.setXAndY(x, y);
			gameObjects.add(g);

			int w = 7;
			int h = 7;

			for(int wi = (w/2) * -1 ;wi <= w/2 ;wi++){
				for(int hi = (h/2) * -1;hi <= h/2;hi++){
					if(map[y + hi][x + wi].getBiome() != null && map[y + hi][x + wi].getBiome().getContents() != null){
						map[y + hi][x + wi].setOwner(faction);
					}
				}
			}
		}
		//************************************************

		for(int i = 0;i < 999; i++){
			advanceStep();
		}

		long end = System.nanoTime();
		System.out.println("GenTime: " + (end - start)/1000000);
		loadedWorld = true;		
	}

	public TerrainObject[][] getMap(){
		return map;
	}

	public void advanceStep(){
		GameCalendar.getInstance().advanceDay();
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
			Game.getInstance().changeToNextGameState(PauseGameState.getInstance());
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_H)){//h
			if(!viewResources){
				viewHoldings = !viewHoldings;
			}
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_I)){
			if(getMap()[currentYPos][currentXPos].getOwner() != null){
				FactionInformationState.getInstance().setFactionToDispaly(getMap()[currentYPos][currentXPos].getOwner());
				Game.getInstance().changeToNextGameState(FactionInformationState.getInstance());
			}
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			if(getMap()[currentYPos][currentXPos].getCurrentGameObject() != null && !(getMap()[currentYPos][currentXPos].getCurrentGameObject() instanceof GameObjectCursor)){
				GameObjectInformationState.getInstance().setObjectToDetail(getMap()[currentYPos][currentXPos].getCurrentGameObject());
				Game.getInstance().changeToNextGameState(GameObjectInformationState.getInstance());
			}
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_T)){
			Game.getInstance().changeToNextGameState(DatesListState.getInstance());
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_R)){
			if(!viewHoldings){
				viewResources = !viewResources;
			}
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_N)){
			confirmNextTurnDialog = !confirmNextTurnDialog;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_Y)){
			if(confirmNextTurnDialog){
				advanceStep();
				confirmNextTurnDialog = !confirmNextTurnDialog;
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
			drawTopBar();
			if(confirmNextTurnDialog){
				drawConfirmBox();
			}
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

		GL11.glTranslatef(0f, 20 , 0f);
		//g.setFont(FontProvider.getInstance().getFont());
		//g.setFont(FontProvider.getInstance().getFont().deriveFont((float)Math.floor(19 * zoomScales[currentZoomIndex])));
		for(int y = currentYPos - (DEFAULT_GRID_SIZE / 2) * (int) (1/zoomScales[currentZoomIndex]), my = 0; my < (DEFAULT_GRID_SIZE * 1/zoomScales[currentZoomIndex]); y++,my++){
			//gx = 15;
			GL11.glPushMatrix();
			for(int x = currentXPos - (DEFAULT_GRID_SIZE / 2) * (int) (1/zoomScales[currentZoomIndex]), mx = 0; mx < (DEFAULT_GRID_SIZE * 1/zoomScales[currentZoomIndex]) ; x++, mx++){
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
					else if(viewResources && map[y][x].hasResources()){
						GL11.glColor3f(1f,1f,1f);
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
			GL11.glTranslatef(0, DEFAULT_TILE_SIZE * zoomScales[currentZoomIndex], 0);

		}

		GL11.glPopMatrix();
	}

	private void drawInfo(){

		float textSpace = Display.getWidth() - (DEFAULT_TILE_SIZE * DEFAULT_GRID_SIZE);
		//Display.getDisplayMode().getWidth() - DEFAULT_TILE_SIZE * 25;
		//int gx = 510;
		//int gy = 30;
		GL11.glPushMatrix();
		GL11.glTranslatef(DEFAULT_TILE_SIZE * DEFAULT_GRID_SIZE, 20 + (DEFAULT_TEXT_SIZE / 2), 0);

		//g.setFont(FontProvider.getInstance().getFont().deriveFont(10f));
		if(currentYPos > 0 && currentXPos > 0 && currentYPos < map.length && currentXPos < map.length){
			//g.drawString(getMap()[currentYPos][currentXPos].toString(),gx, gy);
			GL11.glPushMatrix();
			TextRenderer.getInstance().drawString(getMap()[currentYPos][currentXPos].toString(), DEFAULT_TEXT_SIZE,
					textSpace);
			GL11.glPopMatrix();
		}

		GL11.glTranslatef(0 , DEFAULT_TEXT_SIZE, 0);
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
		GL11.glTranslatef(0, DEFAULT_TEXT_SIZE, 0);
		GL11.glPushMatrix();
		if(getMap()[currentYPos][currentXPos].getOwner() != null){
			GL11.glPushMatrix();
			out = "Property of the " + getMap()[currentYPos][currentXPos].getOwner().toString() + " family  (i)";
			TextRenderer.getInstance().drawString(out, DEFAULT_TEXT_SIZE, textSpace);
			GL11.glTranslatef(DEFAULT_TEXT_SIZE, 0, 0);
			GL11.glPopMatrix();
		}
		else{
			GL11.glPushMatrix();
			out = "Unclaimed land";
			TextRenderer.getInstance().drawString(out, DEFAULT_TEXT_SIZE, textSpace);
			GL11.glPopMatrix();
		}
		GL11.glTranslatef(0,DEFAULT_TEXT_SIZE * 2,0);
		TextRenderer.getInstance().drawString(getMap()[currentYPos][currentXPos].getDesc(), DEFAULT_TEXT_SIZE, textSpace);
		GL11.glPopMatrix();


		if(infoFrameCounter >= INFO_UPDATE_RATE && getMap()[currentYPos][currentXPos].getNumberOfGameObjects() > 0){
			getMap()[currentYPos][currentXPos].getNextGameObject();
			if(getMap()[currentYPos][currentXPos].getCurrentGameObject() instanceof GameObjectCursor){
				getMap()[currentYPos][currentXPos].getNextGameObject();
			}
		}

		GL11.glTranslatef(0, DEFAULT_TEXT_SIZE * 4, 0);
		GL11.glPushMatrix();
		if(getMap()[currentYPos][currentXPos].getNumberOfGameObjects()> 0 && !(getMap()[currentYPos][currentXPos].getCurrentGameObject() instanceof GameObjectCursor)){
			GL11.glPushMatrix();
			out = (getMap()[currentYPos][currentXPos].getCurrentGameObject().toString() + " 1 of " 
					+ getMap()[currentYPos][currentXPos].getNumberOfGameObjects() +" objects on this tile");
			TextRenderer.getInstance().drawString(out, DEFAULT_TEXT_SIZE, textSpace);
			GL11.glPopMatrix();
			GL11.glTranslatef(0, DEFAULT_TEXT_SIZE * 3, 0);

			String [] desc = getMap()[currentYPos][currentXPos].getCurrentGameObject().getStringDesc();
			for(int i = 0; i < desc.length; i++){
				GL11.glPushMatrix();
				String string = desc[i];
				if(i == 0){
					string += "  (d)";
				}
				TextRenderer.getInstance().drawString(string, DEFAULT_TEXT_SIZE, textSpace);
				GL11.glPopMatrix();
				GL11.glTranslatef(0, DEFAULT_TEXT_SIZE, 0);
			}
		}
		GL11.glPopMatrix();
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslatef(0, Display.getDisplayMode().getHeight() - (DEFAULT_TEXT_SIZE * 2) , 0);
		if(viewHoldings){
			GL11.glColor3f(1, 0, 0);
			//gy = 480;
			//gx = 520;
			//g.drawString("Holdings View", gx, gy);
			//GL11.glPushMatrix();
			out = "(Holdings View)";
			GL11.glTranslatef(DEFAULT_TEXT_SIZE, 0, 0);
			TextRenderer.getInstance().drawString(out, DEFAULT_TEXT_SIZE, textSpace);
			//GL11.glPopMatrix();

			GL11.glColor3f(1, 1, 1);
		}
		if(cursorActive){
			TextRenderer.getInstance().drawString(" (Cursor Active)", DEFAULT_TEXT_SIZE, textSpace);
		}
		if(viewResources){
			TextRenderer.getInstance().drawString(" (Resources View)", DEFAULT_TEXT_SIZE, textSpace);
		}
		//gy = 480;
		//gx = 980;
		if(System.currentTimeMillis() - lastFPS > 1000){
			currentFPS = frames;
			frames = 0;
			lastFPS += 1000;
		}
		Integer f = currentFPS;
		GL11.glTranslatef(0, 0, 0);
		TextRenderer.getInstance().drawString(f.toString(), DEFAULT_TEXT_SIZE, textSpace);
		//g.drawString(f.toString(), gx, gy);
		frames++;
		//GL11.glTranslatef(-300f, DEFAULT_TEXT_SIZE, 0);
		//TextRenderer.getInstance().drawString(GameCalendar.getInstance().getCurrentDateAsString(), DEFAULT_TEXT_SIZE, textSpace);

		//GL11.glPopMatrix();

		GL11.glPopMatrix();

	}

	private void drawTopBar(){
		GL11.glPushMatrix();
		GL11.glTranslatef(0,DEFAULT_TEXT_SIZE/2, 0);
		GL11.glColor3f(1f, 1f, 0f);
		TextRenderer.getInstance().drawString(" Gold : " + faction.getMoney(), 12, 
				Display.getDisplayMode().getWidth());
		GL11.glColor3f(1f,1f,1f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(0,0,0);
		GL11.glVertex3f(900, 0, 0);
		GL11.glVertex3f(900, 20, 0);
		GL11.glVertex3f(0, 20, 0);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(Display.getDisplayMode().getWidth() - (GameCalendar.getInstance().getCurrentDateAsString().length() * DEFAULT_TEXT_SIZE) - 50
				, DEFAULT_TEXT_SIZE/2, 0);
		TextRenderer.getInstance().drawString(GameCalendar.getInstance().getCurrentDateAsString(), 12,
				Display.getDisplayMode().getWidth());
		GL11.glPopMatrix();
	}

	private void drawConfirmBox(){
		GL11.glPushMatrix();
		GL11.glTranslatef((DEFAULT_TILE_SIZE * DEFAULT_GRID_SIZE)/2 - 120, Display.getDisplayMode().getHeight()/2 - 35, 0);
		GL11.glColor3f(1f,1f,1f);
		GL11.glBegin(GL11.GL_POLYGON);
		GL11.glVertex2f(0,0);
		GL11.glVertex2f(0,70);
		GL11.glVertex2f(260,70);
		GL11.glVertex2f(260,0);
		GL11.glEnd();	

		TextRenderer.getInstance().drawString(" Do you want to advance to the next turn? ", 16, 260);
		GL11.glTranslatef(-(4*43),20,0);
		TextRenderer.getInstance().drawString( "Yes (y) No(n)", 16, 260);

		GL11.glPopMatrix();
	}

	private void drawLoading(){
		//int gy = 30;
		//int gx = 30;
		GL11.glPushMatrix();
		GL11.glTranslatef(0, 20, 0);
		//g.setFont(FontProvider.getInstance().getFont());
		//g.drawString("Generating world", gx, gy);
		GL11.glPushMatrix();
		TextRenderer.getInstance().drawString("Generating World", 16, Display.getDisplayMode().getWidth());
		GL11.glPopMatrix();
		//gy += 20;
		GL11.glPushMatrix();
		for(String s: getGenStatus()){
			GL11.glTranslatef(0, 30, 0);
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
		GL11.glTranslatef(500, Display.getHeight(), 0);
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
}
