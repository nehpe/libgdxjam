package com.nehpe.utils;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.spaceminer.SpaceMinerGame;
import com.nehpe.utils.hud.HealthBar;

public class HUD {
	HashMap<String, BitmapFont> fonts;
	SpriteBatch batch;
	int savings = 0;
	private GlyphLayout scoreLayout;
	private Vector2 scorePosition;
	HealthBar bar;


	public HUD() {
		batch = new SpriteBatch();
		// Load fonts
		fonts = new HashMap<String, BitmapFont>();
		fonts.put("TitleFont", new BitmapFont(Gdx.files.internal("fonts/TitleFont.fnt")));
		fonts.put("NormalFont", new BitmapFont(Gdx.files.internal("fonts/PressStart.fnt")));

		// Set up score layout
		scoreLayout = new GlyphLayout(fonts.get("NormalFont"), this.getScore());
		scorePosition = new Vector2((Gdx.graphics.getWidth() / 2) - scoreLayout.width / 2,
				scoreLayout.height);
		
		bar = new HealthBar(new Vector2(0, Gdx.graphics.getHeight()), SpaceMinerGame.SCALE);
	}

	public void draw() {
		batch.begin();
		this.drawScore();
		
		this.drawHealth();

		batch.end();
	}

	private void drawHealth() {
		bar.draw(batch);
	}

	private void drawScore() {
		fonts.get("NormalFont").draw(batch, scoreLayout, scorePosition.x, scorePosition.y);
	}

	private String getScore() {
		return "$" + String.format("%07d", savings);
	}

	public void tick() {
	}
}