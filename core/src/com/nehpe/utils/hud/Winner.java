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

public class Winner {
	BitmapFont smallFont;
	BitmapFont largeFont;

	String winString = "You are winner!";
	GlyphLayout winLayout;
	Vector2 winPosition;
	ShapeRenderer shape;
	SpriteBatch batch;
	boolean won = false;

	public Winner() {
		shape = new ShapeRenderer();
		batch = new SpriteBatch();
		smallFont = new BitmapFont(Gdx.files.internal("fonts/PressStart.fnt"));
		largeFont = new BitmapFont(Gdx.files.internal("fonts/TitleFont.fnt"));

		winLayout = new GlyphLayout(largeFont, winString, Color.WHITE,
				winString.length(), Align.left, false);
		// Centered
		winPosition = new Vector2((Gdx.graphics.getWidth() / 2)
				- (winLayout.width / 2), (Gdx.graphics.getHeight() / 2)
				- (winLayout.height / 2));		
	}
	
	public void win() {
		this.won = true;
	}
	
	public boolean won() {
		return this.won;
	}
	
	public void draw() {
		shape.begin(ShapeType.Filled);
		shape.setColor(new Color(Color.BLACK));
		shape.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shape.end();
		
		batch.begin();
		largeFont.draw(batch, winLayout, winPosition.x, winPosition.y);
		batch.end();
	}
	
	public void input(PlayScreen playScreen) {
		if (Gdx.input.isKeyJustPressed(Keys.R)) {
			playScreen.restart();
		}
		if (Gdx.input.isKeyJustPressed(Keys.Q)) {
			Gdx.app.exit();
		}
	}
}
