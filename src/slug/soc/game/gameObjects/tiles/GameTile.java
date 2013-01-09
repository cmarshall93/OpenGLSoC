package slug.soc.game.gameObjects.tiles;

import java.awt.Color;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public abstract class GameTile {

	private String textureName;
	private Color color;
	
	public GameTile(String textureName, Color color){
		this.textureName = textureName;
		this.color = color;
	}
	
	public String getSymbol(){
		return textureName;
	}
	
	public Color getColor(){
		return color;
	}
}
