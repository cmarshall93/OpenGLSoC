package slug.soc.game;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.geom.Point;

import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.MovementOrder;
import slug.soc.game.gameObjects.MovementOrderCoordinate;
import slug.soc.game.gameState.GameModeState;

public class Pathfinder {

	public static Pathfinder instance;

	public Pathfinder(){

	}

	public static Pathfinder getInstance(){
		if(instance == null){
			instance = new Pathfinder();
		}
		return instance;
	}

	public MovementOrder generatePath(GameObject firstObject, GameObject secondObject){

		MovementOrder path = new MovementOrder(GameModeState.getInstance().getMap().length * 2);

		LinkedList<Point> openNodes = new LinkedList<Point>();
		LinkedList<Point> closedNodes = new LinkedList<Point>();

		//look at every adjacent node
		//add them all to the open list
		//take first item on the open list and look at it
		//look at nodes and add them to the front of the list
		Point node = new Point(firstObject.getX(), firstObject.getY());

		while(node.getX() != secondObject.getX() && node.getY() != secondObject.getY()){
			scanNode(openNodes, node);
			closedNodes.add(openNodes.getFirst());
			node = openNodes.getFirst();
			openNodes.removeFirst();
		}

		for(Point p: closedNodes){
			path.getLastCoord().setNextCoord(new MovementOrderCoordinate((int)p.getX(),(int)p.getY(),path.getLastCoord()));
		}
		System.out.println("GENERATED PATH");
		return path;
	}

	private void scanNode(LinkedList<Point> openNodes, Point node){
		int x = (int)node.getX();
		int y = (int)node.getY();
		for(int cx = x - 1;cx < x + 1; cx++){
			for(int cy = y - 1; cy < y + 1; cy++){
				if(x != 0 && y != 0){
					openNodes.addFirst(new Point(x,y));
				}
			}
		}
	}

}
