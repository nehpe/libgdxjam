package com.nehpe.utils.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class HealthBar {
	int health = 100;
	Texture healthSheet;
	TextureRegion[][] healthTiles;
	TextureRegion[][] bgTiles;
	int scale;
	Vector2 position;
	int fullbarWidth;
	int width = 0;
	
	public HealthBar(Vector2 position, int scale) {
		healthSheet = new Texture(Gdx.files.internal("hud/healthsheet.png"));
		healthTiles = TextureRegion.split(healthSheet, 16, 16);
		bgTiles = healthTiles[0][3].split(4, 16);
		
		this.position = position;
		this.scale = scale;
		
		fullbarWidth = 16*this.scale * 4;
		width = fullbarWidth;
		this.updateHealth(health);
	}
	
	public void draw(SpriteBatch batch) {
		System.out.println("Draw");
		this.drawBottomLayer(batch);
		this.drawTopLayer(batch);
	}
	
	private void drawTopLayer(SpriteBatch batch) {
		// Left
		batch.draw(healthTiles[0][0], position.x, position.y-(16*this.scale), 16*this.scale, 16*this.scale);
		
		// Middle (2)
		batch.draw(healthTiles[0][2], position.x+(16*this.scale), position.y-(16*this.scale), 16*this.scale, 16*this.scale);
		batch.draw(healthTiles[0][2], position.x+(16*this.scale)*2, position.y-(16*this.scale), 16*this.scale, 16*this.scale);
		
		// Right
		batch.draw(healthTiles[0][1], position.x+(16*this.scale)*3, position.y-(16*this.scale), 16*this.scale, 16*this.scale);
	}

	private void drawBottomLayer(SpriteBatch batch) {
		// left
		batch.draw(bgTiles[0][0], position.x, position.y-(16*this.scale), 4*this.scale, 16*this.scale);
		
		// middle stretched
		batch.draw(bgTiles[0][1], position.x+(4*this.scale), position.y-(16*this.scale), this.width, 16*this.scale);
		System.out.println(this.width);
		
		// right
		batch.draw(bgTiles[0][3], position.x+(4*this.scale)+(this.width), position.y-(16*this.scale), 4*this.scale, 16*this.scale);
	}
	
	public void updateHealth(int health) {
		this.health = health;
		this.width = fullbarWidth * (health/100) - (8*this.scale);
	}

	public void tick() {
	}
}