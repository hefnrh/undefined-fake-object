package com.me.mygdxgame.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Item extends Actor {

	protected TextureRegion img;
	protected float checkRadius;

	protected Item(float x, float y, float width, float height,
			float checkRadius, TextureRegion img) {
		setBounds(x, y, width, height);
		this.checkRadius = checkRadius;
		this.img = img;
	}

	public float getCheckX() {
		float dx = getOriginX(), dy = getOriginY();
		float ox = getX();
		float halfWidth = getWidth() / 2f, halfHeight = getHeight() / 2f;
		float deg = getRotation();
		float sin = MathUtils.sinDeg(deg), cos = MathUtils.cosDeg(deg);
		return ox + dx - cos * (dx - halfWidth) + sin * (dy - halfHeight);
	};

	public float getCheckY() {
		float dx = getOriginX(), dy = getOriginY();
		float oy = getY();
		float halfWidth = getWidth() / 2f, halfHeight = getHeight() / 2f;
		float deg = getRotation();
		float sin = MathUtils.sinDeg(deg), cos = MathUtils.cosDeg(deg);
		return oy + dy - sin * (dx - halfWidth) - cos * (dy - halfHeight);
	};

	public float getCheckRadius() {
		return checkRadius;
	}

	public boolean isHit(Item i) {
		float dx = getCheckX() - i.getCheckX();
		float dy = getCheckY() - i.getCheckY();
		float distance = i.checkRadius + checkRadius;
		return dx * dx + dy * dy < distance * distance;
	}

	public abstract void hitBy(Item i);

	public void setImg(TextureRegion img) {
		this.img = img;
	}

	@Override
	public void draw(SpriteBatch sb, float alpha) {
		sb.draw(img, getX(), getY(), getOriginX(), getOriginY(), getWidth(),
				getHeight(), getScaleX(), getScaleY(), getRotation());
	}

}