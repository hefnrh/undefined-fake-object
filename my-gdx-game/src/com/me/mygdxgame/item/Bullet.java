package com.me.mygdxgame.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public abstract class Bullet extends Item {
	
	protected boolean selfBullet;
	
	public Bullet(boolean selfBullet, float x, float y, float radius) {
		super(x, y, radius);
		this.selfBullet = selfBullet;
	}
	
	public Bullet(boolean selfBullet, float x, float y, float radius, float speedx, float speedy) {
		super(x, y, radius, speedx, speedy);
		this.selfBullet = selfBullet;
	}

	public float getSpeedX() {
		return speedx;
	}
	
	public float getSpeedY() {
		return speedy;
	}

	public void setSpeedX(float speedx) {
		this.speedx = speedx;
	}
	
	public void setSpeedY(float speedy) {
		this.speedy = speedy;
	}


	public boolean isSelfBullet() {
		return selfBullet;
	}
	
	public void update(float delta) {
		setPosition(position.x + delta * speedx, position.y + delta * speedy);
	}
	
	public abstract void draw(SpriteBatch batch);

}
