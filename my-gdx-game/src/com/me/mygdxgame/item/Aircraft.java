package com.me.mygdxgame.item;

import com.badlogic.gdx.math.Vector2;

public abstract class Aircraft extends Item {
	
	protected static final float RUNNING_FRAME_DURATION = 0.06f;
	
	protected float time = 0f;
	protected boolean moving;
	
	protected Aircraft(Vector2 position, float checkRadius, float speedx, float speedy) {
		super(position, checkRadius, speedx, speedy);
		moving = true;
	}
	protected Aircraft(Vector2 position, float checkRadius) {
		super(position, checkRadius);
		moving = false;
	}
	
	public void addTime(float delta) {
		time += delta;
	}
	
	public float getTime() {
		return time;
	}
	
	public void setMoving(boolean b) {
		moving = b;
	}
	
	public void update(float delta) {
		time += delta;
		if (moving) {
			setPosition(position.x + delta * speedx, position.y + delta * speedy);
		}
	}

}
