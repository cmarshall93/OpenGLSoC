package slug.soc.game.gameObjects.tileResources;

public class AbstractResource {

	protected String name;
	protected String requiredBuilding;
	protected int averageCount;
	protected int count;

	public AbstractResource(String name, int intialCount, int averageCount, String requiredBuilding){
		this.name = name;
		this.requiredBuilding = requiredBuilding;
		this.averageCount = averageCount;
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

	public String getDesc(){
		String out = "";
		String amount;
		if(count < averageCount/10){
			amount = "small";
		}
		else if(count < averageCount/2){
			amount = "average";
		}
		else if(count < averageCount - (averageCount/10)){
			amount = "good";
		}
		else{
			amount = "vast";
		}

		out +=" a " + amount +" amount of " + name + " (" + count +" / " + averageCount + ") " + " NEEDS A " + requiredBuilding.toUpperCase() ;
		return out;
	}
}
