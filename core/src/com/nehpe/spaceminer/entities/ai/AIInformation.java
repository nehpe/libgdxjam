package com.nehpe.spaceminer.entities.ai;

import java.util.ArrayList;
import com.nehpe.spaceminer.entities.Player;
import com.nehpe.spaceminer.entities.World;
import com.nehpe.spaceminer.objects.BaseObject;

public class AIInformation {
	
	ArrayList<BaseObject> objects;
	Player player;
	World world;
	
	public AIInformation() {
		
	}

	public void setObjects(ArrayList<BaseObject> objects) {
		this.objects = objects;
	}
	
	public BaseObject getRandomObject() {
		int selected = (int)Math.ceil(Math.random()*this.objects.size());
		return this.objects.get(selected-1);
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public Player getPlayer() {
		return this.player;
	}
}
