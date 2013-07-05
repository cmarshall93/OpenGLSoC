package slug.soc.game.gameState;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import slug.soc.game.gameObjects.Faction;
import slug.soc.game.rendering.TextRenderer;

public class FamilyTreeState implements IGameState {

	private static FamilyTreeState instance;
	
	private static Faction faction;
	
	private FamilyTreeState(){
		
	}
	
	public static FamilyTreeState getInstance(){
		if(instance == null){
			instance = new FamilyTreeState();
		}
		faction = GameModeState.getInstance().getFaction();
		return instance;
	}
	
	@Override
	public void createImage() {
		GL11.glPushMatrix();
			GL11.glPushMatrix();
				GL11.glTranslatef((Display.getDisplayMode().getWidth() / 2) - 8, 0, 0);
				TextRenderer.getInstance().drawSymbol(faction.getHeadOfFamily().getTile().getSymbol(), 16);
			GL11.glPopMatrix();
			GL11.glTranslatef(0, -20, 0);
			float x = Display.getDisplayMode().getWidth() / faction.getHeadOfFamily().getChildren().size();
			for(int i = 0;i < faction.getHeadOfFamily().getChildren().size(); i++){
				GL11.glTranslatef(x, 0, 0);
				TextRenderer.getInstance().drawSymbol(faction.getHeadOfFamily().getChildren().get(i).getTile().getSymbol(), 16);
			}
		GL11.glPopMatrix();
	}

	@Override
	public void checkInput() {
		
	}

}
