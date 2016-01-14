package com.nehpe.spaceminer.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Table extends BaseObject {
	Texture image;
	Vector2 position;
	public Table(Vector2 position) {
		this.position = position;
		image = new Texture(Gdx.files.internal("objects/table.png"));
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(image, this.position.x, this.position.y);
	}

	@Override
	public void tick() {
	}

	@Override
	public Vector2 getPosition() {
		return this.position;
	}
	
	public Vector2 getAdjoiningPosition() {
		Vector2 newPosition = new Vector2(this.position.x, this.position.y);
		int selection = (int)Math.floor(Math.random()*2);
		if (selection == 0) {
			newPosition.x -= 16;
		} else {
			newPosition.x += 16;
		}
		newPosition.y += 16;
		return newPosition;
	}
}
