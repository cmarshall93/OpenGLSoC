package slug.soc.game.worldBuilding;


public class HouseSigilGenerator {

	private static HouseSigilGenerator instance;
	
	private HouseSigilGenerator(){
		
	}
	
	public static HouseSigilGenerator getInstance(){
		if(instance == null){
			instance = new HouseSigilGenerator();
		}
		return instance;
	}

	public String createNewSigilString(FactionColor factionColor){
		String sigil = "A ";
		sigil += WordGenerator.getInstance().getRandomAdjective().toLowerCase() + " ";
		sigil += ColorFactory.getInstance().getRandomFactionColor() + " ";
		sigil += WordGenerator.getInstance().getRandomNoun().toLowerCase() + " ";
		sigil += "on a ";
		sigil += factionColor.toString()+ " ";
		sigil += "background";
		return sigil;
	}
	
}
