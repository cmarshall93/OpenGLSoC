package slug.soc.game.gameObjects;

import java.awt.Color;

import slug.soc.game.gameObjects.tiles.GameTile;
import slug.soc.game.gameObjects.tiles.faction.TileCastle;

public class GameObjectCastle extends GameObject {

	public GameObjectCastle(Color color, Faction owner) {
		super(new TileCastle(color), owner);
	}

}
