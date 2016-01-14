package com.nehpe.spaceminer.pickups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.utils.Animation;

public class OrangeGem extends Pickup {
	private Texture image;
	private Animation animation;
	
	public OrangeGem(Vector2 position) {
		this.position = position;
		this.image = new Texture(Gdx.files.internal("pickups/OrangeGem.png"));
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
		return 1000;
	}

}
