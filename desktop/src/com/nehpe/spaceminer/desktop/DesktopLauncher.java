package com.nehpe.spaceminer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nehpe.spaceminer.SpaceMinerGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "SpaceMinerGame";
		config.width = SpaceMinerGame.WIDTH * SpaceMinerGame.SCALE;
		config.height = SpaceMinerGame.HEIGHT * SpaceMinerGame.SCALE;
		new LwjglApplication(new SpaceMinerGame(), config);
	}
}
