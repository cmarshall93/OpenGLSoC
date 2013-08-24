package slug.soc.game.gameState;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import slug.soc.game.gameObjects.Faction;
import slug.soc.game.gameObjects.GameObject;
import slug.soc.game.gameObjects.GameObjectPerson;
import slug.soc.game.rendering.TextRenderer;

public class FamilyTreeState implements IGameState {

	private static FamilyTreeState instance;
	
	private ArrayList<GameObjectPerson> familyMembers;
	
	private static Faction faction;
	
	private FamilyTreeState(){
		faction = GameModeState.getInstance().getFaction();
		familyMembers = new ArrayList<GameObjectPerson>();
		addFamilyMember(faction.getHeadOfFamily());
	}
	
	private void addFamilyMember(GameObjectPerson familyMember){
		familyMembers.add(familyMember);
		for(GameObjectPerson gop : familyMember.getChildren()){
			addFamilyMember(gop);
		}
	}
	
	public static FamilyTreeState getInstance(){
		if(instance == null){
			instance = new FamilyTreeState();
		}
		return instance;
	}
	
	@Override
	public void createImage() {
		GL11.glPushMatrix();
			GL11.glTranslatef(0,20,0);
			GL11.glPushMatrix();
				GL11.glTranslatef((Display.getDisplayMode().getWidth() / 2) - 8, 0, 0);
				TextRenderer.getInstance().drawSymbol(faction.getHeadOfFamily().getTile().getSymbol(), 20);
			GL11.glPopMatrix();
			GL11.glTranslatef(0, 30, 0);
			float x = Display.getDisplayMode().getWidth() / (faction.getHeadOfFamily().getChildren().size() + 1);
			for(int i = 0;i < faction.getHeadOfFamily().getChildren().size(); i++){
				GL11.glTranslatef(x - 8, 0, 0);
				TextRenderer.getInstance().drawSymbol(faction.getHeadOfFamily().getChildren().get(i).getTile().getSymbol(), 20);
			}
		GL11.glPopMatrix();
	}

	@Override
	public void checkInput() {
		
	}

}
