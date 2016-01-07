package com.nehpe.spaceminer.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class BaseEnemy extends Entity {
	public abstract void draw(SpriteBatch batch);
	public abstract void tick();
}
