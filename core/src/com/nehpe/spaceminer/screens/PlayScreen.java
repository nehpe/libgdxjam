package com.nehpe.spaceminer.screens;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.spaceminer.SpaceMinerGame;
import com.nehpe.spaceminer.entities.Enemy;
import com.nehpe.spaceminer.entities.Player;
import com.nehpe.spaceminer.levels.Level;
import com.nehpe.utils.HUD;

public class PlayScreen extends Screen {
	Enemy enemy;
	Player player;
	SpriteBatch batch;
	public OrthographicCamera camera;
	Vector2 currentMovement;
	Level level;
	HUD hud;

	public PlayScreen(SpaceMinerGame game) {
		super(game);
		enemy = new Enemy();
		player = new Player();
		batch = new SpriteBatch();

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(w, h);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
		level = new Level();
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

		level.draw(batch);

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
		player.move(currentMovement, this);
	}

	@Override
	public void tick() {
		player.tick();
		hud.tick();
	}

}
