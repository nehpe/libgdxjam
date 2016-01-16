package com.nehpe.spaceminer.entities;

import java.util.HashMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.nehpe.spaceminer.physics.Collidable;
import com.nehpe.spaceminer.pickups.Pickup;
import com.nehpe.spaceminer.screens.PlayScreen;
import com.nehpe.spaceminer.weapons.Pistol;
import com.nehpe.spaceminer.weapons.Weapon;
import com.nehpe.utils.AABB;
import com.nehpe.utils.Animation;
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
	int score = 0;
	int health = 10;

	TextureRegion[][] sprites;
	public float speed = 60f;

	public Player() {
		position = new Vector2(8 * 16, 7 * 16);
		size = new Vector2(16, 16);
		texture = new Texture(Gdx.files.internal("sheets/char3.png"));
		sprites = TextureRegion.split(texture, 16, 16);

		currentWeapon = new Pistol();

		this.loadAnimations();
	}

	private void loadAnimations() {
		// Set up all the player animations
		currentAnimation = new Animation(16, 16, sprites[0]);
		animations = new HashMap<Direction, Animation>();
		animations.put(Direction.DOWN, new Animation(16, 16, sprites[0]));
		animations.put(Direction.RIGHT, new Animation(16, 16, sprites[1]));
		animations.put(Direction.UP, new Animation(16, 16, sprites[2]));
		animations.put(Direction.LEFT, new Animation(16, 16, sprites[3]));
	}

	@Override
	public void draw(SpriteBatch batch) {
		if (!moving) {
			batch.draw(currentAnimation.getFrame(0), position.x, position.y, size.x, size.y);
			currentAnimation.reset();
		}
		batch.draw(currentAnimation.getFrame(), position.x, position.y, size.x, size.y);

		if (currentWeapon != null)
			currentWeapon.draw(batch, this.position, currentDirection);
	}

	@Override
	public void tick() {
		if (moving) {
			currentAnimation.tick();
		}
	}

	public void move(Vector2 newPosition, PlayScreen playScreen) {
		Vector2 movement = new Vector2(newPosition.x - position.x, newPosition.y - position.y);
		position.x = newPosition.x;
		position.y = newPosition.y;
		this.moveCamera(movement, playScreen);
	}

	public Vector2 proposeMove(Vector2 currentMovement) {
		if (currentMovement.x == 0 && currentMovement.y == 0) {
			moving = false;
			return position;
		}
		moving = true;
		Vector2 proposedPosition = new Vector2(position.x, position.y);
		Vector2 normalizedMovement = currentMovement;
		normalizedMovement.x *= (speed) * Gdx.graphics.getDeltaTime();
		normalizedMovement.y *= (speed) * Gdx.graphics.getDeltaTime();
		proposedPosition.x += normalizedMovement.x;
		proposedPosition.y += normalizedMovement.y;
		return proposedPosition;
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

	public Vector2 doCollision(Vector2 proposedMovement, Collidable collidable, World world) {
		if (collidable instanceof Pickup) {
			doPickup((Pickup) collidable, world);
		}
		return proposedMovement;
	}

	public void doPickup(Pickup collidingPickup, World world) {
		int score = collidingPickup.getScore();
		this.addScore(score);
		world.removePickup(collidingPickup);
	}

	public void addScore(int score) {
		this.score += score;
	}

	public int getScore() {
		return this.score;
	}

	public Projectile attackUp() {
		if (currentDirection != Direction.UP) {
			currentDirection = Direction.UP;
			this.resetAnimation();
		}
		if (currentWeapon == null)
			return null;
		return currentWeapon.fire(Direction.UP, this.position);
	}

	public Projectile attackDown() {
		if (currentDirection != Direction.DOWN) {
			currentDirection = Direction.DOWN;
			this.resetAnimation();
		}
		if (currentWeapon == null)
			return null;
		return currentWeapon.fire(Direction.DOWN, this.position);
	}

	public Projectile attackRight() {
		if (currentDirection != Direction.RIGHT) {
			currentDirection = Direction.RIGHT;
			this.resetAnimation();
		}
		if (currentWeapon == null)
			return null;
		return currentWeapon.fire(Direction.RIGHT, this.position);
	}

	public Projectile attackLeft() {
		if (currentDirection != Direction.LEFT) {
			currentDirection = Direction.LEFT;
			this.resetAnimation();
		}
		if (currentWeapon == null)
			return null;
		return currentWeapon.fire(Direction.LEFT, this.position);
	}

	public AABB getAABB() {
		return new AABB(position, new Vector2(16, 16));
	}

	public void hit(int damage) {
		
		this.health -= damage;
		if (this.health < 0) {
			this.health = 0;
		}
	}
	
	public boolean dead() {
		return (this.health == 0);
	}
	
	public int getHealth() {
		return this.health;
	}
}
