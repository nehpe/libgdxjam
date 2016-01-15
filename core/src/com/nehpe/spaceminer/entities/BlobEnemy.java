package com.nehpe.spaceminer.entities;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.spaceminer.entities.ai.AIInformation;
import com.nehpe.spaceminer.entities.ai.AIStates.State;
import com.nehpe.spaceminer.objects.BaseObject;
import com.nehpe.spaceminer.pickups.GemFactory;
import com.nehpe.spaceminer.pickups.Pickup;
import com.nehpe.utils.AABB;
import com.nehpe.utils.Animation;

public class BlobEnemy extends BaseEnemy {
	Vector2 position;
	Texture sheet;
	TextureRegion[][] frames;

	int health = 4;
	boolean dead = false;
	Animation animation;
	State currentAIState = State.NONE;
	Object target = null;
	Vector2 targetPosition;
	float timer = 0f;
	public float speed = 50f;
	int presents = 0;
	float gunTimer = 0f;

	public BlobEnemy(Vector2 position) {
		this.position = position;
		sheet = new Texture(Gdx.files.internal("sheets/blob_enemy.png"));
		frames = TextureRegion.split(sheet, 16, 16);
		animation = new Animation(16, 16, frames[0]);
		this.presents = (int) Math.floor(Math.random() * 4);
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(animation.getFrame(), this.position.x, this.position.y);
	}

	@Override
	public void tick(AIInformation info) {
		animation.tick();
		this.ai_tick(info);
	}

	private void ai_tick(AIInformation information) {
		if (currentAIState == State.NONE) {
			this.shop(information);
		}

		if (currentAIState == State.SHOPPING) {
			timer += Gdx.graphics.getDeltaTime();
			// System.out.println(timer);
			if (timer > 10f) {
				presents++;
				timer = 0f;
				currentAIState = State.NONE;
			}
		}
		
		Vector2 positionDifference;
		if (currentAIState == State.ATTACKING) {
			if (target instanceof Player) {
				Player player = (Player)target;
				positionDifference = new Vector2(player.getPosition().x - this.position.x,
						player.getPosition().y - this.position.y);
				
				// Are we close enough to start attacking?
				if (Math.abs(positionDifference.x) < 64 && Math.abs(positionDifference.y) < 64) {
					this.fire(player, information);
					timer = 0f;
				}
				Vector2 movementVelocity = new Vector2();

				if (positionDifference.x < 0) {
					movementVelocity.x = -1;
				} else if (positionDifference.x > 0) {
					movementVelocity.x = 1;
				}
				if (positionDifference.y < 0) {
					movementVelocity.y = -1;
				} else if (positionDifference.y > 0) {
					movementVelocity.y = 1;
				}

				if (movementVelocity != new Vector2(0, 0)) {
					this.move(movementVelocity);
				}
			}
		}

		if (currentAIState == State.MOVING) {
			// Check if we are at the target
			// If we are, change the state
			// If we aren't, move to the target
			if (target instanceof BaseObject) {
				positionDifference = new Vector2(targetPosition.x - this.position.x,
						targetPosition.y - this.position.y);
				if (Math.abs(positionDifference.x) < 1 && Math.abs(positionDifference.y) < 1) {
					this.position = targetPosition;
					currentAIState = State.SHOPPING;
					timer = 0f;
					return;
				}
				Vector2 movementVelocity = new Vector2();

				if (positionDifference.x < 0) {
					movementVelocity.x = -1;
				} else if (positionDifference.x > 0) {
					movementVelocity.x = 1;
				}
				if (positionDifference.y < 0) {
					movementVelocity.y = -1;
				} else if (positionDifference.y > 0) {
					movementVelocity.y = 1;
				}

				if (movementVelocity != new Vector2(0, 0)) {
					this.move(movementVelocity);
				}
			}
		}
	}

	private void fire(Player player, AIInformation information) {
		gunTimer += Gdx.graphics.getDeltaTime();
		if (gunTimer > 2.5f) {
			gunTimer = 0f;
			World world;
			world = information.getWorld();

			world.addEnemyProjectile(new EnemyProjectile(this.position, new Vector2(0, 1))); // Up
			world.addEnemyProjectile(new EnemyProjectile(this.position, new Vector2(1, 0))); // Right
			world.addEnemyProjectile(new EnemyProjectile(this.position, new Vector2(-1, 0))); // Left
			world.addEnemyProjectile(new EnemyProjectile(this.position, new Vector2(0, -1))); // Down
			world.addEnemyProjectile(new EnemyProjectile(this.position, new Vector2(1, 1))); // TR
			world.addEnemyProjectile(new EnemyProjectile(this.position, new Vector2(-1, 1))); // TL
			world.addEnemyProjectile(new EnemyProjectile(this.position, new Vector2(1, -1))); // BR
			world.addEnemyProjectile(new EnemyProjectile(this.position, new Vector2(-1, -1))); // BL
		}
	}

	private void move(Vector2 movementVelocity) {
		Vector2 normalizedMovement = movementVelocity;
		normalizedMovement.x *= (speed) * Gdx.graphics.getDeltaTime();
		normalizedMovement.y *= (speed) * Gdx.graphics.getDeltaTime();
		position.x += normalizedMovement.x;
		position.y += normalizedMovement.y;
	}

	private void shop(AIInformation information) {
		currentAIState = State.MOVING;
		BaseObject object = information.getRandomObject();
		target = object;

		if (target instanceof BaseObject) {
			this.targetPosition = ((BaseObject) target).getAdjoiningPosition();
		}
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public AABB getAABB() {
		return new AABB(position, new Vector2(16, 16));
	}

	@Override
	public void hit(Projectile projectile, Player player) {
		this.health -= projectile.getDamage();
		this.attack(player);
	}

	private void attack(Player player) {
		currentAIState = State.ATTACKING;
		target = player;
	}

	@Override
	public boolean dead() {
		if (this.health <= 0)
			return true;
		return false;
	}

	@Override
	public void tick() {

	}

	@Override
	public ArrayList<Pickup> die() {
		if (presents == 0)
			return null;
		ArrayList<Pickup> dispersedPresents = new ArrayList<Pickup>();

		for (int i = 0; i < presents; i++) {
			Vector2 originalPosition = new Vector2(position.x, position.y);
			originalPosition.x += Math.ceil(Math.random() * 16);
			originalPosition.y += Math.ceil(Math.random() * 16);
			dispersedPresents.add(GemFactory.Get(originalPosition));
		}

		return dispersedPresents;
	}
}