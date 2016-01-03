package com.nehpe.spaceminer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Projectile extends Entity {
	Vector2 currentPosition;
	Vector2 movement;
	Texture image;
	float speed = 80.0f;
	
	public Projectile(Vector2 startingPosition, Vector2 movementDirection) {
		this.currentPosition = new Vector2(startingPosition.x, startingPosition.y);
		this.movement = movementDirection;
		image = new Texture(Gdx.files.internal("projectiles/bullet.png"));
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
}