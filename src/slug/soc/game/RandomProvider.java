package slug.soc.game;

import java.util.Random;

public class RandomProvider {

	private static final long SEED = System.currentTimeMillis()/1000;
	Random random = new Random(SEED);
	
	private static RandomProvider instance;
	
	protected RandomProvider(){
	}
	
	public static RandomProvider getInstance(){
		if(instance == null){
			instance = new RandomProvider();
		}
		return instance;
	}
	
	public int nextInt(int limit){
		return random.nextInt(limit);
	}
	
	public boolean nextBoolean(){
		return random.nextBoolean();
	}
	
}
