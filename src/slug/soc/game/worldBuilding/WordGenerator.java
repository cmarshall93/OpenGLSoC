package slug.soc.game.worldBuilding;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import slug.soc.game.RandomProvider;

public class WordGenerator {

	private static WordGenerator instance = null;

	private File nounsFile = new File("textFiles/nouns.txt");
	private File verbsFile = new File("textFiles/verbs.txt");
	private File sizeFile = new File("textFiles/sizes.txt");
	private File lengthsFile = new File("textFiles/lengths.txt");
	
	private File maleFirstNameFile = new File("textFiles/people/names/maleFirstNames.txt");
	private File femaleFirstNameFile = new File("textFiles/people/names/femaleFirstNames.txt");
	private File surnamePrefixFile = new File("textFiles/people/names/surnamePrefix.txt");
	private File surnameSuffixFile = new File("textFiles/people/names/surnameSuffix.txt");
	private File surnameWholeFile = new File("textFiles/people/names/surnameWhole.txt");
	
	private File hairColoursFile = new File("textFiles/people/features/hairColours.txt");
	private File eyeColoursFile = new File("textFiles/people/features/eyeColours.txt");
	private File bodyTypesFile = new File("textFiles/people/features/bodyTypes.txt");
	private File beardTypesFile = new File("textFiles/people/features/beards.txt");
	
	
	private File placeSuffixFile = new File("textFiles/places/placeSuffix.txt");
	private File placesPrefixFile = new File("textFiles/places/placePrefix.txt");
	
	private File landResourceFile = new File("textFiles/resources/land.txt");
	private File seaResourceFile = new File("textFiles/resources/sea.txt");
	
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
	
	public String getRandomBodyType(){
		return getRandomWordFromFile(bodyTypesFile);
	}
	
	public String getRandomPlaceName(){
		return getRandomWordFromFile(placesPrefixFile) + getRandomWordFromFile(placeSuffixFile);
	}
	
	public String getRandomBeard(){
		return getRandomWordFromFile(beardTypesFile);
	}

	public String getRandomLandResource() {
		return getRandomWordFromFile(landResourceFile);
	}
	
	public String getRandomSeaResource(){
		return getRandomWordFromFile(seaResourceFile);
	}
}
