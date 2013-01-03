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
			,'k', 'l', 'm', 'n' ,'o' ,'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
	};

	private AsciiTextureGenerator(){
		map = new HashMap<Character, Texture>();
		try{
			for(Character c: chars){
				Texture tex = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("sprites/"+c+".png"));
				map.put(c, tex );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
