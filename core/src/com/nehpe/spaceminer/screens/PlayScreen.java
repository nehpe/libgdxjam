package com.nehpe.spaceminer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.spaceminer.SpaceMinerGame;
import com.nehpe.spaceminer.entities.Enemy;
import com.nehpe.spaceminer.entities.Player;
import com.nehpe.spaceminer.entities.World;
import com.nehpe.spaceminer.physics.Collidable;
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

		world.draw(batch);

		enemy.draw(batch);

		player.draw(batch);

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
		
		if (Gdx.input.isKeyJustPressed(Keys.UP)) {
			player.attackUp();
		} else if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
			player.attackDown();
		} else if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			player.attackLeft();
		} else if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			player.attackRight();
		}
		
		player.move(currentMovement, this);
		Collidable collision = world.checkCollisions(player.getPosition());
		System.out.println(collision);
		
	}

	@Override
	public void tick() {
		player.tick();
		hud.tick();
	}

}
