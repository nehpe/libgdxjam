package com.nehpe.spaceminer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.utils.AABB;

public class EnemyProjectile extends Entity {
	Vector2 currentPosition;
	Vector2 movement;
	Texture image;
	int damage = 2;
	float speed = 120f;
	
	public EnemyProjectile(Vector2 startingPosition, Vector2 movementDirection) {
		this.currentPosition = new Vector2(startingPosition.x, startingPosition.y);
		this.movement = movementDirection;
		image = new Texture(Gdx.files.internal("projectiles/goo.png"));
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(image, currentPosition.x, currentPosition.y);
		
	}
	@Override
	public void tick() {
		currentPosition.x += movement.x * (speed * Gdx.graphics.getDeltaTime());
		currentPosition.y += movement.y * (speed * Gdx.graphics.getDeltaTime());
	}
	
	public Vector2 getPosition() {
		return this.currentPosition;
	}
	
	public AABB getAABB() {
		return new AABB(currentPosition, new Vector2(image.getWidth(), image.getHeight()));
	}
	
	public int getDamage() {
		return this.damage;
	}
}
