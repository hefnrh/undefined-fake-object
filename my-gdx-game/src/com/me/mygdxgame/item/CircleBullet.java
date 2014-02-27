package com.me.mygdxgame.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class CircleBullet extends Bullet {
	
	private static final Texture img = new Texture(Gdx.files.internal("images/bullet2.jpg"));;
	
	public CircleBullet(float speedx, float speedy, boolean selfBullet, float x, float y,
			float radius) {
		super(selfBullet, x, y, radius, speedx, speedy);
		imgBound = new Rectangle();
		imgBound.x = x - radius;
		imgBound.y = y - radius;
		imgBound.width = imgBound.height = radius * 2;
	}
	
	public CircleBullet(boolean selfBullet, float x, float y, float radius) {
		this(0, 0, selfBullet, x, y, radius);
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		imgBound.x = x - checkBound.radius;
		imgBound.y = y - checkBound.radius;
	}
	
	public void update(float delta) {
		// TODO delete?
		setPosition(position.x + delta * speedx, position.y + delta * speedy);
	}
	
	@Override
	public void draw(SpriteBatch sb) {
		// FIXME debug
		if (selfBullet) {
			sb.draw(img, imgBound.x, imgBound.y, imgBound.width, imgBound.height, 0, 32, 32, 32, false, false);
		} else {
			sb.draw(img, imgBound.x, imgBound.y, imgBound.width, imgBound.height, 0, 0, 32, 32, false, false);
		}
	}
	
	@Override
	public void hitBy(Item i) {
		// TODO
	}
}
