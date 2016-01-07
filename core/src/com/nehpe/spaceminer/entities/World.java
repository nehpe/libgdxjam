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
	ArrayList<Projectile> projectiles;
	int[][] collidables;
	public World() {
		level = new Level();
		pickups = new ArrayList<Pickup>();
		projectiles = new ArrayList<Projectile>();
		this.initial_setup();
	}
	
	private void initial_setup() {
		pickups.add(new Present(new Vector2(16*5, 16*5)));
		collidables = level.getCollidables();
	}
	
	public void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}
	
	public void removeProjectile(Projectile projectile) {
		projectiles.remove(projectile);
	}
	
	public void removePickup(Pickup pickup) {
		pickups.remove(pickup);
	}

	public void drawBackground(SpriteBatch batch) {
		level.draw(batch);
		
		// Draw pickups
		for (Pickup p : pickups) {
			p.draw(batch);
		}
	}
	
	public void drawForeground(SpriteBatch batch) {
		for (Projectile p : projectiles) {
			p.draw(batch);
		}
	}
	
	public void tick() {
		for (Pickup p : pickups) {
			p.tick();
		}
//		System.out.println("Projectiles: "+projectiles.size());
		for (Projectile p : projectiles) {
			p.tick();
		}
		
		this.checkNonPlayerCollisions();
	}
	
	public void checkNonPlayerCollisions() {
		Vector2 tilePosition = new Vector2(0,0);
		Vector2 projectilePosition = new Vector2(0,0);
		Projectile projectileToRemove = null;
		for (Projectile p : projectiles) {
			projectilePosition = p.getPosition();
			tilePosition = new Vector2(
					(float)Math.floor(projectilePosition.x/16),
					(float)Math.floor(projectilePosition.y/16)
					);
			if (collidables[(int) tilePosition.x][(int) tilePosition.y] == 1) {
				projectileToRemove = p;
				break;
			}
		}
		if (projectileToRemove != null) {
			this.removeProjectile(projectileToRemove);
		}
	}
	
	public Collidable checkPlayerCollisions(Vector2 playerPosition) {
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
}