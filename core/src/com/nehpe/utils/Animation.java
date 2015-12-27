package com.nehpe.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Animation {
	TextureRegion[] frames;
	Vector2 dimensions;
	int maxFrames;
	int currentFrame = 0;
	float speed;
	float timer = 0f;
	float flip_time = 5f;
	private final static float DEFAULT_SPEED = 20f;
	
	
	public Animation(int width, int height, TextureRegion[] Frames, float speed) {
		this.dimensions = new Vector2(width, height);
		this.frames = Frames;
		maxFrames = Frames.length;
		this.speed = speed;
	}

	public Animation(int width, int height, TextureRegion[] Frames) {
		this(width, height, Frames, DEFAULT_SPEED);
	}
	
	public Animation(int width, int height, Texture Sheet) {
		this(width, height, TextureRegion.split(Sheet, width, height)[0]);
	}
	
	public void tick() {
		timer += speed * Gdx.graphics.getDeltaTime();
		if (timer > flip_time) {
			timer = 0f;
			currentFrame++;
			if (currentFrame+1 > maxFrames) {
				currentFrame = 0;
			}
		}
	}
	
	public TextureRegion getFrame() {
		return this.frames[currentFrame];
	}
	
	public void reset() {
		timer = 0f;
		currentFrame = 0;
	}
}
