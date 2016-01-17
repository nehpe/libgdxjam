package com.nehpe.spaceminer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.spaceminer.SpaceMinerGame;
import com.nehpe.spaceminer.entities.BlobEnemy;
import com.nehpe.spaceminer.entities.Enemy;
import com.nehpe.spaceminer.entities.Player;
import com.nehpe.spaceminer.entities.Projectile;
import com.nehpe.spaceminer.entities.World;
import com.nehpe.spaceminer.physics.Collidable;
import com.nehpe.spaceminer.physics.Wall;
import com.nehpe.utils.HUD;
import com.nehpe.utils.hud.Pause;
import com.nehpe.utils.hud.Winner;

public class PlayScreen extends Screen {
	public static int displayWidth;
	public static int displayHeight;
	public static int lowDisplayWidth;
	public static int lowDisplayHeight;

	Enemy enemy;
	Player player;
	SpriteBatch batch;
	public OrthographicCamera camera;
	Pause pause;
	Winner win;
	Vector2 currentMovement;
	World world;
	HUD hud;
	int targetScore = 10000;
//	int targetScore = 100;
	boolean gameWon = false;

	public PlayScreen(SpaceMinerGame game) {
		super(game);

		batch = new SpriteBatch();
		pause = new Pause();
		win = new Winner();

		displayWidth = (int) Gdx.graphics.getWidth();
		displayHeight = (int) Gdx.graphics.getHeight();

		lowDisplayWidth = (int) (displayWidth / (displayHeight / (displayHeight / Math
				.floor(displayHeight / 160))));
		lowDisplayHeight = (int) (displayHeight / Math
				.floor(displayHeight / 160));

		world = new World();

		Vector2 mapDimensions = world.getMapDimensions();
		Vector2 playerStartPosition = new Vector2(0, 0);
		playerStartPosition.x = (float) Math.floor(Math.random()
				* mapDimensions.x);
		playerStartPosition.y = (float) Math.floor(Math.random()
				* mapDimensions.y);
		player = new Player(playerStartPosition);

		camera = new OrthographicCamera(lowDisplayWidth, lowDisplayHeight);
		camera.position.set((playerStartPosition.x * 16),
				(playerStartPosition.y * 16), 0);
		camera.update();

		// Add items
		world.addEnemy(new BlobEnemy(new Vector2(16 * 4, 16 * 4)));

		hud = new HUD();
	}

	public OrthographicCamera getCamera() {
		return this.camera;
	}

	@Override
	public void draw() {
		if (!pause.isPaused() && !win.won()) {
			camera.update();
			batch.setProjectionMatrix(this.getCamera().combined);

			batch.begin();

			world.drawBackground(batch);

			player.draw(batch);

			world.drawForeground(batch);

			batch.end();

			hud.draw();
		} else if (win.won()) {
			win.draw();
		} else {
			pause.draw();
		}
		
	}

	@Override
	public void input() {
		if (win.won()) {
			win.input(this);
			return;
		}
		if (pause.isPaused()) {
			pause.input(this);
			return;
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			pause.pause(player.getScore());
		}

		if (player.dead() && Gdx.input.isKeyJustPressed(Keys.R)) {
			this.restart();
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
		Collidable collision = world.checkPlayerCollisions(proposedMovement,
				player.getAABB().getRect());
		if (collision != null) {
			// Check if there is a collision with our X movement
			Vector2 playerPosition = player.getPosition();
			Vector2 proposedDirectionalMovement = new Vector2(
					proposedMovement.x, playerPosition.y);
			collision = world.checkPlayerCollisions(
					proposedDirectionalMovement, player.getAABB().getRect());
			if (collision != null && collision instanceof Wall) {
				// There is! Is it also along Y?
				proposedDirectionalMovement = new Vector2(playerPosition.x,
						proposedMovement.y);
				collision = world
						.checkPlayerCollisions(proposedDirectionalMovement,
								player.getAABB().getRect());
				if (collision != null && collision instanceof Wall) {
					// Our movement causes collisions on both axes, so we can't
					// do the proposed move
					proposedMovement = playerPosition;
				} else {
					// Our movement is only colliding on the X axis, so we can
					// still move Y
					// Reset our X
					proposedMovement.x = playerPosition.x;
				}

			} else if (collision instanceof Wall) {
				// By default, our collision must be on the Y axis.
				// Reset the Y and proceed
				proposedMovement.y = playerPosition.y;
			}
			proposedMovement = player.doCollision(proposedMovement, collision,
					world);
		}

		player.move(proposedMovement, this);
	}

	public void restart() {
		this.game.nextScreen(new PlayScreen(this.game));
	}

	@Override
	public void tick() {
		if (!pause.isPaused() && !win.won()) {
			player.tick();
			world.tick(player);
			hud.tick(player);
			if (player.getScore() >= targetScore) {
				this.winTheGame();				
			}
		}
	}
	
	/** You just lost the game =) **/
	private void winTheGame() {
		gameWon = true;
		win.win(); // It's a win win =)
		
	}
}