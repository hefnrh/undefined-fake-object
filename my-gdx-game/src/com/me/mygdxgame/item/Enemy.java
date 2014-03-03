package com.me.mygdxgame.item;

import com.badlogic.gdx.assets.AssetManager;


public class Enemy extends Aircraft {
	
	public int hp;
	
	protected Enemy(float x, float y, float width, float height,
			float checkRadius, AssetManager resources) {
		super(x, y, width, height, checkRadius, resources);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void hitBy(Item i) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void loadAtlas(AssetManager resources) {
		// TODO Auto-generated method stub
		
	}

}
