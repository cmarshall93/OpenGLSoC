package slug.soc.game.worldBuilding;

import java.awt.Color;
import java.util.ArrayList;

import slug.soc.game.RandomProvider;

public class ColorFactory {

	private static ColorFactory instance = null;
	
	private int factionColorsIndex = 0;
	private FactionColor[] factionColors = {
			new FactionColor(Color.RED,"red"),
			new FactionColor(Color.CYAN,"cyan"),
			new FactionColor(Color.YELLOW,"yellow"),
			new FactionColor(Color.GRAY,"grey"),
			new FactionColor(Color.MAGENTA,"magenta"),
			new FactionColor(new Color(252,252,252), "white"),
			new FactionColor(new Color(150,75,0), "brown")
	};

	protected ColorFactory(){
	}

	public static ColorFactory getInstance(){
		if(instance == null){
			instance = new ColorFactory();
		}
		return instance;
	}	

	public FactionColor getNextFactionColor(){
		if(factionColorsIndex == factionColors.length){
			factionColorsIndex = 0;
		}
		factionColorsIndex += 1;
		return factionColors[factionColorsIndex - 1];
	}
	
	public FactionColor getRandomFactionColor(){
		int i = RandomProvider.getInstance().nextInt(factionColors.length);
		return factionColors[i];
	}
}
