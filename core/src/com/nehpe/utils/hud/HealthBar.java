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
	Texture heart;
	int scale;
	Vector2 position;
	int fullbarWidth;
	int width = 0;
	
	public HealthBar(Vector2 position) {
		heart = new Texture(Gdx.files.internal("hud/heart.png"));
		healthSheet = new Texture(Gdx.files.internal("hud/healthbar.png"));
		healthTiles = TextureRegion.split(healthSheet, 16, 32);
		
		this.position = position;
		this.scale = 4;
		
		fullbarWidth = 16*this.scale * 4;
		width = fullbarWidth;
		this.updateHealth(health);
	}
	
	public void draw(SpriteBatch batch) {
		this.drawBottomLayer(batch);
		this.drawTopLayer(batch);
	}
	
	private void drawTopLayer(SpriteBatch batch) {
		
		// Left
		batch.draw(healthTiles[0][0], position.x+(7*this.scale), position.y-(32*this.scale), 16*this.scale, 32*this.scale);
		batch.draw(healthTiles[0][3], position.x+(7*this.scale)+(16*this.scale), position.y-(32*this.scale), 16*this.scale, 32*this.scale);
		
		// Middle (2)
		batch.draw(healthTiles[0][2], position.x+(7*this.scale)+(16*this.scale)*2, position.y-(32*this.scale), 16*this.scale, 32*this.scale);
		batch.draw(healthTiles[0][2], position.x+(7*this.scale)+(16*this.scale)*3, position.y-(32*this.scale), 16*this.scale, 32*this.scale);
		
		// Right
		batch.draw(healthTiles[0][1], position.x+(7*this.scale)+(16*this.scale)*4, position.y-(32*this.scale), 16*this.scale, 32*this.scale);
		
	}

	private void drawBottomLayer(SpriteBatch batch) {
		batch.draw(healthTiles[0][4], position.x+(7*this.scale)+(16*this.scale), position.y-(32*this.scale), this.width, 32*this.scale);
		
	}
	
	public void updateHealth(int health) {
		this.health = health;
		this.width = (int) (fullbarWidth * (float)(health/100f));
	}

	public void tick() {
	}
}