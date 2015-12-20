package com.nehpe.spaceminer.screens;

import com.badlogic.gdx.Game;
import com.nehpe.spaceminer.SpaceMinerGame;

public abstract class Screen {
	
	SpaceMinerGame game;
	
	public Screen(SpaceMinerGame game) {
		this.game = game;
	}
	
	public abstract void draw();
	public abstract void input();
	public abstract void tick();

}
