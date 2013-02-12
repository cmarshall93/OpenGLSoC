package slug.soc.game.menu;

import slug.soc.game.Game;
import slug.soc.game.gameState.GameModeState;

public class RunGameOption extends AbstractMenuOption {

	public RunGameOption(){
		super("Start Game");
	}
	
	@Override
	public void act() {
		Game.getInstance().changeToNextGameState(	GameModeState.getInstance());
		Thread t = new Thread(GameModeState.getInstance());
		t.start();
	}

}
