package com.nehpe.spaceminer.levels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.nehpe.spaceminer.SpaceMinerGame;

public class Level {
	TiledMap map;
	TiledMapTileLayer layer;
	Vector2 tileDimensions;
	
	public Level() {
		map = new TmxMapLoader().load("maps/large_test.tmx");
		layer = (TiledMapTileLayer) map.getLayers().get(0);
		System.out.println(layer.getWidth());
		System.out.println(layer.getHeight());
		tileDimensions = new Vector2(layer.getWidth(), layer.getHeight());
	}
	
	public void draw(SpriteBatch batch) {
		for (int x = 0; x < tileDimensions.x; x++) {
			for (int y = 0; y < tileDimensions.y; y++) {
				batch.draw(layer.getCell(x, y).getTile().getTextureRegion(),
						(16 * SpaceMinerGame.SCALE) * x,
						(16 * SpaceMinerGame.SCALE) * y,
						(16 * SpaceMinerGame.SCALE), 
						(16 * SpaceMinerGame.SCALE));
			}
		}
	}
}
