package com.me.mygdxgame.item;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Item {
	
	protected Circle checkBound;
	protected Rectangle imgBound;
	protected Vector2 position;
	protected float speedx;
	protected float speedy;
	
	protected Item(Vector2 position, float checkRadius, float speedx, float speedy) {
		this.position = position;
		checkBound = new Circle(position.x, position.y, checkRadius);
		this.speedx = speedx;
		this.speedy = speedy;
		imgBound = new Rectangle();
	}
	
	protected Item(Vector2 position, float checkRadius) {
		this(position, checkRadius, 0f, 0f);
	}
	
	protected Item(float x, float y, float checkRadius, float speedx, float speedy) {
		this(new Vector2(x, y), checkRadius, speedx, speedy);
	}
	
	protected Item(float x, float y, float checkRadius) {
		this(new Vector2(x, y), checkRadius);
	}
	
	public void setPosition(float x, float y) {
		position.x = checkBound.x = x;
		position.y = checkBound.y = y;
	}
	
	public Vector2 getPosition() {
		return position;
	}

	public Circle getCheckBound() {
		return checkBound;
	}

	public Rectangle getImgBound() {
		return imgBound;
	}
	
	public boolean isHit(Item i) {
		float dx = position.x - i.position.x;
		float dy = position.y - i.position.y;
		float distance = i.checkBound.radius + checkBound.radius;
		return dx * dx + dy * dy < distance * distance;
	}
	
	public void setSpeedx(float speedx) {
		this.speedx = speedx;
	}
	
	public void setSpeedy(float speedy) {
		this.speedy = speedy;
	}
	
	public abstract void hitBy(Item i);
	
}
