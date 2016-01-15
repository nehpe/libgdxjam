package com.nehpe.spaceminer.entities;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.spaceminer.entities.ai.AIInformation;
import com.nehpe.spaceminer.levels.Level;
import com.nehpe.spaceminer.objects.BaseObject;
import com.nehpe.spaceminer.physics.Collidable;
import com.nehpe.spaceminer.physics.Wall;
import com.nehpe.spaceminer.pickups.Pickup;

public class World {
	private Level level;
	ArrayList<Pickup> pickups;
	ArrayList<Projectile> projectiles;
	ArrayList<EnemyProjectile> enemyProjectiles;
	ArrayList<BaseEnemy> enemies;
	ArrayList<BaseObject> objects;
	int[][] collidables;
	public World() {
		level = new Level();
		pickups = new ArrayList<Pickup>();
		projectiles = new ArrayList<Projectile>();
		enemyProjectiles = new ArrayList<EnemyProjectile>();
		enemies = new ArrayList<BaseEnemy>();
		objects = new ArrayList<BaseObject>();
		this.initial_setup();
	}
	
	private void initial_setup() {
		
		collidables = level.getCollidables();
	}
	
	public void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}
	
	public void removeProjectile(Projectile projectile) {
		projectiles.remove(projectile);
	}

	public void addEnemyProjectile(EnemyProjectile projectile) {
		enemyProjectiles.add(projectile);
	}

	public void removeEnemyProjectile(EnemyProjectile projectile) {
		enemyProjectiles.remove(projectile);
	}
	
	public void removePickup(Pickup pickup) {
		pickups.remove(pickup);
	}
	
	public void addEnemy(BaseEnemy enemy) {
		enemies.add(enemy);
	}
	
	public void removeEnemy(BaseEnemy enemy) {
		enemies.remove(enemy);
	}
	
	public void addObject(BaseObject object) {
		objects.add(object);
	}
	
	public void removeObject(BaseObject object) {
		objects.remove(object);
	}

	public void drawBackground(SpriteBatch batch) {
		level.draw(batch);
		
		// Draw pickups
		for (Pickup p : pickups) {
			p.draw(batch);
		}
		
		// Draw objects
		for (BaseObject o : objects) {
			o.draw(batch);
		}
	}
	
	public void drawForeground(SpriteBatch batch) {
		for (Projectile p : projectiles) {
			p.draw(batch);
		}
		
		for (EnemyProjectile p : enemyProjectiles) {
			p.draw(batch);
		}
		for (BaseEnemy e : enemies) {
			e.draw(batch);
		}
	}
	
	public void tick(Player player) {
		for (Pickup p : pickups) {
			p.tick();
		}
		for (Projectile p : projectiles) {
			p.tick();
		}
		for (EnemyProjectile p : enemyProjectiles) {
			p.tick();
		}
		AIInformation info = this.gatherAIInformation();
		for (BaseEnemy e : enemies) {
			e.tick(info);
		}
		for (BaseObject o : objects) {
			o.tick();
		}
		
		this.checkNonPlayerCollisions(player);
	}
	
	private AIInformation gatherAIInformation() {
		AIInformation info = new AIInformation();
		info.setObjects(objects);
		info.setWorld(this);
		return info;
	}

	public void checkNonPlayerCollisions(Player player) {
		Vector2 tilePosition = new Vector2(0,0);
		Vector2 projectilePosition = new Vector2(0,0);
		Projectile projectileToRemove = null;
		
		ArrayList<Pickup> additionalPickups = null;
		// Check if dead
		BaseEnemy enemyToRemove = null;
		for (BaseEnemy e : enemies) {
			if (e.dead()) {
				additionalPickups = e.die();
				enemyToRemove = e;
				continue;
			}
		}
		if (enemyToRemove != null) {
			this.removeEnemy(enemyToRemove);
			enemyToRemove = null;
			
			if (additionalPickups != null) {
				pickups.addAll(additionalPickups);
			}
			
		}
		
		// Check if projectile is colliding with an enemy
		Rectangle projectileRect;
		Rectangle enemyRect;
		BaseEnemy enemyToHit = null;
		for (Projectile p : projectiles) {
			projectileRect = p.getAABB().getRect();
			for (BaseEnemy e : enemies) {
				enemyRect = e.getAABB().getRect();
				if (enemyRect.overlaps(projectileRect)) {
					projectileToRemove = p;
					enemyToHit = e;
					break;
				}
			}
			if (projectileToRemove != null) {
				break;
			}
		}
		
		if (enemyToHit != null && projectileToRemove != null) {
			enemyToHit.hit(projectileToRemove, player);
		}
		if (projectileToRemove != null) {
			this.removeProjectile(projectileToRemove);
			projectileToRemove = null;
		}
		
		
		// Check if projectile is coliding with a non-movable tile (i.e. wall)
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
		// Check if enemy projectile is colliding with a non-movable tile (i.e. wall)
		EnemyProjectile enemyProjectileToRemove = null;
		for (EnemyProjectile p : enemyProjectiles) {
			projectilePosition = p.getPosition();
			tilePosition = new Vector2(
					(float)Math.floor(projectilePosition.x/16),
					(float)Math.floor(projectilePosition.y/16)
					);
			if (collidables[(int) tilePosition.x][(int) tilePosition.y] == 1) {
				enemyProjectileToRemove = p;
				break;
			}
		}
		if (enemyProjectileToRemove != null) {
			this.removeEnemyProjectile(enemyProjectileToRemove);
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