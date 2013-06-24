package slug.soc.game.gameState;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import slug.soc.game.Game;
import slug.soc.game.GameCalendar;
import slug.soc.game.RandomProvider;
import slug.soc.game.gameObjects.Faction;
import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.GameObjectCastle;
import slug.soc.game.gameObjects.GameObjectCursor;
import slug.soc.game.gameObjects.GameObjectPerson;
import slug.soc.game.gameObjects.GameObjectTown;
import slug.soc.game.gameObjects.MovementOrder;
import slug.soc.game.gameObjects.MovementOrderCoordinate;
import slug.soc.game.gameObjects.TerrainObject;
import slug.soc.game.gameObjects.TerrainObjectGrassPlain;
import slug.soc.game.gameObjects.TerrainObjectWater;
import slug.soc.game.gameObjects.tasks.MoveTask;
import slug.soc.game.rendering.TextRenderer;
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
	private static final int MINIMAP_RATIO = 4;
	private static GameModeState instance;

	private long lastFPS;
	private int currentFPS;
	private int frames;

	private boolean viewHoldings;
	private boolean viewResources;
	private boolean confirmNextTurnDialog;
	private boolean fogOfWar;
	private boolean movingObject;

	private ArrayList<String> notifications;
	private boolean showingNotifications;

	private boolean buildingRoad;

	private TerrainObject[][] map;
	private TerrainObject[][] miniMap;
	private ArrayList<GameObject> gameObjects;
	private Faction faction;

	private GameObject objectOfFocus;
	private MovementOrder movementOrder;

	private TerrianGenerator terrianGenerator;
	private GameObjectCursor cursor = new GameObjectCursor();
	private Integer currentXPos;
	private Integer currentYPos;
	private float[] zoomScales = {
			1.0f, 0.5f, 0.3f
	};
	private boolean zoomedOut;

	private boolean cursorActive = false;
	private boolean loadedWorld = false;

	private String[] loadingString = {".", "..", "..."};
	private int currentLoadingString = 0;

	private int currentZoomIndex = 0;

	private int mapFrameCounter;
	private int infoFrameCounter;
	private boolean interactObject;

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
		notifications = new ArrayList<String>();
	}

	public void run(){
		generateWorld();
	}

	public void generateWorld(){
		long start = System.nanoTime();

		map = terrianGenerator.testGenerateMapMultiCont(100, 100);
		currentXPos = 50;
		currentYPos = 50;

		faction = new Faction();


		miniMap = new TerrainObject[map.length/ MINIMAP_RATIO][map.length / MINIMAP_RATIO];
		for(int x2 = 0, miniX = 0;x2 < map.length;x2+=MINIMAP_RATIO, miniX++){
			for(int y2 = 0, miniY = 0; y2 < map.length;y2+=MINIMAP_RATIO, miniY++){

				int landCount = 0;
				int seaCount = 0;

				for(int a = 0; a < MINIMAP_RATIO;a++){
					for(int b = 0; b < MINIMAP_RATIO;b++){
						if(map[y2 + a][x2 + b] instanceof TerrainObjectWater){
							seaCount += 1;
						}
						else{
							landCount += 1;
						}
					}
				}

				if(seaCount >= landCount){
					miniMap[miniY][miniX] = new TerrainObjectWater();
				}
				else{
					miniMap[miniY][miniX] = new TerrainObjectGrassPlain();
				}
			}
		}

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

	public Integer getCurrentXPos() {
		return currentXPos;
	}

	public Integer getCurrentYPos() {
		return currentYPos;
	}

	public Faction getFaction() {
		return faction;
	}

	public void advanceStep(){
		for(GameObject o : gameObjects){
			o.act();
		}
		faction.updateFov();
		if(notifications.size() > 0){
			showingNotifications = true;
		}
		if(GameCalendar.getInstance().getCurrentDate().getEvents().size() > 0){
			GameCalendar.getInstance().addKeyDate(GameCalendar.getInstance().getCurrentDate());
		}
		GameCalendar.getInstance().advanceDay();
	}

	public void addNotification(String n){
		notifications.add(n);
	}

	public void checkInput(){
		if(loadedWorld){
			boolean canMoveCamera = false;
			if(Keyboard.isKeyDown(Keyboard.KEY_UP)){//up
				if(currentYPos > 0){
					if(movingObject){
						if(movementOrder.canMove(0,-1)){
							movementOrder.moveY(-1);
							//currentYPos--;
							canMoveCamera = true;
						}
					}
					else{
						canMoveCamera = true;
					}
				}
				if(canMoveCamera && !zoomedOut){
					currentYPos--;
					if(cursorActive){
						map[currentYPos + 1][currentXPos].removeGameObject(cursor);
						map[currentYPos][currentXPos].addGameObject(cursor);
					}
				}
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){//down
				if(currentYPos < getMap().length - 1){
					if(movingObject){
						if(movementOrder.canMove(0,1)){
							movementOrder.moveY(1);
							canMoveCamera = true;
						}
					}
					else{
						canMoveCamera = true;
					}
				}
				if(canMoveCamera && !zoomedOut){
					currentYPos++;
					if(cursorActive){
						map[currentYPos - 1][currentXPos].removeGameObject(cursor);
						map[currentYPos][currentXPos].addGameObject(cursor);
					}
				}
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				if(currentXPos < getMap().length -1){
					if(movingObject){
						if(movementOrder.canMove(1,0)){
							movementOrder.moveX(1);
							canMoveCamera = true;
						}else{
							canMoveCamera = false;
						}
					}
					else{
						canMoveCamera = true;
					}
				}
				if(canMoveCamera && !zoomedOut){
					currentXPos++;
					if(cursorActive){
						map[currentYPos][currentXPos - 1].removeGameObject(cursor);
						map[currentYPos][currentXPos].addGameObject(cursor);
					}
				}
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){//left
				if(currentXPos > 0){
					if(movingObject){
						if(movementOrder.canMove(-1,0)){
							movementOrder.moveX(-1);
							canMoveCamera = true;
						}
					}
					else{
						canMoveCamera = true;
					}
				}
				if(canMoveCamera  && !zoomedOut){
					currentXPos--;
					if(cursorActive){
						map[currentYPos][currentXPos + 1].removeGameObject(cursor);
						map[currentYPos][currentXPos].addGameObject(cursor);
					}
				}
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_LBRACKET)){//open bracket

			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_RBRACKET)){//close bracket
				if(getMap()[currentYPos][currentXPos].getNumberOfGameObjects() > 0){
					getMap()[currentYPos][currentXPos].getNextGameObject();
					if(getMap()[currentYPos][currentXPos].getCurrentGameObject() instanceof GameObjectCursor){
						getMap()[currentYPos][currentXPos].getNextGameObject();
					}
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
				if(movingObject){
					movingObject = false;
				}
				if(interactObject){
					interactObject = false;
				}
				if(buildingRoad){
					buildingRoad = false;
				}
				else{
					Game.getInstance().changeToNextGameState(PauseGameState.getInstance());
				}
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
			else if(Keyboard.isKeyDown(Keyboard.KEY_F)){
				fogOfWar = !fogOfWar;
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_M)){
				if(getMap()[currentYPos][currentXPos].getNumberOfGameObjects() > 0 && !movingObject && !interactObject && !buildingRoad){
					if(getMap()[currentYPos][currentXPos].getCurrentGameObject() instanceof GameObjectPerson && !movingObject){
						if(getMap()[currentYPos][currentXPos].getCurrentGameObject().getOwner().equals(faction)){
							movingObject = true;
							objectOfFocus = getMap()[currentYPos][currentXPos].getCurrentGameObject();
							movementOrder = new MovementOrder(5);
						}
					}
				}
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
				if(movingObject){
					objectOfFocus.giveOrders(movementOrder);
					movingObject = false;
				}
				if(interactObject && getMap()[currentYPos][currentXPos].getNumberOfGameObjects() > 0){
					InteractState.getInstance().setInteractions(getMap()[currentYPos][currentXPos].getCurrentGameObject().getInteractions(),
							objectOfFocus, getMap()[currentYPos][currentXPos].getCurrentGameObject());
					Game.getInstance().changeToNextGameState(InteractState.getInstance());
					interactObject = false;
				}
				if(showingNotifications){
					if(notifications.size() > 0){
						notifications.remove(0);
					}
					if(notifications.size() == 0){
						showingNotifications = false;
					}
				}
				if(buildingRoad){
					if(getMap()[currentYPos][currentXPos].getNumberOfGameObjects() > 0){
						buildingRoad = false;
						terrianGenerator.buildRoad(objectOfFocus, getMap()[currentYPos][currentXPos].getCurrentGameObject());
					}
				}
			}

			//here for testing
			else if(Keyboard.isKeyDown(Keyboard.KEY_V)){
				if(getMap()[currentYPos][currentXPos].getNumberOfGameObjects() > 0 && !movingObject && !interactObject  && !buildingRoad){
					buildingRoad = true;
					objectOfFocus = getMap()[currentYPos][currentXPos].getCurrentGameObject();
				}
			}
			//

			else if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
				zoomedOut = !zoomedOut;
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_E)){
				if(getMap()[currentYPos][currentXPos].getNumberOfGameObjects() > 0 && !movingObject && !interactObject  && !buildingRoad){
					if(getMap()[currentYPos][currentXPos].getCurrentGameObject() instanceof GameObjectPerson){
						objectOfFocus =  getMap()[currentYPos][currentXPos].getCurrentGameObject();
						interactObject = true;
					}
				}
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_B)){
				if(getMap()[currentYPos][currentXPos].isBuildable()){
					BuildModeState.getInstance().setTerrainObject(getMap()[currentYPos][currentXPos]);
					Game.getInstance().changeToNextGameState(BuildModeState.getInstance());
				}
			}

			//----------------------TESTING STUFF-----------------------------
			else if(Keyboard.isKeyDown(Keyboard.KEY_1)){
				if(getMap()[currentYPos][currentXPos].isBuildable()){
					GameObjectPerson p = new GameObjectPerson(faction.getFactionColor().getColor(),faction,null,null,currentXPos,currentYPos);
					if(faction.getHeadOfFamily() == null){
						faction.setHeadOfFamily(p);
					}
					addNewFactionObject(currentXPos,currentYPos,p);
				}
			}
		}

	}

	public void createImage(){
		mapFrameCounter++;
		infoFrameCounter++;
		if(loadedWorld){
			if(!zoomedOut){
				drawMap();
			}
			else{
				drawMiniMap();
			}
			//g.setColor(Color.WHITE);
			GL11.glColor3f(1f, 1f, 1f);
			//g.drawLine(500, 0, 500, 500);
			drawInfo();
			drawTopBar();
			if(confirmNextTurnDialog){
				drawConfirmBox();
			}
			else if(showingNotifications){
				drawNotificationBox(notifications.get(0));
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

		//build a list of coords that are part of a path if there is one to display
		boolean[][] movementCoords = new boolean[map.length][map.length];
		if(map[currentYPos][currentXPos].getGameObjects().size() > 0){
			if(map[currentYPos][currentXPos].getCurrentGameObject().hasOrders()){
				MovementOrderCoordinate moveCoord = map[currentYPos][currentXPos].getCurrentGameObject().getOrder().getFirstCoord();
				int xCoord = currentXPos;
				int yCoord = currentYPos;
				while(moveCoord != null){
					xCoord = xCoord + moveCoord.getX();
					yCoord = yCoord + moveCoord.getY();
					if(xCoord < map.length && yCoord < map.length){
						movementCoords[yCoord][xCoord] = true;
					}
					moveCoord = moveCoord.getNextCoord();
				}
			}
		}
		for(int y = currentYPos - (DEFAULT_GRID_SIZE / 2) * (int) (1/zoomScales[currentZoomIndex]), my = 0; my < (DEFAULT_GRID_SIZE * 1/zoomScales[currentZoomIndex]); y++,my++){
			//gx = 15;
			GL11.glPushMatrix();
			for(int x = currentXPos - (DEFAULT_GRID_SIZE / 2) * (int) (1/zoomScales[currentZoomIndex]), mx = 0; mx < (DEFAULT_GRID_SIZE * 1/zoomScales[currentZoomIndex]) ; x++, mx++){
				if(x < 0 || y < 0 || x >= getMap().length || y >= getMap().length ){

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
					if(fogOfWar){
						if(faction.getFov()[y][x] == false){
							Color color = getMap()[y][x].getTile().getColor();
							GL11.glColor3f(/*(color.getRed()/255.0f) -*/ 0.4f, /*(color.getGreen()/255.0f) -*/ 0.4f,
									/*color.getBlue()/255.0f) -*/ 0.4f);
						}
					}
					if(cursorActive && y ==  currentYPos && x == currentXPos){
						Color color = map[y][x].getTile().getColor();
						GL11.glColor3f(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f);

					}
					if(movementCoords[y][x] == true){
						GL11.glColor3f(0.5f,1f,1f);
					}
					//g.drawString(getMap()[y][x].getTile().getSymbol().toString(), gx, gy);
					//drawCharacter('a');
					if(!fogOfWar){
						TextRenderer.getInstance().drawSymbol(getMap()[y][x].getTile().getSymbol(),
								DEFAULT_TILE_SIZE * zoomScales[currentZoomIndex]);
					}
					else{
						if(faction.getFov()[y][x] == true){
							TextRenderer.getInstance().drawSymbol(getMap()[y][x].getTile().getSymbol(),
									DEFAULT_TILE_SIZE * zoomScales[currentZoomIndex]);
						}
						else{
							TextRenderer.getInstance().drawSymbol(getMap()[y][x].getBaseTile().getSymbol(),
									DEFAULT_TILE_SIZE * zoomScales[currentZoomIndex]);
						}
					}
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

	private void drawMiniMap(){
		GL11.glPushMatrix();
		GL11.glTranslatef(0f, 20 , 0f);
		for(int y = 0;y < miniMap.length; y++){
			GL11.glPushMatrix();
			for(int x = 0; x < miniMap.length; x++){
				if(x < 0 || y < 0 || x >= miniMap.length || y >= miniMap.length){
				}
				else{
					Color color = miniMap[y][x].getTile().getColor();
					GL11.glColor3f(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f);
					TextRenderer.getInstance().drawSymbol(miniMap[y][x].getBaseTile().getSymbol(),
							DEFAULT_TILE_SIZE);
				}
				GL11.glTranslatef(DEFAULT_TILE_SIZE, 0, 0);

			}
			GL11.glPopMatrix();
			GL11.glTranslatef(0, DEFAULT_TILE_SIZE, 0);
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

		GL11.glTranslatef(0, DEFAULT_TEXT_SIZE * 5, 0);
		GL11.glPushMatrix();
		if(getMap()[currentYPos][currentXPos].getGameObjects().size() > 0 && !(getMap()[currentYPos][currentXPos].getCurrentGameObject() instanceof GameObjectCursor) && !movingObject){
			if(fogOfWar){
				if(faction.getFov()[currentYPos][currentXPos] = true){
					drawGameObjectDetails(textSpace);
				}
			}
			else{
				drawGameObjectDetails(textSpace);
			}
		}
		else if(movingObject){
			GL11.glPushMatrix();
			GL11.glTranslatef(0, DEFAULT_TEXT_SIZE, 0);
			TextRenderer.getInstance().drawString("Moving object : " + objectOfFocus.toString(),
					DEFAULT_TEXT_SIZE, textSpace);
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslatef(DEFAULT_TILE_SIZE * DEFAULT_GRID_SIZE, Display.getDisplayMode().getHeight() - (DEFAULT_TEXT_SIZE * 2) , 0);
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
		if(fogOfWar){
			TextRenderer.getInstance().drawString(" (Fog Of War)", DEFAULT_TEXT_SIZE, textSpace);
		}
		if(movingObject){
			TextRenderer.getInstance().drawString(" (Moving Object)", DEFAULT_TEXT_SIZE, textSpace);
		}
		if(interactObject){
			TextRenderer.getInstance().drawString(" (Interacting)", DEFAULT_TEXT_SIZE, textSpace);
		}
		if(buildingRoad){
			TextRenderer.getInstance().drawString(" (Building Road)", DEFAULT_TEXT_SIZE, textSpace);
		}
		//gy = 480;
		//gx = 980;
		if(System.currentTimeMillis() - lastFPS > 1000){
			currentFPS = frames;
			frames = 0;
			lastFPS += 1000;
		}
		Integer f = currentFPS;
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(Display.getDisplayMode().getWidth() - 50,Display.getDisplayMode().getHeight() - (DEFAULT_TEXT_SIZE * 2), 0);
		TextRenderer.getInstance().drawString(f.toString(), DEFAULT_TEXT_SIZE, textSpace);
		//g.drawString(f.toString(), gx, gy);
		frames++;
		//GL11.glTranslatef(-300f, DEFAULT_TEXT_SIZE, 0);
		//TextRenderer.getInstance().drawString(GameCalendar.getInstance().getCurrentDateAsString(), DEFAULT_TEXT_SIZE, textSpace);
		GL11.glPopMatrix();
	}


	private void drawGameObjectDetails(float textSpace){
		GL11.glPushMatrix();
		String out = (getMap()[currentYPos][currentXPos].getCurrentGameObject().toString() + " 1 of " 
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

		GL11.glPushMatrix();
		String familyName = faction.toString();
		GL11.glTranslatef(Display.getDisplayMode().getWidth()/2 - (familyName.length() * 12),
				DEFAULT_TEXT_SIZE/2,0);
		TextRenderer.getInstance().drawString(familyName, 12, Display.getDisplayMode().getWidth());
		GL11.glPopMatrix();
	}

	private void drawConfirmBox(){
		GL11.glPushMatrix();
		drawBox();
		TextRenderer.getInstance().drawString(" Do you want to advance to the next turn? ", 16, 260);
		GL11.glTranslatef(-(4*43),20,0);
		TextRenderer.getInstance().drawString( "Yes (y) No(n)", 16, 260);

		GL11.glPopMatrix();
	}

	private void drawNotificationBox(String notification){
		GL11.glPushMatrix();
		drawBox();
		TextRenderer.getInstance().drawString(notification, DEFAULT_TEXT_SIZE, 260);
		GL11.glPopMatrix();
	}

	private void drawBox(){
		GL11.glTranslatef((DEFAULT_TILE_SIZE * DEFAULT_GRID_SIZE)/2 - 120, Display.getDisplayMode().getHeight()/2 - 35, 0);
		GL11.glColor3f(1f,1f,1f);
		GL11.glBegin(GL11.GL_POLYGON);
		GL11.glVertex2f(0,0);
		GL11.glVertex2f(0,70);
		GL11.glVertex2f(260,70);
		GL11.glVertex2f(260,0);
		GL11.glEnd();
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

	public void moveFactionObject(int dx, int dy, GameObject g){
		int x = g.getX();
		int y = g.getY();
		map[y][x].removeGameObject(g);
		map[y+dy][x+dx].addGameObject(g);
		g.setXAndY(x+dx, y+dy);
		System.out.println((y+dy) + " : " + (x+dx));
	}

	private void addFactionObject(int x, int y, GameObject g){
		map[y][x].addGameObject(g);
		g.setXAndY(x, y);
		gameObjects.add(g);

		int w = 12;
		int h = 12;


		for(int wi = (w/2) * -1 ;wi <= w/2 ;wi++){
			for(int hi = (h/2) * -1;hi <= h/2;hi++){
				if(y + hi >= 0 && y + hi < map.length && x + wi >= 0 && x + wi < map.length){
					if(map[y + hi][x + wi].getBiome() != null && map[y + hi][x + wi].getBiome().getContents() != null){
						if((hi != ((h/2) * -1) || wi != ((w/2) * -1)) && (hi != h/2 || wi != w/2) && (hi != h/2 || wi != ((w/2) * -1)) && (hi != ((h/2) * -1) || wi != w/2)){
							map[y + hi][x + wi].setOwner(g.getOwner());
						}
					}
				}
			}
		}
	}

	public void addNewFactionObject(int x, int y, GameObject g){
		g.getOwner().getHoldings().add(g);
		addFactionObject(x,y,g);
	}
	
	public void removeGameObject(GameObject g){
		g.getOwner().getHoldings().remove(g);
		map[g.getY()][g.getX()].removeGameObject(g);
	}
}
