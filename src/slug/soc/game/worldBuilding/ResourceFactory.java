package slug.soc.game.worldBuilding;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

import slug.soc.game.RandomProvider;
import slug.soc.game.gameObjects.tileResources.AbstractResource;

public class ResourceFactory {

	private static ResourceFactory instance;

	private ResourceFactory(){
	}
	
	public static ResourceFactory getInstance(){
		if(instance == null){
			instance = new ResourceFactory();
		}
		return instance;
	}
	
	public AbstractResource getRandomLandResource(){
		String[] resourceString = WordGenerator.getInstance().getRandomLandResource().split(":");
		String resourceName = resourceString[0];
		Integer resourceAmount = Integer.parseInt(resourceString[1]);
		String requiredBuilding = resourceString[2];
		return new AbstractResource(resourceName, RandomProvider.getInstance().nextInt(resourceAmount), resourceAmount, requiredBuilding);
	}
	
	public AbstractResource getRandomSeaResource(){
		String[] resourceString = WordGenerator.getInstance().getRandomSeaResource().split(":");
		String resourceName = resourceString[0];
		Integer resourceAmount = Integer.parseInt(resourceString[1]);
		String requiredBuilding = resourceString[2];
		return new AbstractResource(resourceName, RandomProvider.getInstance().nextInt(resourceAmount), resourceAmount, requiredBuilding);
	}
	
	public AbstractResource getRandomMountainResource(){
		String[] resourceString = WordGenerator.getInstance().getRandomMountainResource().split(":");
		String resourceName = resourceString[0];
		Integer resourceAmount = Integer.parseInt(resourceString[1]);
		String requiredBuilding = resourceString[2];
		return new AbstractResource(resourceName, RandomProvider.getInstance().nextInt(resourceAmount), resourceAmount, requiredBuilding);
	
	}
}
