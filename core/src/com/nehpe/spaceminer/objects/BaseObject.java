package com.nehpe.spaceminer.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class BaseObject {
	public abstract void draw(SpriteBatch batch);
	public abstract void tick();
	public abstract Vector2 getPosition();
	public abstract Vector2 getAdjoiningPosition();
}