package slug.soc.game.menu;

import slug.soc.game.Game;
import slug.soc.game.gameState.AboutState;

public class AboutOption extends AbstractMenuOption {

	public AboutOption() {
		super("About");
	}

	@Override
	public void act() {
		Game.getInstance().changeToNextGameState(AboutState.getInstance());
	}

}
