package com.nehpe.spaceminer.pickups;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.spaceminer.physics.Collidable;
import com.nehpe.utils.AABB;

public abstract class Pickup extends Collidable {
	protected Vector2 position;

	public abstract void draw(SpriteBatch batch);
	public abstract void tick();

	public Vector2 getTilePosition() {
		return new Vector2((float) Math.floor(this.position.x / 16), (float) Math.floor(this.position.y / 16));
	}
	
	public abstract AABB getAABB();

	public abstract int getScore();

}
