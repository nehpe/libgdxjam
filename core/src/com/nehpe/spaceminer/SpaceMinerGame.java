package com.nehpe.spaceminer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.nehpe.spaceminer.screens.LogoScreen;
import com.nehpe.spaceminer.screens.Screen;

public class SpaceMinerGame extends ApplicationAdapter {
	private Screen currentScreen;
	
	@Override
	public void create () {
		currentScreen = new LogoScreen(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		currentScreen.draw();
		currentScreen.tick();
		currentScreen.input();
	}

	public void nextScreen(Screen newScreen) {
		this.currentScreen = newScreen;
	}
}