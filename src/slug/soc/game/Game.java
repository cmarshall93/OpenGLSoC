package slug.soc.game;

import slug.soc.game.gameState.IGameState;
import slug.soc.game.gameState.MainMenuState;

public class Game {
	
	private static Game instance;
	
	private IGameState currentGameState;
	
	public static Game getInstance(){
		if(instance == null){
			instance = new Game();
		}
		return instance;
	}
	
	private Game(){
		currentGameState = MainMenuState.getInstance();
	}
	
	public void setCurrentGameState(IGameState gameState){
		currentGameState = gameState;
	}
	
	public IGameState getCurrentGameState(){
		return currentGameState;
	}
}
