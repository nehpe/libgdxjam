package com.nehpe.spaceminer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.nehpe.spaceminer.SpaceMinerGame;

public class TitleScreen extends Screen {
	BitmapFont titleFont;
	BitmapFont normalFont;
	SpriteBatch batch;
	GlyphLayout layout;
	Vector2 textPosition;
	GlyphLayout instruction;
	Vector2 instructionPosition;

	GlyphLayout credit;
	Vector2 creditPosition;

	public TitleScreen(SpaceMinerGame game) {
		super(game);

		titleFont = new BitmapFont(Gdx.files.internal("fonts/TitleFont.fnt"));
		normalFont = new BitmapFont(Gdx.files.internal("fonts/PressStart.fnt"));
		batch = new SpriteBatch();
		layout = new GlyphLayout(titleFont, "Space Black Friday", Color.GREEN,
				18, Align.left, false);

		textPosition = new Vector2(
				(Gdx.graphics.getWidth() / 2 - layout.width / 2),
				((Gdx.graphics.getHeight() / 4) * 3 - layout.height / 2));

		instruction = new GlyphLayout(normalFont, "Press Enter to Play",
				Color.WHITE, 19, Align.left, false);
		instructionPosition = new Vector2(
				(Gdx.graphics.getWidth() / 2 - instruction.width / 2),
				((Gdx.graphics.getHeight() / 4) * 1 - instruction.height / 2));

		String creditString = "By nehpe for LibGDXJam 2015 (nehpe.com)";
		credit = new GlyphLayout(normalFont, creditString, Color.GRAY,
				creditString.length(), Align.left, false);
		creditPosition = new Vector2(
				(Gdx.graphics.getWidth() / 2 - credit.width / 2),
				(credit.height));
	}

	@Override
	public void draw() {
		batch.begin();

		titleFont.draw(batch, layout, textPosition.x, textPosition.y);
		normalFont.draw(batch, instruction, instructionPosition.x,
				instructionPosition.y);
		normalFont.draw(batch, credit, creditPosition.x, creditPosition.y);

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
