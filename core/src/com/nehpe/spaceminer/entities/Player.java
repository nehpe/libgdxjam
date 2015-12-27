package com.nehpe.spaceminer.entities;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.spaceminer.SpaceMinerGame;
import com.nehpe.utils.Animation;

public class Player extends Entity {
	Vector2 position;
	Vector2 size;
	Texture texture;
	Animation currentAnimation;
	HashMap<String, Animation> animations;
	
	TextureRegion[][] sprites;
	public float speed = 60f;
	
	public Player() {
		position = new Vector2(8*16*SpaceMinerGame.SCALE, 7*16*SpaceMinerGame.SCALE);
		size = new Vector2(16 * SpaceMinerGame.SCALE, 16 * SpaceMinerGame.SCALE);
		texture = new Texture(Gdx.files.internal("sheets/char3.png"));
		sprites = TextureRegion.split(texture, 16, 16);
		this.loadAnimations();
	}
	
	private void loadAnimations() {
		// Set up all the player animations
		currentAnimation = new Animation(16, 16, sprites[0]);
		animations = new HashMap<String, Animation>();
		animations.put("Walk_Down", new Animation(16, 16, sprites[0]));
		animations.put("Walk_Right", new Animation(16, 16, sprites[1]));
		animations.put("Walk_Up", new Animation(16, 16, sprites[2]));
		animations.put("Walk_Right", new Animation(16, 16, sprites[3]));
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(currentAnimation.getFrame(), position.x, position.y, size.x, size.y);
	}

	@Override
	public void tick() {
		currentAnimation.tick();
	}

	public void move(Vector2 currentMovement) {
		if (currentMovement == Vector2.Zero) {
			System.out.println("No Movement");
			return;
		}
		Vector2 normalizedMovement = currentMovement;
		normalizedMovement.x *= (speed * SpaceMinerGame.SCALE) * Gdx.graphics.getDeltaTime();
		normalizedMovement.y *= (speed * SpaceMinerGame.SCALE) * Gdx.graphics.getDeltaTime();
		position.x += normalizedMovement.x;
		position.y += normalizedMovement.y;
	}
}
