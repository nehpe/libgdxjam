package com.nehpe.spaceminer.pickups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Present extends Pickup {
	private Texture image;
	
	public Present(Vector2 position) {
		this.position = position;
		this.image = new Texture(Gdx.files.internal("pickups/present.png"));
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(this.image, this.position.x, this.position.y, 16, 16);
	}

	@Override
	public void tick() {

	}

	@Override
	public int getScore() {
		return 25;
	}

}
