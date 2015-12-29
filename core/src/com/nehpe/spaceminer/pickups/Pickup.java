package com.nehpe.spaceminer.pickups;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.spaceminer.physics.Collidable;

public abstract class Pickup extends Collidable {
	public abstract void draw(SpriteBatch batch);
	public abstract void tick();
	
	public abstract Vector2 getTilePosition();
	
	
}
