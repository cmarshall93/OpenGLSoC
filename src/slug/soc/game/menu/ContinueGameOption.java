package slug.soc.game.menu;

import slug.soc.game.Game;
import slug.soc.game.gameState.GameModeState;

public class ContinueGameOption extends AbstractMenuOption {

	public ContinueGameOption(){
		super("Continue Game");
	}
	
	@Override
	public void act() {
		Game.getInstance().changeToPreviousGameState();
	}

}
