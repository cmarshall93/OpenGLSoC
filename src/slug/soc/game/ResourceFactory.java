package slug.soc.game;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import slug.soc.game.gameObjects.tileResources.AbstractResource;
import slug.soc.game.gameObjects.tileResources.DeerResource;
import slug.soc.game.gameObjects.tileResources.FishResource;
import slug.soc.game.gameObjects.tileResources.GoldResource;
import slug.soc.game.gameObjects.tileResources.IronResource;
import slug.soc.game.gameObjects.tileResources.LobsterResource;
import slug.soc.game.gameObjects.tileResources.SapphireResource;

public class ResourceFactory {

	private static ResourceFactory instance;
	
	private ArrayList<AbstractResource> landResourceList;
	private ArrayList<AbstractResource> seaResourceList;
	
	private ResourceFactory(){
		landResourceList = new ArrayList<AbstractResource>();
		landResourceList.add(new DeerResource());
		landResourceList.add(new GoldResource());
		landResourceList.add(new IronResource());
		landResourceList.add(new SapphireResource());
		
		seaResourceList = new ArrayList<AbstractResource>();
		seaResourceList.add(new FishResource());
		seaResourceList.add(new LobsterResource());
	}
	
	public static ResourceFactory getInstance(){
		if(instance == null){
			instance = new ResourceFactory();
		}
		return instance;
	}
	
	public AbstractResource getRandomLandResource(){
		AbstractResource r = landResourceList.get(RandomProvider.getInstance().nextInt(landResourceList.size()));
		try {
			return (AbstractResource) Class.forName(r.getClass().getName()).getConstructor().newInstance();
		} catch (Exception e) {
		} 
		return r;
	}
	
	public AbstractResource getRandomSeaResource(){
		AbstractResource r = seaResourceList.get(RandomProvider.getInstance().nextInt(seaResourceList.size()));
		try {
			return (AbstractResource) Class.forName(r.getClass().getName()).getConstructor().newInstance();
		} catch (Exception e) {
		} 
		return r;
		}
	
}
