package slug.soc.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class WordGenerator {

	private static WordGenerator instance = null;

	private File nounsFile = new File("nouns.txt");
	private File verbsFile = new File("verbs.txt");
	private File maleFirstNameFile = new File("people/names/maleFirstNames.txt");
	private File femaleFirstNameFile = new File("people/namesfemaleFirstNames.txt");
	private File surnamePrefixFile = new File("people/names/surnamePrefix.txt");
	private File surnameSuffixFile = new File("people/names/surnameSuffix.txt");
	private File surnameWholeFile = new File("people/names/surnameWhole.txt");
	private File sizeFile = new File("sizes.txt");
	private File lengthsFile = new File("lengths.txt");
	private File hairColoursFile = new File("people/features/hairColours.txt");
	private File eyeColoursFile = new File("people/features/eyeColours.txt");
	private File placeSuffixFile = new File("places/placeSuffix.txt");
	private File placesPrefixFile = new File("places/placePrefix.txt");
	private File landResourcesFile = new File("textFiles/resources/land.txt");
	private File seaResourcesFile = new File("textFiles/resources/sea.txt");
	
	private Scanner scanner;
	
	protected WordGenerator(){
		
	}
	
	public static WordGenerator getInstance(){
		if(instance == null){
			instance = new WordGenerator();
		}
		return instance;
	}
	
	private int countLines(File file){
		int n = 0;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(scanner.hasNext()){
			n++;
			scanner.next();
		}
		scanner.close();
		return n;
	}
	
	private String getRandomWordFromFile(File file){
		String word = null;
		int n = countLines(file);
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int q = RandomProvider.getInstance().nextInt(n) + 1;
		for(int i = 0; i < q ; i++){
			if(scanner.hasNext()){
				word = scanner.nextLine();
			}
		}
		scanner.close();
		return word;
	}
	
	public String getRandomFactionName(){
		if(RandomProvider.getInstance().nextInt(4) == 0){
			return getRandomWordFromFile(surnameWholeFile);
		}
		else{
			return getRandomWordFromFile(surnamePrefixFile) + getRandomWordFromFile(surnameSuffixFile).toLowerCase();
		}
	}
	
	public String getRandomMaleFirstName(){
		return getRandomWordFromFile(maleFirstNameFile);
	}
	
	public String getRandomAdjective(){
		return getRandomWordFromFile(verbsFile);
	}
	
	public String getRandomNoun(){
		return getRandomWordFromFile(nounsFile);
	}
	
	public String getRandomSize(){
		return getRandomWordFromFile(sizeFile);
	}
	
	public String getRandomLength(){
		return getRandomWordFromFile(lengthsFile);
	}
	
	public String getRandomHairColour(){
		return getRandomWordFromFile(hairColoursFile);
	}
	
	public String getRandomFemaleFirstName(){
		return getRandomWordFromFile(femaleFirstNameFile);
	}
	
	public String getRandomEyeColour(){
		return getRandomWordFromFile(eyeColoursFile);
	}
	
	public String getRandomPlaceName(){
		return getRandomWordFromFile(placesPrefixFile) + getRandomWordFromFile(placeSuffixFile);
	}
	
	public String getRandomLandResource(){
		return getRandomWordFromFile(landResourcesFile);
	}
	
	public String getRandomSeaResource(){
		return getRandomWordFromFile(seaResourcesFile);
	}
}
