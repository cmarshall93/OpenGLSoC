package slug.soc.game.gameObjects;

import slug.soc.game.gameObjects.tiles.faction.TileCursor;

public class GameObjectCursor extends GameObject {

	public GameObjectCursor() {
		super(new TileCursor(), null);
	}

	@Override
	public String[] getStringDesc() {
		String[] string = new String[1];
		string[0] = " ";
		return string;
	}
	
	public String toString(){
		return " ";
	}

}
