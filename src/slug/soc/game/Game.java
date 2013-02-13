package slug.soc.game;

import java.util.Stack;

import slug.soc.game.gameState.GameModeState;
import slug.soc.game.gameState.IGameState;
import slug.soc.game.gameState.MainMenuState;

public class Game {
	
	private static Game instance;
	
	private IGameState currentGameState;
	private Stack<IGameState> previousGameStates;
	
	public static Game getInstance(){
		if(instance == null){
			instance = new Game();
		}
		return instance;
	}
	
	private Game(){
		currentGameState = MainMenuState.getInstance();
		previousGameStates = new Stack<IGameState>();
	}
	
	public void changeToNextGameState(IGameState gameState){
		previousGameStates.push(currentGameState);
		currentGameState = gameState;
	}
	
	public void changeToPreviousGameState(){
		if(previousGameStates.size() != 0){
			currentGameState = previousGameStates.pop();
		}
		else{
			currentGameState = GameModeState.getInstance();
		}
	}
	
	public void changeToMainScreen(){
		previousGameStates.clear();
		currentGameState = GameModeState.getInstance();
	}

	public IGameState getCurrentGameState(){
		return currentGameState;
	}
}
