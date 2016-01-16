package com.nehpe.spaceminer.pickups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.nehpe.utils.AABB;
import com.nehpe.utils.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class RedGem extends Pickup {
	private Texture image;
	private Animation animation;
	
	public RedGem(Vector2 position) {
		this.position = position;
		this.image = new Texture(Gdx.files.internal("pickups/RedGem.png"));
		TextureRegion[][] region = TextureRegion.split(image, 16, 16);
		animation = new Animation(16, 16, region[0]);
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(animation.getFrame(), this.position.x, this.position.y, 16, 16);
	}

	@Override
	public void tick() {
		animation.tick();
	}

	@Override
	public int getScore() {
		return 250;
	}

	@Override
	public AABB getAABB() {
		return new AABB(position, new Vector2(16, 16));
	}
}