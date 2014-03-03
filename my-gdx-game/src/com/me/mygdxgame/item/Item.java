package com.me.mygdxgame.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.me.mygdxgame.stage.World;

public abstract class Item extends Actor {

	protected TextureRegion img;
	protected float checkRadius;
	private float imgRadius;

	protected Item(float x, float y, float width, float height,
			float checkRadius, TextureRegion img) {
		init(x, y, width, height, checkRadius, img);
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

	public void setCheckRadius(float radius) {
		this.checkRadius = radius;
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

	public float getImgRadius() {
		return imgRadius;
	}

	@Override
	public void draw(SpriteBatch sb, float alpha) {
		sb.draw(img, getX(), getY(), getOriginX(), getOriginY(), getWidth(),
				getHeight(), getScaleX(), getScaleY(), getRotation());
	}

	public void init(float x, float y, float width, float height,
			float checkRadius, TextureRegion img) {
		setBounds(x, y, width, height);
		this.img = img;
		this.checkRadius = checkRadius;
		imgRadius = (float) Math.sqrt(width * width + height * height) / 2;
	}

	public boolean isOutOfWorld() {
		float x = getX(), y = getY();
		return x + imgRadius < 0 || x - imgRadius > World.CAMERA_WIDTH
				|| y + imgRadius < 0 || y - imgRadius > World.CAMERA_HEIGHT;
	}
}