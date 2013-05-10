package slug.soc.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

import slug.soc.game.gameObjects.tileResources.AbstractResource;

public class ResourceFactory {

	private static ResourceFactory instance;
	
	private File resourcesFile;

	private ResourceFactory(){
		resourcesFile = new File("resources/land.txt");
	}
	
	public static ResourceFactory getInstance(){
		if(instance == null){
			instance = new ResourceFactory();
		}
		return instance;
	}
	
	public AbstractResource getRandomLandResource(){
		String resourceString = WordGenerator.getInstance().getRandomLandResource();
		String resourceName = resourceString.substring(0, resourceString.indexOf(':'));
		Integer resourceAmount = Integer.parseInt(resourceString.substring(resourceString.indexOf(':') + 1, resourceString.length()));
		return new AbstractResource(resourceName, RandomProvider.getInstance().nextInt(resourceAmount), resourceAmount);
	}
	
	public AbstractResource getRandomSeaResource(){
		String resourceString = WordGenerator.getInstance().getRandomSeaResource();
		String resourceName = resourceString.substring(0, resourceString.indexOf(':'));
		Integer resourceAmount = Integer.parseInt(resourceString.substring(resourceString.indexOf(':') + 1, resourceString.length()));
		return new AbstractResource(resourceName, RandomProvider.getInstance().nextInt(resourceAmount), resourceAmount);
	}
}
