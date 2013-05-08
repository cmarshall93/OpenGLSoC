package slug.soc.game.gameObjects.tileResources;

public abstract class AbstractResource {

	protected String name;
	protected int count;
	
	public AbstractResource(String name, int intialCount){
		this.name = name;
		count = intialCount;
	}
	
	public void decrementCount(int amount){
		count -= amount;
	}
	
	public String getName(){
		return name;	
	}
	
	public int getCount(){
		return count;
	}
	
	public abstract String getDesc();
}
