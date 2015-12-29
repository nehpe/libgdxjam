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
	TiledMapTileLayer collidableLayer;
	Vector2 tileDimensions;
	
	public Level() {
		map = new TmxMapLoader().load("maps/large_test.tmx");
		layer = (TiledMapTileLayer) map.getLayers().get(0);
		collidableLayer = (TiledMapTileLayer) map.getLayers().get(1);
		
		tileDimensions = new Vector2(layer.getWidth(), layer.getHeight());
	}
	
	public int[][] getCollidables() {
		int[][] collidables = new int[(int)tileDimensions.x][(int)tileDimensions.y];
		for (int x = 0; x < tileDimensions.x; x++) {
			for (int y = 0; y < tileDimensions.y; y++) {
				if (collidableLayer.getCell(x, y) != null) {
					collidables[x][y] = 1;
				}
			}
		}
		return collidables;
	}
	
	public void draw(SpriteBatch batch) {
		for (int x = 0; x < tileDimensions.x; x++) {
			for (int y = 0; y < tileDimensions.y; y++) {
				batch.draw(layer.getCell(x, y).getTile().getTextureRegion(),
						(16) * x,
						(16) * y,
						(16), 
						(16));
			}
		}
	}
}
