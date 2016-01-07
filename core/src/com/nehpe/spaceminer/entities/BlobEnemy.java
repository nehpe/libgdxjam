package com.nehpe.spaceminer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.utils.Animation;

public class BlobEnemy extends BaseEnemy {
	Vector2 position;
	Texture sheet;
	TextureRegion[][] frames;
	Animation animation;
	public BlobEnemy(Vector2 position) {
		this.position = position;
		sheet = new Texture(Gdx.files.internal("sheets/blob_enemy.png"));
		frames = TextureRegion.split(sheet, 16, 16);
		animation = new Animation(16, 16, frames[0]);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(animation.getFrame(), this.position.x, this.position.y);
	}

	@Override
	public void tick() {
		animation.tick();
	}
}
