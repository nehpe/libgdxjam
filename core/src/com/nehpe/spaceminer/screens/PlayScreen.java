package com.nehpe.spaceminer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.spaceminer.SpaceMinerGame;
import com.nehpe.spaceminer.entities.Enemy;
import com.nehpe.spaceminer.entities.Player;
import com.nehpe.spaceminer.entities.Projectile;
import com.nehpe.spaceminer.entities.World;
import com.nehpe.spaceminer.physics.Collidable;
import com.nehpe.spaceminer.physics.Wall;
import com.nehpe.utils.HUD;

public class PlayScreen extends Screen {
	public static int displayWidth;
	public static int displayHeight;
	public static int lowDisplayWidth;
	public static int lowDisplayHeight;
	
	Enemy enemy;
	Player player;
	SpriteBatch batch;
	public OrthographicCamera camera;
	Vector2 currentMovement;
	World world;
	HUD hud;

	public PlayScreen(SpaceMinerGame game) {
		super(game);
		enemy = new Enemy();
		player = new Player();
		batch = new SpriteBatch();

		displayWidth  = (int) Gdx.graphics.getWidth();
		displayHeight = (int) Gdx.graphics.getHeight();
		
		lowDisplayWidth  = (int)(displayWidth/(displayHeight/(displayHeight/Math.floor(displayHeight/160))));
		lowDisplayHeight = (int)(displayHeight/Math.floor(displayHeight/160));
		

		camera = new OrthographicCamera(lowDisplayWidth, lowDisplayHeight);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
		world = new World();
		hud = new HUD();
	}

	public OrthographicCamera getCamera() {
		return this.camera;
	}

	@Override
	public void draw() {
		camera.update();
		batch.setProjectionMatrix(this.getCamera().combined);

		batch.begin();

		world.drawBackground(batch);

		enemy.draw(batch);

		player.draw(batch);
		
		world.drawForeground(batch);

		batch.end();

		hud.draw();
	}

	@Override
	public void input() {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		currentMovement = new Vector2(0, 0);
		if (Gdx.input.isKeyPressed(Keys.W)) {
			currentMovement.y = 1;
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			currentMovement.y = -1;
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			currentMovement.x = -1;
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			currentMovement.x = 1;
		}
		
		Projectile projectile = null;
		if (Gdx.input.isKeyJustPressed(Keys.UP)) {
			projectile = player.attackUp();
		} else if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
			projectile = player.attackDown();
		} else if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			projectile = player.attackLeft();
		} else if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			projectile = player.attackRight();
		}
		
		if (projectile != null) {
			world.addProjectile(projectile);
		}

		Vector2 proposedMovement = player.proposeMove(currentMovement);
		Collidable collision = world.checkPlayerCollisions(proposedMovement);
		if (collision != null) {
			// Check if there is a collision with our X movement
			Vector2 playerPosition = player.getPosition();
			Vector2 proposedDirectionalMovement = new Vector2(proposedMovement.x, playerPosition.y);
			collision = world.checkPlayerCollisions(proposedDirectionalMovement);
			if (collision != null && collision instanceof Wall) {
				// There is! Is it also along Y?
				proposedDirectionalMovement = new Vector2(playerPosition.x, proposedMovement.y);
				collision = world.checkPlayerCollisions(proposedDirectionalMovement);
				if (collision != null && collision instanceof Wall) { 
					// Our movement causes collisions on both axes, so we can't do the proposed move
					proposedMovement = playerPosition;
				} else {
					// Our movement is only colliding on the X axis, so we can still move Y
					// Reset our X
					proposedMovement.x = playerPosition.x;
				}
				
			} else if (collision instanceof Wall) {
				// By default, our collision must be on the Y axis.
				// Reset the Y and proceed
				proposedMovement.y = playerPosition.y;
			}
			proposedMovement = player.doCollision(proposedMovement, collision, world);
		}
		
		player.move(proposedMovement, this);
	}

	@Override
	public void tick() {
		player.tick();
		world.tick();
		hud.tick(player);
	}

}
