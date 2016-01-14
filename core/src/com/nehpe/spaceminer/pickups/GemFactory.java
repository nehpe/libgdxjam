package com.nehpe.spaceminer.pickups;

import com.badlogic.gdx.math.Vector2;

public class GemFactory {
	
	public static Pickup Get(Vector2 position) {
		int random = (int)Math.floor(Math.random()*12);
		
		if (random == 0) {
			return new OrangeGem(position);
		} else if (random > 0 && random < 3) {
			return new RedGem(position);
			
		} else if (random >= 3 && random < 6) {
			return new BlueGem(position);
		}
		return new BrownGem(position);
	}

}
