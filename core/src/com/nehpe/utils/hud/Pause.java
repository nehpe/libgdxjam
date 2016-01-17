package com.nehpe.utils.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.nehpe.spaceminer.screens.PlayScreen;

public class Pause {
	BitmapFont smallFont;
	BitmapFont bigFont;
	ShapeRenderer shape;
	SpriteBatch batch;
	boolean paused = false;
	GlyphLayout pausedLayout;
	
	Vector2 pausedPositioning;
	GlyphLayout instructions1Layout;
	GlyphLayout instructions2Layout;
	Vector2 instructions1Positioning;
	Vector2 instructions2Positioning;
	String instructions1 = "Press Q to Quit";
	String instructions2 = "Press R to Restart";
	int score = 0;
	GlyphLayout scoreLayout;
	Vector2 scorePositioning;
	
	public Pause() {
		bigFont = new BitmapFont(Gdx.files.internal("fonts/TitleFont.fnt"));
		smallFont = new BitmapFont(Gdx.files.internal("fonts/PressStart.fnt"));
		shape = new ShapeRenderer();
		batch = new SpriteBatch();
		
		pausedLayout = new GlyphLayout(bigFont, "PAUSED", Color.GRAY, 6, Align.left, false);
		pausedPositioning = new Vector2(
				(Gdx.graphics.getWidth() / 2) - pausedLayout.width / 2,
				(float)(((Gdx.graphics.getHeight() / 2) - pausedLayout.height / 2) * 1.25)
				);
		
		instructions1Layout = new GlyphLayout(smallFont, instructions1, Color.BLACK, instructions1.length(), Align.left, false);
		instructions1Positioning = new Vector2(
				(Gdx.graphics.getWidth() / 2) - instructions1Layout.width / 2,
				(float)(((Gdx.graphics.getHeight() / 2) - instructions1Layout.height / 2) - 24)
				);
		
		instructions2Layout = new GlyphLayout(smallFont, instructions2, Color.BLACK, instructions2.length(), Align.left, false);
		instructions2Positioning = new Vector2(
				(Gdx.graphics.getWidth() / 2) - instructions2Layout.width / 2,
				(float)(((Gdx.graphics.getHeight() / 2) - instructions2Layout.height / 2) - 48)
				);
		
		this.redoScoreLayout();
		
	}
	
	private void redoScoreLayout() {
		String scoreText = "Current Score: $" + String.format("%07d", this.score);
		scoreLayout = new GlyphLayout(smallFont, scoreText, Color.GREEN, scoreText.length(), Align.left, false);
		scorePositioning = new Vector2(
				(Gdx.graphics.getWidth()/2) - scoreLayout.width /2,
				scoreLayout.height
				);
		
	}

	public boolean isPaused() {
		return paused;
	}
	
	public void pause(int score) {
		this.paused = true;
		this.score = score;
		this.redoScoreLayout();
		
	}
	
	public void input(PlayScreen playScreen) {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			this.paused = false;
		}
		if (Gdx.input.isKeyJustPressed(Keys.Q)){
			Gdx.app.exit();
		}
		if (Gdx.input.isKeyJustPressed(Keys.R)) {
			playScreen.restart();
		}
	}
	
	public void draw() {
		shape.begin(ShapeType.Filled);
		shape.setColor(1f, 1f, 1f, 1f);
		shape.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shape.end();
		
		batch.begin();
		bigFont.draw(batch, pausedLayout, pausedPositioning.x, pausedPositioning.y);
		smallFont.draw(batch, instructions1Layout, instructions1Positioning.x, instructions1Positioning.y);
		smallFont.draw(batch, instructions2Layout, instructions2Positioning.x, instructions2Positioning.y);
		smallFont.draw(batch, scoreLayout, scorePositioning.x, scorePositioning.y);
		batch.end();
	}
}