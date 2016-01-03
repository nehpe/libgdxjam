package com.nehpe.spaceminer.weapons;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.utils.GameVars.Direction;
import com.nehpe.spaceminer.entities.Projectile;
import com.nehpe.utils.GameVars;

public class Pistol extends Weapon {
	Texture image;
	TextureRegion[][] frames;
	HashMap<Direction, Vector2> posOffsets;
	
	public Pistol() {
		image = new Texture(Gdx.files.internal("weapons/Pistol.png"));
		frames = TextureRegion.split(image, 16, 16);
		posOffsets = new HashMap<Direction, Vector2>();
		posOffsets.put(Direction.DOWN, new Vector2(0,0));
		posOffsets.put(Direction.LEFT, new Vector2(6,4));
		posOffsets.put(Direction.RIGHT, new Vector2(6,4));
		posOffsets.put(Direction.UP, new Vector2(12,6));
	}
	
	@Override
	public void draw(SpriteBatch batch, Vector2 position, Direction currentDirection) {
		switch(currentDirection) {
		case LEFT:
			batch.draw(frames[(int)GameVars.WeaponFrameLeft.x][(int)GameVars.WeaponFrameLeft.y], position.x, position.y);
			break;
		case RIGHT:
			batch.draw(frames[(int)GameVars.WeaponFrameRight.x][(int)GameVars.WeaponFrameRight.y], position.x, position.y);
			break;
		case UP:
			batch.draw(frames[(int)GameVars.WeaponFrameUp.x][(int)GameVars.WeaponFrameUp.y], position.x, position.y);
			break;
		case DOWN:
			batch.draw(frames[(int)GameVars.WeaponFrameDown.x][(int)GameVars.WeaponFrameDown.y], position.x, position.y);
			break;
		}
	}

	@Override
	public void tick() {
		
	}

	@Override
	public Projectile fire(Direction direction, Vector2 position) {
		Vector2 movement = new Vector2(0,0);
		Vector2 positionOffset = null;
		Vector2 fixedPosition = new Vector2(position.x, position.y);
		switch(direction) {
		case LEFT:
			movement.x = -1;
			positionOffset = posOffsets.get(Direction.LEFT);
			break;
		case RIGHT:
			movement.x = 1;
			positionOffset = posOffsets.get(Direction.RIGHT);
			break;
		case UP:
			movement.y = 1;
			positionOffset = posOffsets.get(Direction.UP);
			break;
		case DOWN:
			movement.y = -1;
			positionOffset = posOffsets.get(Direction.DOWN);
			break;
		}
		
		fixedPosition.x += positionOffset.x;
		fixedPosition.y += positionOffset.y;
		
		return new Projectile(fixedPosition, movement);
	}
}