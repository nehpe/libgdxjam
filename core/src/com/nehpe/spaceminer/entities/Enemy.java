package com.nehpe.spaceminer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.spaceminer.SpaceMinerGame;

public class Enemy extends Entity {
	
	Vector2 position;
	Vector2 size;
	Texture texture;
	TextureRegion[][] sprites;
	
	public Enemy() {
		position = new Vector2(2*16*SpaceMinerGame.SCALE, 2*16*SpaceMinerGame.SCALE);
		texture = new Texture(Gdx.files.internal("sheets/enemy.png"));
		sprites = TextureRegion.split(texture, 16, 16);
		size = new Vector2(16 * SpaceMinerGame.SCALE, 16 * SpaceMinerGame.SCALE);		
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(sprites[0][0], position.x, position.y, size.x, size.y);

	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

}
