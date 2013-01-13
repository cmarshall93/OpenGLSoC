package slug.soc.game;

import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class AsciiTextureGenerator {

	private static AsciiTextureGenerator instance ;

	private SpriteSheet sheet;
	private HashMap<String, Texture> map;
	
	private String[] chars = {"a","b", "c", "d", "e", "f", "g", "h", "i", "j"
			,"k", "l", "m", "n" ,"o" ,"p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", 
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q"
			,"R", "S", "T", "U", "V", "W" ,"X" ,"Y", "Z",".", "(", ")","u2500", "u2502", "u250c", "u2510"
			,"u2514", "u2518","mountain", "1", "person", "plains", "water", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
	};

	private AsciiTextureGenerator(){
		map = new HashMap<String, Texture>();
		try {
			sheet = new SpriteSheet("sheet",new ResourceLoader().getResourceAsStream("spritesheet.png"), 16, 16);
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
		/**
		int charIndex = 0;
		for(int y = 0; y < sheet.getVerticalCount(); y++){
			for(int x = 0; x < sheet.getHorizontalCount(); x++){
				map.put(chars[charIndex], sheet.getSubImage(x, y).getTexture());
				charIndex++;
				System.out.println(charIndex);
			}
		}**/
		for(String s : chars){
			try {
				map.put(s, new TextureLoader().getTexture("PNG", new ResourceLoader().getResourceAsStream("sprites/"+s+".png"), true));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(map.size());
	}

	public static AsciiTextureGenerator getInstance(){
		if(instance == null){
			instance = new AsciiTextureGenerator();
		}
		return instance;
	}
	
	public Texture getCharacterTexture(String c){
		return map.get(c);
	}
}
