package slug.soc.game;

import java.util.ArrayList;

import slug.soc.game.gameObjects.tileResources.AbstractResource;
import slug.soc.game.gameObjects.tileResources.DeerResource;
import slug.soc.game.gameObjects.tileResources.GoldResource;
import slug.soc.game.gameObjects.tileResources.IronResource;

public class ResourceFactory {

	private static ResourceFactory instance;
	
	private ArrayList<AbstractResource> resourceList;
	
	private ResourceFactory(){
		resourceList = new ArrayList<AbstractResource>();
		resourceList.add(new DeerResource());
		resourceList.add(new GoldResource());
		resourceList.add(new IronResource());
	}
	
	public static ResourceFactory getInstance(){
		if(instance == null){
			instance = new ResourceFactory();
		}
		return instance;
	}
	
	public AbstractResource getRandomResource(){
		return resourceList.get(RandomProvider.getInstance().nextInt(resourceList.size()));
	}
	
}
