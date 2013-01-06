package slug.soc.game;

import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class AsciiTextureGenerator {

	private static AsciiTextureGenerator instance ;

	private HashMap<Character, Texture> map;
	private Character[] chars = {'a','b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'
			,'k', 'l', 'm', 'n' ,'o' ,'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q'
			,'R', 'S', 'T', 'U', 'V', 'W' ,'X' ,'Y', 'Z'
	};

	private AsciiTextureGenerator(){
		map = new HashMap<Character, Texture>();

		for(Character c: chars){
			try {
				map.put(c, new TextureLoader().getTexture("PNG", new ResourceLoader().getResourceAsStream("sprites/"+c+".png"),true));
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
	
	public Texture getCharacterTexture(Character c){
		return map.get(c);
	}
}
