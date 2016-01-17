package com.nehpe.spaceminer.entities;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.spaceminer.entities.ai.AIInformation;
import com.nehpe.spaceminer.levels.Level;
import com.nehpe.spaceminer.objects.BaseObject;
import com.nehpe.spaceminer.objects.Portal;
import com.nehpe.spaceminer.objects.Table;
import com.nehpe.spaceminer.physics.Collidable;
import com.nehpe.spaceminer.physics.Wall;
import com.nehpe.spaceminer.pickups.Pickup;

public class World {
	private Level level;
	ArrayList<Pickup> pickups;
	ArrayList<Projectile> projectiles;
	ArrayList<EnemyProjectile> enemyProjectiles;
	ArrayList<Portal> portals;
	ArrayList<BaseEnemy> enemies;
	ArrayList<BaseObject> objects;
	int[][] collidables;
	int collidables_w;
	int collidables_h;

	public World() {
		level = new Level();
		pickups = new ArrayList<Pickup>();
		projectiles = new ArrayList<Projectile>();
		enemyProjectiles = new ArrayList<EnemyProjectile>();
		enemies = new ArrayList<BaseEnemy>();
		objects = new ArrayList<BaseObject>();
		portals = new ArrayList<Portal>();
		this.initial_setup();
	}

	private void initial_setup() {
		collidables = level.getCollidables();
		collidables_w = collidables.length;
		collidables_h = collidables[0].length;
		
		// Set up tables
		int[][] tablePositions = level.getTables();
		for (int x = 0; x < tablePositions.length; x++) {
			for (int y = 0; y < tablePositions[x].length; y++) {
				if (tablePositions[x][y] == 1) {
					this.addObject(new Table(new Vector2(x*16, y*16)));
				}
			}
		}
		// Set up portals
		int[][] portalPositions = level.getPortals();
		for (int x = 0; x < portalPositions.length; x++) {
			for (int y = 0; y < portalPositions[x].length; y++) {
				if (portalPositions[x][y] == 1) {
					this.addPortal(new Portal(new Vector2(x*16, y*16)));
				}
			}
		}
	}
	
	public Vector2 getMapDimensions() {
		return new Vector2(collidables_w, collidables_h);
	}

	public void addPortal(Portal portal) {
		portals.add(portal);
	}
	
	public void removePortal(Portal portal) {
		portals.remove(portal);
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

		// Draw objects
		for (BaseObject o : objects) {
			o.draw(batch);
		}
		
		// Draw portals
		for (Portal p : portals) {
			p.draw(batch);
		}
		
		// Draw pickups
		for (Pickup p : pickups) {
			p.draw(batch);
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
		AIInformation info = this.gatherAIInformation(player);
		for (BaseEnemy e : enemies) {
			e.tick(info);
		}
		for (BaseObject o : objects) {
			o.tick();
		}
		// Tick portals
		for (Portal p : portals) {
			p.tick(this);
		}
		this.checkNonPlayerCollisions(player);
	}

	private AIInformation gatherAIInformation(Player player) {
		AIInformation info = new AIInformation();
		info.setPlayer(player);
		info.setObjects(objects);
		info.setWorld(this);
		return info;
	}

	public void checkNonPlayerCollisions(Player player) {
		Vector2 tilePosition = new Vector2(0, 0);
		Vector2 projectilePosition = new Vector2(0, 0);
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
					(float) Math.floor(projectilePosition.x / 16),
					(float) Math.floor(projectilePosition.y / 16));
			if (collidables[(int) tilePosition.x][(int) tilePosition.y] == 1) {
				projectileToRemove = p;
				break;
			}
		}
		if (projectileToRemove != null) {
			this.removeProjectile(projectileToRemove);
		}

		EnemyProjectile enemyProjectileToRemove = null;

		// Check if enemy projectile is hitting player
		Rectangle playerRect = player.getAABB().getRect();
		projectileRect = null;

		for (EnemyProjectile p : enemyProjectiles) {
			projectileRect = p.getAABB().getRect();

			if (projectileRect.overlaps(playerRect)) {
				player.hit(p.getDamage());
				enemyProjectileToRemove = p;
				break;
			}
		}

		if (enemyProjectileToRemove != null) {
			this.removeEnemyProjectile(enemyProjectileToRemove);
		}

		enemyProjectileToRemove = null;
		// Check if enemy projectile is colliding with a non-movable tile (i.e.
		// wall)

		for (EnemyProjectile p : enemyProjectiles) {
			projectilePosition = p.getPosition();
			tilePosition = new Vector2(
					(float) Math.floor(projectilePosition.x / 16),
					(float) Math.floor(projectilePosition.y / 16));
			if (tilePosition.x < 0 || tilePosition.y < 0
					|| tilePosition.x >= collidables_w
					|| tilePosition.y >= collidables_h) {
				enemyProjectileToRemove = p;
				break;
			} else if (collidables[(int) tilePosition.x][(int) tilePosition.y] == 1) {
				enemyProjectileToRemove = p;
				break;
			}
		}
		if (enemyProjectileToRemove != null) {
			this.removeEnemyProjectile(enemyProjectileToRemove);
		}

	}

	public Collidable checkPlayerCollisions(Vector2 playerPosition,
			Rectangle playerRect) {

		Rectangle pickupRect = null;
		for (Pickup p : pickups) {
			pickupRect = p.getAABB().getRect();
			if (pickupRect.overlaps(playerRect))
				return p;
		}

		Vector2 tilePosition = new Vector2(
				(float) Math.floor(playerPosition.x / 16),
				(float) Math.floor(playerPosition.y / 16));

		if (collidables[(int) tilePosition.x][(int) tilePosition.y] == 1) {
			return new Wall(tilePosition);
		}

		return null;
	}
}