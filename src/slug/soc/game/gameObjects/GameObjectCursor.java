package slug.soc.game.gameObjects;

import slug.soc.game.gameObjects.tiles.faction.TileCursor;

public class GameObjectCursor extends GameObject {

	public GameObjectCursor() {
		super(new TileCursor(), null, 0, 0);
	}

	@Override
	public String[] getStringDesc() {
		String[] string = new String[1];
		string[0] = " ";
		return string;
	}
	
	public void act(){
		
	}
	
	public String toString(){
		return " ";
	}

	@Override
	public String getDetailedDesc() {
		return " ";
	}

	@Override
	public String getSpecialCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
