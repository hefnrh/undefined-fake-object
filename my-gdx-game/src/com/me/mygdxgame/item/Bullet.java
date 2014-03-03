package com.me.mygdxgame.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bullet extends Item {

	public Bullet(float x, float y, float width, float height,
			float checkRadius, TextureRegion img) {
		super(x, y, width, height, checkRadius, img);
	}

	@Override
	public void hitBy(Item i) {
		// TODO Auto-generated method stub
		
	}
	
}
