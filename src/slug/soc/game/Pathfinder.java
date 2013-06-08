package slug.soc.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.geom.Point;

import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.MovementOrder;
import slug.soc.game.gameObjects.MovementOrderCoordinate;
import slug.soc.game.gameState.GameModeState;

public class Pathfinder {

	public static Pathfinder instance;
	
	private LinkedList<Point> openNodes;
	private LinkedList<Point> closedNodes;

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

		openNodes = new LinkedList<Point>();
		closedNodes = new LinkedList<Point>();

		//look at every adjacent node
		//move to the one closest to the target
		int currentX = firstObject.getX();
		int currentY = firstObject.getY();
		System.out.println("TARGET - " + secondObject.getX() + " : "  + secondObject.getY());
		while(true){
			Point node = new Point(currentX, currentY);
			openNodes = scanNode(openNodes,node, new Point(secondObject.getX(), secondObject.getY()));
			System.out.println(node.getX() + " : " + node.getY());
			closedNodes.add(node);
			node = openNodes.getFirst();
			openNodes.removeFirst();
			currentX = (int)node.getX();
			currentY = (int)node.getY();
			if(currentX == secondObject.getX() && currentY == secondObject.getY()){
				break;
			}
			
			if(closedNodes.size() == 70){
				break;
			}
		}
		
		for(Point p: closedNodes){
			int x = (int) (p.getX() - firstObject.getX());
			if(x > 0){
				x = 1;
			}
			else if(x < 0){
				x = -1;
			}
			
			int y = (int) (p.getY() - firstObject.getY());
			if(y > 0){
				y = 1;
			}
			else if(y < 0){
				y = -1;
			}
			path.getLastCoord().setNextCoord(new MovementOrderCoordinate(
					x,
					y,
					path.getLastCoord()));
		}
		System.out.println("GENERATED PATH");

		return path;
	}

	private  LinkedList<Point> scanNode(LinkedList<Point> openNodes, Point node, Point target){
		//pick the one closest
		ArrayList<Point> points = new ArrayList<Point>(); 
		points.add(new Point(node.getX() - 1, node.getY()));
		points.add(new Point(node.getX() + 1, node.getY()));
		points.add(new Point(node.getX(), node.getY() - 1));
		points.add(new Point(node.getX(), node.getY() + 1));
		
		Point p = null;	
		double distance = 0;
		for(int i = 0; i < points.size(); i++){
			
			double xd = Math.abs(target.getX() - points.get(i).getX());
			double yd = Math.abs(target.getY() - points.get(i).getY());
			
			double d = Math.sqrt((Math.pow(xd, 2)) + (Math.pow(yd, 2)));
			
			if((d < distance || distance == 0) && !closedNodes.contains(points.get(i))){
				distance = d;
				p = points.get(i);
			}
		}
		
		
		openNodes.addFirst(p);
		return openNodes;
	}

}

