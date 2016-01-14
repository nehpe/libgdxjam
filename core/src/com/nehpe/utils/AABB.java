package com.nehpe.utils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class AABB {
	Vector2 position;
	Vector2 size;
	
	public AABB(Vector2 position, Vector2 size) {
		this.position = position;
		this.size = size;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public Vector2 getSize() {
		return size;
	}
	
	public Rectangle getRect() {
		return new Rectangle(
				position.x, position.y,
				size.x, size.y
				);
	}
	
	
}
