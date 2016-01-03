package com.nehpe.utils;

import com.badlogic.gdx.math.Vector2;

public class GameVars {
	
	public enum Direction {
		UP, LEFT, DOWN, RIGHT
	}
	
	public static int WIDTH  = 1280;
	public static int HEIGHT = 720;
	
	public static Vector2 WeaponFrameDown = new Vector2(0, 0);
	public static Vector2 WeaponFrameRight = new Vector2(0, 1);
	public static Vector2 WeaponFrameUp = new Vector2(0, 2);
	public static Vector2 WeaponFrameLeft = new Vector2(0, 3);
}
