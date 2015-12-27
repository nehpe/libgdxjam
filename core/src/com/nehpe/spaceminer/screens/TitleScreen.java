package com.nehpe.spaceminer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.spaceminer.SpaceMinerGame;

public class TitleScreen extends Screen {
	BitmapFont titleFont;
	BitmapFont normalFont;
	SpriteBatch batch;
	GlyphLayout layout;
	Vector2 textPosition;
	
	public TitleScreen(SpaceMinerGame game) {
		super(game);
		
		titleFont = new BitmapFont(Gdx.files.internal("fonts/TitleFont.fnt"));
		normalFont = new BitmapFont(Gdx.files.internal("fonts/PressStart.fnt"));
		batch = new SpriteBatch();
		layout = new GlyphLayout();
		layout.setText(titleFont, "Space Miner");
		
		textPosition = new Vector2((Gdx.graphics.getWidth()/2 - layout.width/2), ((Gdx.graphics.getHeight()/4)*3 - layout.height/2));
	}

	@Override
	public void draw() {
		batch.begin();
		
		titleFont.draw(batch, layout, textPosition.x, textPosition.y);
		normalFont.draw(batch, "Press Enter", 50, 50);
		
		batch.end();
	}

	@Override
	public void input() {
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			this.play();
		}
	}

	private void play() {
		this.game.nextScreen(new PlayScreen(this.game));
	}

	@Override
	public void tick() {

	}

}
