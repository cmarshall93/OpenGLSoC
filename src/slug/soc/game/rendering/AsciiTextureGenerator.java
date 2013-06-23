package slug.soc.game.rendering;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class AsciiTextureGenerator {

	private static AsciiTextureGenerator instance ;

	private SpriteSheet sheet;
	
	private ArrayList<String> characters;
	private ArrayList<String> symbols;
	
	private HashMap<String, Texture> charMap;
	private HashMap<String, Texture> tileMap;

	private AsciiTextureGenerator(){
		charMap = new HashMap<String, Texture>();
		tileMap = new HashMap<String, Texture>();
		FileReadCharacters();
		FileReadSymbols();
		for(String s : characters){
			try {
				charMap.put(s.substring(0,s.lastIndexOf('.')), new TextureLoader().getTexture("PNG", new ResourceLoader().getResourceAsStream("sprites/"+s), true));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for(String s : symbols){
			try{
				tileMap.put(s.substring(0,s.lastIndexOf('.')), new TextureLoader().getTexture("PNG", new ResourceLoader().getResourceAsStream("sprites/tiles/"+s), true));
			}catch(Exception e){
				
			}
		}
	}

	public static AsciiTextureGenerator getInstance(){
		if(instance == null){
			instance = new AsciiTextureGenerator();
		}
		return instance;
	}
	
	public Texture getCharacterTexture(String c){
		return charMap.get(c);
	}
	
	public Texture getTileTexture(String c){
		return tileMap.get(c);
	}
	
	private void FileReadCharacters(){
		characters = new ArrayList<String>();
		
		File folder = new File("sprites");
		File[] listOfFiles = folder.listFiles();
		
		for(int i = 0; i < listOfFiles.length; i++){
			if(listOfFiles[i].isFile()){
				characters.add(listOfFiles[i].getName());
			}
		}
		
	}
	
	private void FileReadSymbols(){
		symbols = new ArrayList<String>();
		
		File folder = new File("sprites/tiles");
		File[] listOfFiles = folder.listFiles();
		
		for(int i = 0; i < listOfFiles.length; i++){
			if(listOfFiles[i].isFile()){
				symbols.add(listOfFiles[i].getName());
			}
		}
	}
	
	
}
