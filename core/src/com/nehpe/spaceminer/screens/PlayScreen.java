package com.nehpe.spaceminer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.nehpe.spaceminer.SpaceMinerGame;

public class PlayScreen extends Screen {

	
	public PlayScreen(SpaceMinerGame game) {
		super(game);
	}

	@Override
	public void draw() {

	}

	@Override
	public void input() {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void tick() {

	}

}
