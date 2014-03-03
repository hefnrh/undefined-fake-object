package com.me.mygdxgame.item;


public class Enemy extends Aircraft {
	
	public int hp;

	protected Enemy(float x, float y, float width, float height,
			float checkRadius) {
		super(x, y, width, height, checkRadius);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void hitBy(Item i) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void loadAtlas() {
		// TODO Auto-generated method stub
		
	}

}
