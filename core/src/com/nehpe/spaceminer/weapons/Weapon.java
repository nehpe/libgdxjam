package com.nehpe.spaceminer.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Weapon {
	public abstract void draw(SpriteBatch batch);
	public abstract void tick();
	public abstract void fire();
}
