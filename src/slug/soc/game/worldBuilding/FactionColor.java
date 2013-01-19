package slug.soc.game.worldBuilding;

import java.awt.Color;

public class FactionColor {

	private Color color;
	private String desc;
	
	
	public FactionColor(Color color, String desc){
		this.color = color;
		this.desc = desc;
	}
	
	public Color getColor(){
		return color;
	}
	
	public String toString(){
		return desc;
	}
}
