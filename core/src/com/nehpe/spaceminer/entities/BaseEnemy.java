package com.nehpe.spaceminer.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nehpe.spaceminer.entities.ai.AIInformation;
import com.nehpe.spaceminer.pickups.Pickup;
import com.nehpe.utils.AABB;

public abstract class BaseEnemy extends Entity {
	public abstract void draw(SpriteBatch batch);
	public abstract void tick(AIInformation info);
	public abstract int getHealth();
	public abstract AABB getAABB();
	public abstract void hit(Projectile projectileToRemove, Player player);
	public abstract boolean dead();
	public abstract ArrayList<Pickup> die();
}
