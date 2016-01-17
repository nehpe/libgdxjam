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

	GlyphLayout instructions1Layout;
	GlyphLayout instructions2Layout;
	Vector2 instructions1Positioning;
	Vector2 instructions2Positioning;
	String instructions1 = "Press Q to Quit";
	String instructions2 = "Press R to Restart";

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
		smallFont.draw(batch, instructions1Layout, instructions1Positioning.x, instructions1Positioning.y);
		smallFont.draw(batch, instructions2Layout, instructions2Positioning.x, instructions2Positioning.y);
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
