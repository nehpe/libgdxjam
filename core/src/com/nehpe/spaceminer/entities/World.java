package com.nehpe.spaceminer.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.spaceminer.levels.Level;
import com.nehpe.spaceminer.physics.Collidable;
import com.nehpe.spaceminer.physics.Wall;
import com.nehpe.spaceminer.pickups.Pickup;
import com.nehpe.spaceminer.pickups.Present;

public class World {
	private Level level;
	ArrayList<Pickup> pickups;
	int[][] collidables;
	public World() {
		level = new Level();
		pickups = new ArrayList<Pickup>();
		this.initial_setup();
	}
	
	private void initial_setup() {
		pickups.add(new Present(new Vector2(16*5, 16*5)));
		collidables = level.getCollidables();
	}

	public void draw(SpriteBatch batch) {
		level.draw(batch);
		
		// Draw pickups
		for (Pickup p : pickups) {
			p.draw(batch);
		}
	}
	
	public Collidable checkCollisions(Vector2 playerPosition) {
		Vector2 tilePosition = new Vector2(
				(float)Math.floor(playerPosition.x/16),
				(float)Math.floor(playerPosition.y/16)
				);
		Vector2 pickupTilePosition;
		for (Pickup p : pickups) {
			pickupTilePosition = p.getTilePosition();
			if (pickupTilePosition.x == tilePosition.x &&
					pickupTilePosition.y == tilePosition.y) {
				return p;
			}
				
		}
		
		if (collidables[(int) tilePosition.x][(int) tilePosition.y] == 1) {
			return new Wall(tilePosition);
		}
		
		return null;
	}
	
	public void tick() {
		
	}
}
