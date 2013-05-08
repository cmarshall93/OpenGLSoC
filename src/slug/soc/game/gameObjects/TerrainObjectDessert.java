package slug.soc.game.gameObjects;

import slug.soc.game.gameObjects.tiles.terrian.TileDessert;

public class TerrainObjectDessert extends TerrainObject {

	public TerrainObjectDessert(){
		super(new TileDessert(), true);
	}
	
	public String toString(){
		return ("The " + getBiomeString() + "Beach");
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getTypeString(){
		return "dessert";
	}
}
