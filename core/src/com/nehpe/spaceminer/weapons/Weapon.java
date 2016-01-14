package com.nehpe.spaceminer.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.spaceminer.entities.Projectile;
import com.nehpe.utils.GameVars.Direction;

public abstract class Weapon {
	public abstract void draw(SpriteBatch batch, Vector2 position, Direction currentDirection);
	public abstract void tick();
	public abstract Projectile fire(Direction direction, Vector2 position);
	public abstract int getDamage();
}
