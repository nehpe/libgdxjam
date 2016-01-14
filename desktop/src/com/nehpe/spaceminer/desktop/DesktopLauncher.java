package com.nehpe.spaceminer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nehpe.spaceminer.SpaceMinerGame;
import com.nehpe.utils.GameVars;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "SpaceMinerGame";
		config.width  = GameVars.WIDTH;
		config.height = GameVars.HEIGHT;
		config.x = 1920;
		config.y = 0;
		new LwjglApplication(new SpaceMinerGame(), config);
	}
}
