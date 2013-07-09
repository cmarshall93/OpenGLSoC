package slug.soc.game.gameObjects.tasks;

import java.util.ArrayList;

import org.newdawn.slick.util.pathfinding.Path;

import slug.soc.game.GameCalendar;
import slug.soc.game.GameCalendarEvent;
import slug.soc.game.Pathfinder;
import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.GameObjectPerson;
import slug.soc.game.gameObjects.MovementOrder;
import slug.soc.game.gameObjects.MovementOrderCoordinate;

public class DuelTask extends AbstractTask {

	private MovementOrder path;
	private boolean readyToDuel;

	public DuelTask(GameObject owner , GameObject target){
		super(owner, target);
		Pathfinder finder = new Pathfinder(10000,false);
		Path tmpPath = finder.findPath(owner.getX(), owner.getY(), target.getX(), target.getY());
		if(tmpPath != null){
			path = new MovementOrder(tmpPath.getLength());
			for(int i = 0; i < tmpPath.getLength(); i++){
				if(i != 0){
					path.getLastCoord().setNextCoord(
							new MovementOrderCoordinate(tmpPath.getStep(i).getX() - tmpPath.getStep(i - 1).getX()
									,tmpPath.getStep(i).getY() -  tmpPath.getStep(i - 1).getY()
									, path.getLastCoord()));
				}
				else{
					path.getLastCoord().setNextCoord(
							new MovementOrderCoordinate(tmpPath.getStep(i).getX() - owner.getX()
									,tmpPath.getStep(i).getY() - owner.getY()
									, path.getLastCoord()));
				}
			}
		}else{
			isCompleted = true;
		}
	}

	@Override
	public void act() {
		if(readyToDuel){
			duel();
			isCompleted = true;
		}
		else{
			owner.giveOrders(path);
			if(path.getLastCoord().getNextCoord() == null || path == null){
				readyToDuel = true;
			}
			else{
				owner.giveOrders(path);
			}
		}
	}

	@Override
	public String getDesc() {
		return "dueling with " + target.getName();
	}

	private void duel(){
		int ownerFS = ((GameObjectPerson) owner).getFightingSkill();
		int targetFS = ((GameObjectPerson) target).getFightingSkill();

		ArrayList<GameObject> objs = new ArrayList<GameObject>();
		objs.add(owner);
		objs.add(target);
		//owner of task wins
		if(ownerFS > targetFS){
			if(Math.abs(ownerFS - targetFS) < 20){
				((GameObjectPerson) target).wound();
				String out = owner.getName() + " fought and wounded " + target.getName();
				GameCalendar.getInstance().getCurrentDate().addEvent(new GameCalendarEvent(out,
						"The two parties fought for a couple of hours. The fight was close, until "
						+ owner.getName() + " gained the upper hand and " + target.getName() + " yeilded.",
						objs));
			}
			else{
				((GameObjectPerson) target).kill();
				String out = owner.getName() + " fought and killed " + target.getName();
				GameCalendar.getInstance().getCurrentDate().addEvent(new GameCalendarEvent(out,
						"The two parties fought for less than an hour. The fight was one sided, " + target.getName()
						+ " did not stand a chance in the fight and was killed.",
						objs));
			}
		}
		//owner of task losses
		else{
			if(Math.abs(targetFS - ownerFS) < 20){
				((GameObjectPerson) owner).wound();
				String out = owner.getName() + " fought and was wounded by " + target.getName();
				GameCalendar.getInstance().getCurrentDate().addEvent(new GameCalendarEvent(out,
						"The two parties fought for a couple of hours. The fight was close, until "
								+ target.getName() + " gained the upper hand and " + owner.getName() + " yeilded."
						,objs));
			}
			else{
				((GameObjectPerson) owner).kill();
				String out = owner.getName() + " fought and was killed by " + target.getName();
				GameCalendar.getInstance().getCurrentDate().addEvent(new GameCalendarEvent(out,
						"The two parties fought for less than an hour. The fight was one sided, " + owner.getName()
						+ " did not stand a chance in the fight and was killed."
						,objs));
			}
		}
	}

}
