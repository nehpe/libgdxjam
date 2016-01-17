package com.nehpe.utils;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.nehpe.spaceminer.SpaceMinerGame;
import com.nehpe.spaceminer.entities.Player;
import com.nehpe.utils.hud.HealthBar;

public class HUD {
	HashMap<String, BitmapFont> fonts;
	SpriteBatch batch;
	int savings = 0;
	private GlyphLayout scoreLayout;
	private GlyphLayout dropShadowLayout;
	private GlyphLayout deadLayout;
	private GlyphLayout restartLayout;
	private Vector2 scorePosition;
	private Vector2 dropShadowPosition;
	private Vector2 deadPosition;
	private Vector2 restartPosition;
	private Rectangle deathBackdrop;
	private ShapeRenderer shapeRenderer;

	HealthBar bar;

	public HUD() {
		batch = new SpriteBatch();
		// Load fonts
		fonts = new HashMap<String, BitmapFont>();
		fonts.put("TitleFont",
				new BitmapFont(Gdx.files.internal("fonts/TitleFont.fnt")));
		fonts.put("NormalFont",
				new BitmapFont(Gdx.files.internal("fonts/PressStart.fnt")));

		// Set up score layout
		this.redoScoreLayout();

		shapeRenderer = new ShapeRenderer();
		this.initDeathScreen();

		bar = new HealthBar(new Vector2(0, Gdx.graphics.getHeight()));
	}

	private void initDeathScreen() {
		deathBackdrop = new Rectangle(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		deadLayout = new GlyphLayout(fonts.get("NormalFont"), "You dead",
				Color.WHITE, 8, Align.left, false);
		deadPosition = new Vector2((Gdx.graphics.getWidth() / 2)
				- deadLayout.width / 2, 
				(Gdx.graphics.getHeight() / 2)
				- deadLayout.height / 2);
		
		restartLayout = new GlyphLayout(fonts.get("NormalFont"), "Press R to Restart",
				Color.GRAY, 18, Align.left, false);
		
		restartPosition = new Vector2((Gdx.graphics.getWidth() / 2)
				- restartLayout.width / 2, 
				(Gdx.graphics.getHeight() / 2)
				- (restartLayout.height / 2) - (deadLayout.height*2));
		
	}

	public void draw() {
		if (bar.isDead()) {
			this.drawDeathScreen();
		} else {
			batch.begin();
			this.drawScore();
			this.drawHealth();
			batch.end();
		}
	}

	private void drawHealth() {
		bar.draw(batch);
	}

	private void drawDeathScreen() {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(0f, 0f, 0f, 0.5f);
		shapeRenderer.rect(deathBackdrop.x, deathBackdrop.y,
				deathBackdrop.width, deathBackdrop.height);
		shapeRenderer.end();
		batch.begin();
		fonts.get("NormalFont").draw(batch, deadLayout, deadPosition.x,
				deadPosition.y);
		fonts.get("NormalFont").draw(batch, restartLayout, restartPosition.x,
				restartPosition.y);
		batch.end();
	}

	private void drawScore() {
		fonts.get("NormalFont").draw(batch, dropShadowLayout,
				dropShadowPosition.x, dropShadowPosition.y);
		fonts.get("NormalFont").draw(batch, scoreLayout, scorePosition.x,
				scorePosition.y);
	}

	private String getScore() {
		return "$" + String.format("%07d", savings);
	}

	public void tick(Player player) {
		if (player.getScore() != savings) {
			savings = player.getScore();
			this.redoScoreLayout();
		}
		bar.updateHealth(player.getHealth());
	}

	private void redoScoreLayout() {
		scoreLayout = new GlyphLayout(fonts.get("NormalFont"), this.getScore(),
				Color.GREEN, this.getScore().length(), Align.left, false);
		dropShadowLayout = new GlyphLayout(fonts.get("NormalFont"),
				this.getScore(), Color.BLACK, this.getScore().length(),
				Align.left, false);

		scorePosition = new Vector2((Gdx.graphics.getWidth() / 2)
				- scoreLayout.width / 2, scoreLayout.height + 2);
		dropShadowPosition = new Vector2(
				((Gdx.graphics.getWidth() / 2) - scoreLayout.width / 2) + 2,
				scoreLayout.height);
	}
}