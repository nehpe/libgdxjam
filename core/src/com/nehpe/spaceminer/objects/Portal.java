package com.nehpe.spaceminer.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.spaceminer.entities.BlobEnemy;
import com.nehpe.spaceminer.entities.World;

public class Portal {
	
	Vector2 position;
	Texture image;
	float timer = 0f;
	
	public Portal(Vector2 position) {
		this.position = position;
		this.image = new Texture(Gdx.files.internal("objects/portal.png"));
	}

	public void draw(SpriteBatch batch) {
		batch.draw(this.image, this.position.x, this.position.y);
	}

	public void tick(World world) {
		timer += Gdx.graphics.getDeltaTime();
		if (timer > 5f) {
			world.addEnemy(new BlobEnemy(this.position.cpy()));
			timer = 0f;
		}
		
	}

	public Vector2 getPosition() {
		return null;
	}

	public Vector2 getAdjoiningPosition() {
		return null;
	}

}
