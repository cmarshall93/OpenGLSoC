package slug.soc.game.worldBuilding;


import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.Path.Step;

import slug.soc.game.Pathfinder;
import slug.soc.game.RandomProvider;
import slug.soc.game.gameObjects.*;
import slug.soc.game.gameState.GameModeState;


public class TerrianGenerator {

	private static final int MOUNTAIN_CONSTANT = 10;
	private static final int NUMBER_OF_FORESTS = 30;

	private TerrainObject[] temperateTerrain = {
			new TerrainObjectWater(),
			new TerrainObjectGrassPlain(),
			new TerrainObjectGrassHill(),
	};
	private TerrainObject[] articTerrain = {
			new TerrainObjectWater(),
			new TerrainObjectSnowPlain(),
			new TerrainObjectSnowHill(),
	};

	private int genStatus;

	private ArrayList<LinkedList<Point>> rivers = new ArrayList<LinkedList<Point>>();

	private int[][] forestMap;

	public TerrianGenerator(){
	}

	public Integer getGenStatus(){
		return genStatus;
	}

	private TerrainObject[][] constructTerrainMap(int[][] intMap){

		TerrainObject[][] map = new TerrainObject[intMap.length][intMap.length];

		for(int y = 0; y < map.length; y++){
			for(int x = 0; x < map.length; x++){
				if(y < map.length/101 || y > map.length - (map.length/101)){
					try {
						if(intMap[y][x] < articTerrain.length){
							map[y][x] = articTerrain[intMap[y][x]].getClass().newInstance();
						}
						else{
							map[y][x] = articTerrain[articTerrain.length - (1 + RandomProvider.getInstance().nextInt(2))].getClass().newInstance();
						}
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				else{
					try {
						if(intMap[y][x] > MOUNTAIN_CONSTANT){
							map[y][x] = new TerrainObjectMountain();
						}
						else if(intMap[y][x] < temperateTerrain.length){
							map[y][x] = temperateTerrain[intMap[y][x]].getClass().newInstance();
						}
						else{
							map[y][x] = new TerrainObjectGrassPlain();
						}
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		for(int y = 0;y < forestMap.length; y++){
			for(int x = 0; x < forestMap.length; x++){
				if(forestMap[y][x] == 1){
					map[y][x] = new TerrainObjectForest();
				}
			}
		}
		genStatus = 80;
		setupRiver(map);
		genStatus = 90;
		generateBiomes(map);
		genStatus = 100;
		return map;
	}


	private void setupRiver(TerrainObject[][] map){
		for(LinkedList<Point> l : rivers){

			for(ListIterator<Point> itr = l.listIterator(); itr.hasNext();itr.next()) {

				Point p = (Point) itr.next();
				itr.previous();//return to p
				Point next = null;
				Point prev = null;

				if(itr.hasPrevious()){
					prev = itr.previous();//previous river part;
					itr.next();//return to p
				}

				itr.next();
				if(itr.hasNext()){
					next = itr.next();//next river part
					itr.previous();
				}
				itr.previous();

				if(prev != null && next != null){
					if(prev.getY() != p.getY() && next.getY() != p.getY()){	//if next and previous are above and below.
						map[(int) p.getY()][(int) p.getX()] = new TerrainObjectRiverVertical();
					}

					if(prev.getY() < p.getY() && next.getY() == p.getY() && next.getX() < p.getX()){//if previous is lower and next is to the left
						map[(int) p.getY()][(int) p.getX()] = new TerrainObjectRiverBottomRightCorner();
					}
					if(prev.getY() < p.getY() && next.getY() == p.getY() && next.getX() > p.getX()){//if previous is lower and next is to the right
						map[(int) p.getY()][(int) p.getX()] = new TerrainObjectRiverBottomLeftCorner();
					}
					if(prev.getY() > p.getY() && next.getY() == p.getY() && next.getX() < p.getX()){//if previous is higher and next is to the left
						map[(int) p.getY()][(int) p.getX()] = new TerrainObjectRiverTopRightCorner();
					}
					if(prev.getY() > p.getY() && next.getY() == p.getY() && next.getX() > p.getX()){//if previous is higher and next is to the right
						map[(int) p.getY()][(int) p.getX()] = new TerrainObjectRiverTopLeftCorner();
					}

					if(next.getY() < p.getY() && prev.getY() == p.getY() && prev.getX() < p.getX()){//if next is lower and previous is to the left
						map[(int) p.getY()][(int) p.getX()] = new TerrainObjectRiverBottomRightCorner();
					}
					if(next.getY() < p.getY() && prev.getY() == p.getY() && prev.getX() > p.getX()){//if next is lower and previous is to the right
						map[(int) p.getY()][(int) p.getX()] = new TerrainObjectRiverBottomLeftCorner();
					}
					if(next.getY() > p.getY() && prev.getY() == p.getY() && prev.getX() < p.getX()){//if next is higher and previous is to the left
						map[(int) p.getY()][(int) p.getX()] = new TerrainObjectRiverTopRightCorner();
					}
					if(next.getY() > p.getY() && prev.getY() == p.getY() && prev.getX() > p.getX()){//if next is higher and previous is to the right
						map[(int) p.getY()][(int) p.getX()] = new TerrainObjectRiverTopLeftCorner();
					}			

					if(prev.getX() != p.getX() && next.getX() != p.getX()){
						map[(int) p.getY()][(int) p.getX()] = new TerrainObjectRiverHorizontal();
					}
				}
				else if(next == null && prev != null){
				}
				else{
					map[(int) p.getY()][(int) p.getX()] = new TerrainObjectRiverSource();
				}
			}
		}
	}

	private int[][] simulateAnt(int[][] intMap, int cx, int cy, int l){
		for(int i = 0, c = l;i < c ; i++){
			if(cy < 0 || cx < 0 || cy > intMap.length -1 || cx > intMap.length -1){
				cy = RandomProvider.getInstance().nextInt(intMap.length);
				cx = RandomProvider.getInstance().nextInt(intMap.length);
			}
			intMap[cy][cx] += 1;
			cy += RandomProvider.getInstance().nextInt(3) - 1;
			cx += RandomProvider.getInstance().nextInt(3) - 1;
		}
		return intMap;
	}

	private int[][] generateIntMap(int w, int h){
		int[][] intMap = new int[w][h];

		for(int y = 0; y < intMap.length; y++){
			for(int x = 0; x < intMap.length; x++){
				intMap[y][x] = 0;
			}
		}
		return intMap;
	}

	private TerrainObject[][] generateBiomes(TerrainObject[][] map){
		for(int y = 0; y < map.length; y++){
			for(int x = 0; x < map.length; x++){
				if(!map[y][x].isBiome() && (!(map[y][x] instanceof TerrainObjectWater)) ){
					createBiome(x, y, map,new Biome(WordGenerator.getInstance().getRandomAdjective()));
				}
			}
		}
		return map;
	}

	private void smoothTerrain(int[][] intMap){
		for(int i = 0; i < 2; i++){	
			for(int y = 0; y < intMap.length; y++){
				for(int x = 0; x < intMap.length; x++){
					if(intMap[y][x] == 0){
						if(x > 0 && y > 0 && x < intMap.length - 1 && y < intMap.length - 1){
							if((intMap[y + 1][x] > 0 && intMap[y -1][x] > 0) || (intMap[y][x + 1] > 0 && intMap[y][x - 1] > 0)){
								intMap[y][x] = 1;
							}
						}
					}
				}
			}
		}
	}


	private void generateRivers(int[][] intMap){
		int[][] hMap = new int[intMap.length][intMap.length];

		for(int y = 0; y < intMap.length; y++){
			for(int x = 0; x < intMap.length; x++){
				hMap[y][x] = intMap[y][x];
			}
		}

		ArrayList<Point> possiblePoints = new ArrayList<Point>();
		//check for mountains to spawn rivers on.
		for(int y = 0; y < intMap.length; y++){
			for(int x = 0; x < intMap.length; x++){
				if(intMap[y][x] >= MOUNTAIN_CONSTANT){
					possiblePoints.add(new Point(x,y));
				}
			}
		}
		System.out.println(possiblePoints.size());
		//spawn the rivers at one of the points randomly
		for(int i = 0;i < 10 && !possiblePoints.isEmpty(); i++){
			int r = RandomProvider.getInstance().nextInt(possiblePoints.size());
			generateRiver(hMap,(int)possiblePoints.get(r).getX(),(int)possiblePoints.get(r).getY());
			possiblePoints.remove(r);
		}

	}

	private void generateRiver(int[][] hMap,int x,int y){

		//starting point of the river
		int sY = y;
		int sX = x;

		LinkedList<Point> river = new LinkedList<Point>();

		river.add(new Point(sX, sY));

		int cY = sY, cX = sX;

		boolean finishedBuilding = false;

		int c = 0;

		while(finishedBuilding == false){

			ArrayList<Point> possiblePath = new ArrayList<Point>();

			//check for lower spots to move to
			if(cY + 1 < hMap.length){
				if(hMap[cY + 1][cX] <= hMap[cY][cX]){
					possiblePath.add(new Point(cX , cY + 1));
				}
			}
			if(cY - 1 > -1){
				if(hMap[cY - 1][cX] <= hMap[cY][cX]){
					possiblePath.add(new Point(cX, cY - 1));
				}
			}
			if(cX + 1 < hMap.length){
				if(hMap[cY][cX + 1] <= hMap[cY][cX]){
					possiblePath.add(new Point(cX + 1, cY));
				}
			}
			if(cX - 1 > -1){
				if(hMap[cY][cX - 1] <= hMap[cY][cX]){
					possiblePath.add(new Point(cX - 1, cY));
				}
			}

			//check there are places to go
			if(possiblePath.isEmpty()){
				finishedBuilding = true;
			}

			ArrayList<Point> updatedPath = new ArrayList<Point>();

			//check possible places aren't already in the river
			for(Point p : possiblePath){
				if(!river.contains(p)){
					updatedPath.add(p);
				}
			}

			possiblePath = updatedPath;

			//check there are still places to go
			if(possiblePath.isEmpty()){
				finishedBuilding = true;
			}

			for(Point p : possiblePath){
				if(hMap[(int)p.getY()][(int)p.getX()] == 0){
					river.add(p);
					finishedBuilding = true;
				}
			}

			if(c > 10000){
				finishedBuilding = true;
			}

			//add one point from possible path
			if(!finishedBuilding){
				int r = RandomProvider.getInstance().nextInt(possiblePath.size());
				river.add(possiblePath.get(r));
				cY = (int) possiblePath.get(r).getY();
				cX = (int) possiblePath.get(r).getX();
			}

			c++;
		}

		rivers.add(river);

	}
	
	private void createBiome(int x, int y, TerrainObject[][] map, Biome b){
		map[y][x].addToBiome(b);
		b.addTerrianObject(map[y][x]);
		if(y + 1 < map.length -1){
			if(!map[y + 1][x].isBiome() && map[y + 1][x].getClass() == (map[y][x]).getClass()){
				createBiome(x, y + 1, map, b );
			}
		}
		if(y - 1 > 0){
			if(!map[y - 1][x].isBiome() && (map[y - 1][x].getClass() == (map[y][x]).getClass())){
				createBiome(x, y - 1, map, b);
			}
		}
		if(x + 1 < map.length -1){
			if(!map[y][x + 1].isBiome() && map[y][x + 1].getClass() == (map[y][x]).getClass()){
				createBiome(x + 1, y, map, b);
			}
		}
		if(x - 1 > 0){
			if(!map[y][x - 1].isBiome() && map[y][x - 1].getClass() == (map[y][x]).getClass()){
				createBiome(x - 1, y, map, b);
			}
		}
	}

	public TerrainObject[][] testGenerateMapAnt(int w, int h){

		int[][] intMap = generateIntMap(w, h);


		intMap = simulateAnt(intMap, intMap.length/2, intMap.length/2, 1001);

		return constructTerrainMap(intMap);
	}

	public TerrainObject[][] testGenerateMapMultiCont(int w, int h){

		genStatus = 0;

		int[][] intMap = generateIntMap(w, h);

		for(int n = 0, cy = RandomProvider.getInstance().nextInt(intMap.length), cx = RandomProvider.getInstance().nextInt(intMap.length); n < 4; n++){
			intMap = simulateAnt(intMap, cx, cy, 2001);
			genStatus += 10;
		}

		for(int y1 = 0; y1 < intMap.length; y1++){
			for(int x1 = 0; x1 < intMap.length; x1++){
				if(intMap[y1][x1] > 0 && intMap[y1][x1] < 7){
					intMap[y1][x1] = 1;
				}
				else if(intMap[y1][x1] >= 7 && intMap[y1][x1] < MOUNTAIN_CONSTANT){
					intMap[y1][x1] = 2;
				}
			}
		}
		smoothTerrain(intMap);

		genStatus = 50;

		generateForests(intMap);

		generateRivers(intMap);

		genStatus = 70;

		return constructTerrainMap(intMap);
	}

	private void generateForests(int[][] map){
		forestMap = new int[map.length][map.length];
		for(int i = 0;  i < NUMBER_OF_FORESTS;i++){
			boolean goodCoord = false;
			int xCoord = 0;
			int yCoord = 0;
			while(!goodCoord){
				xCoord = RandomProvider.getInstance().nextInt(forestMap.length);
				yCoord = RandomProvider.getInstance().nextInt(forestMap.length);
				if(map[yCoord][xCoord] != 0 && map[yCoord][xCoord] < 3){
					goodCoord = true;
				}
			}
			for(int n = 0;n < 11;n++){
				if(map[yCoord][xCoord] == 1 || map[yCoord][xCoord] == 2){
					forestMap[yCoord][xCoord] = 1;
				}
				if(yCoord - 1 > 0 && xCoord - 1 > 0 && yCoord + 1 < forestMap.length -1 && xCoord + 1 < forestMap.length -1){
					yCoord += RandomProvider.getInstance().nextInt(3) - 1;
					xCoord += RandomProvider.getInstance().nextInt(3) - 1;
				}
			}
		}

	}

	public void buildRoad(GameObject g, GameObject g2){
		Pathfinder pathFinder = new Pathfinder(1000, false);
		Path path = pathFinder.findPath(g.getX(), g.getY(),
				g2.getX(), g2.getY());
		if(path != null){
			//go through each point and the path and change the point on the map to a road tile
			for(int i = 0;i < path.getLength();i++){
				Step prev = null;
				Step next = null;
				if(i > 0){
					prev = path.getStep(i -1);
				}
				if(i + 1 < path.getLength()){
					next = path.getStep(i + 1);
				}
				Step current = path.getStep(i);
				
				if(prev != null && next != null){
					if(prev.getY() != current.getY() && next.getY() != current.getY()){	//if next and previous are above and below.
						GameModeState.getInstance().getMap()[(int) current.getY()][(int) current.getX()] = new TerrainObjectRoadVertical();
					}

					if(prev.getY() < current.getY() && next.getY() == current.getY() && next.getX() < current.getX()){//if previous is lower and next is to the left
						GameModeState.getInstance().getMap()[(int) current.getY()][(int) current.getX()] = new TerrainObjectRoadBottomRightCorner();
					}
					if(prev.getY() < current.getY() && next.getY() == current.getY() && next.getX() > current.getX()){//if previous is lower and next is to the right
						GameModeState.getInstance().getMap()[(int) current.getY()][(int) current.getX()] = new TerrainObjectRoadBottomLeftCorner();
					}
					if(prev.getY() > current.getY() && next.getY() == current.getY() && next.getX() < current.getX()){//if previous is higher and next is to the left
						GameModeState.getInstance().getMap()[(int) current.getY()][(int) current.getX()] = new TerrainObjectRoadTopRightCorner();
					}
					if(prev.getY() > current.getY() && next.getY() == current.getY() && next.getX() > current.getX()){//if previous is higher and next is to the right
						GameModeState.getInstance().getMap()[(int) current.getY()][(int) current.getX()] = new TerrainObjectRoadTopLeftCorner();
					}

					if(next.getY() < current.getY() && prev.getY() == current.getY() && prev.getX() < current.getX()){//if next is lower and previous is to the left
						GameModeState.getInstance().getMap()[(int) current.getY()][(int) current.getX()] = new TerrainObjectRoadBottomRightCorner();
					}
					if(next.getY() < current.getY() && prev.getY() == current.getY() && prev.getX() > current.getX()){//if next is lower and previous is to the right
						GameModeState.getInstance().getMap()[(int) current.getY()][(int) current.getX()] = new TerrainObjectRoadBottomLeftCorner();
					}
					if(next.getY() > current.getY() && prev.getY() == current.getY() && prev.getX() < current.getX()){//if next is higher and previous is to the left
						GameModeState.getInstance().getMap()[(int) current.getY()][(int) current.getX()] = new TerrainObjectRoadTopRightCorner();
					}
					if(next.getY() > current.getY() && prev.getY() == current.getY() && prev.getX() > current.getX()){//if next is higher and previous is to the right
						GameModeState.getInstance().getMap()[(int) current.getY()][(int) current.getX()] = new TerrainObjectRoadTopLeftCorner();
					}			

					if(prev.getX() != current.getX() && next.getX() != current.getX()){
						GameModeState.getInstance().getMap()[(int) current.getY()][(int) current.getX()] = new TerrainObjectRoadHorizontal();
					}
				}
			}
		}
	}
}
