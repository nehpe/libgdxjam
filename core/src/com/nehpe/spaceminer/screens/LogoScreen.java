package com.nehpe.spaceminer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.nehpe.spaceminer.SpaceMinerGame;

public class LogoScreen extends Screen {
	SpriteBatch batch;
	Color bColor;
	Texture logo;
	Texture logoBlock;
	Rectangle logoPositioning;
	Rectangle logoBlockPositioning;
	int logoScale;
	boolean blockOn = false;
	float blockTimer = 0.0f;
	boolean reverse = false;
	float opacity = 0.0f;
	float opacitySpeed = 0.5f;
	
	
	public LogoScreen(SpaceMinerGame game) {
		super(game);
		batch = new SpriteBatch();
		bColor = batch.getColor();
		
		logo = new Texture(Gdx.files.external("Code/libgdxjam/spaceminer/core/assets/logos/nehpe.png"));
		logoBlock = new Texture(Gdx.files.external("Code/libgdxjam/spaceminer/core/assets/logos/nehpe_block.png"));
		
		logoScale = 2;
		logoPositioning = new Rectangle(
			(Gdx.graphics.getWidth()/2 - (logo.getWidth()*logoScale)/2),
			(Gdx.graphics.getHeight()/2 - (logo.getHeight() * logoScale)/2),
			logo.getWidth()*logoScale,
			logo.getHeight()*logoScale
		);
		
		logoBlockPositioning = new Rectangle(logoPositioning);
		
		logoBlockPositioning.x += (logo.getWidth() * logoScale) + (2f * logoScale);
		logoBlockPositioning.width = logoBlock.getWidth()*logoScale;
		logoBlockPositioning.height = logoBlock.getHeight()*logoScale;
	}

	@Override
	public void draw() {
		batch.begin();
		
		batch.setColor(bColor.r, bColor.g, bColor.b, opacity);
		
		batch.draw(logo, logoPositioning.x, logoPositioning.y, logoPositioning.width, logoPositioning.height);
		
		if (blockOn) {
			batch.draw(
					logoBlock, 
					logoBlockPositioning.x, logoBlockPositioning.y,
					logoBlockPositioning.width, logoBlockPositioning.height
			);
		}
		
		batch.end();
	}

	@Override
	public void input() {
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.ENTER)) {
			this.nextScreen();
		}
	}

	@Override
	public void tick() {
		blockTimer += 1.0f * Gdx.graphics.getDeltaTime();
		if (blockTimer > 1.0f) { 
			blockOn = !blockOn;
			blockTimer = 0.0f;
		}
		
		if (!reverse) {
			opacity += opacitySpeed * Gdx.graphics.getDeltaTime();	
		} else {
			opacity -= opacitySpeed * Gdx.graphics.getDeltaTime();
		}
		
		if (opacity > 1.0f) {
			opacity = 1.0f;
			reverse = true;
		}
		
		if (opacity < 0.0f) {
			opacity = 0.0f;
			this.nextScreen();
		}
	}

	private void nextScreen() {
		this.game.nextScreen(new TitleScreen(this.game));
	}
}
