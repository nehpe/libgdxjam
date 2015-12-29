package com.nehpe.spaceminer.entities;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.nehpe.spaceminer.screens.PlayScreen;
import com.nehpe.spaceminer.weapons.Weapon;
import com.nehpe.utils.Animation;
import com.nehpe.utils.GameVars;
import com.nehpe.utils.GameVars.Direction;

public class Player extends Entity {
	Vector2 position;
	Vector2 size;
	Texture texture;
	Animation currentAnimation;
	HashMap<Direction, Animation> animations;
	boolean moving = false;
	Direction currentDirection = Direction.DOWN;
	Weapon currentWeapon = null;

	TextureRegion[][] sprites;
	public float speed = 60f;

	public Player() {
		position = new Vector2(8 * 16, 7 * 16);
		size = new Vector2(16, 16);
		texture = new Texture(Gdx.files.internal("sheets/char3.png"));
		sprites = TextureRegion.split(texture, 16, 16);

		this.loadAnimations();
	}

	private void loadAnimations() {
		// Set up all the player animations
		currentAnimation = new Animation(16, 16, sprites[0]);
		animations = new HashMap<Direction, Animation>();
		animations.put(Direction.DOWN, new Animation(16, 16, sprites[0]));
		animations.put(Direction.LEFT, new Animation(16, 16, sprites[1]));
		animations.put(Direction.UP, new Animation(16, 16, sprites[2]));
		animations.put(Direction.RIGHT, new Animation(16, 16, sprites[3]));
	}

	@Override
	public void draw(SpriteBatch batch) {
		if (!moving) {
			batch.draw(currentAnimation.getFrame(0), position.x, position.y, size.x, size.y);
			currentAnimation.reset();
		}
		batch.draw(currentAnimation.getFrame(), position.x, position.y, size.x, size.y);
	}

	@Override
	public void tick() {
		if (moving) {
			currentAnimation.tick();
		}
	}

	public void move(Vector2 currentMovement, PlayScreen playScreen) {
		if (currentMovement.x == 0 && currentMovement.y == 0) {
			moving = false;
			System.out.println("No Movement");
			return;
		}
		moving = true;

		if (currentMovement.x > 0) {
			if (currentDirection != Direction.LEFT) {
				currentDirection = Direction.LEFT;
				this.resetAnimation();
			}

		} else if (currentMovement.x < 0) {
			if (currentDirection != Direction.RIGHT) {
				currentDirection = Direction.RIGHT;
				this.resetAnimation();
			}

		} else if (currentMovement.y > 0) {
			if (currentDirection != Direction.UP) {
				currentDirection = Direction.UP;
				this.resetAnimation();
			}
		} else if (currentMovement.y < 0) {
			if (currentDirection != Direction.DOWN) {
				currentDirection = Direction.DOWN;
				this.resetAnimation();
			}
		}

		Vector2 normalizedMovement = currentMovement;
		normalizedMovement.x *= (speed) * Gdx.graphics.getDeltaTime();
		normalizedMovement.y *= (speed) * Gdx.graphics.getDeltaTime();
		position.x += normalizedMovement.x;
		position.y += normalizedMovement.y;

		this.moveCamera(normalizedMovement, playScreen);
	}

	private void resetAnimation() {
		currentAnimation = animations.get(currentDirection);
		currentAnimation.reset();
	}

	private void moveCamera(Vector2 normalizedMovement, PlayScreen playScreen) {
		Vector3 screenPos = playScreen.camera.project(new Vector3(this.position.x, this.position.y, 0));
		if (screenPos.x > 650 || screenPos.x < 250) {
			playScreen.camera.position.x += normalizedMovement.x;
		}

		if (screenPos.y > 450 || screenPos.y < 250) {
			playScreen.camera.position.y += normalizedMovement.y;
		}

	}

	public Vector2 getPosition() {
		return this.position;
	}

	public void attackUp() {

	}

	public void attackDown() {

	}

	public void attackRight() {

	}

	public void attackLeft() {

	}
}
