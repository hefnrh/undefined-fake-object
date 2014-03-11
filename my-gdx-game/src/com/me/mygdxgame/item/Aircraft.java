package com.me.mygdxgame.item;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.mygdxgame.stage.World;

public abstract class Aircraft extends Item {
	
	protected static final float RUNNING_FRAME_DURATION = 0.06f;
	
	protected float time = 0f;
	protected Animation selfIdle;
	protected Animation moveLeft;
	protected Animation moveRight;
	protected Animation currentState;
	protected float lastX;
	protected AssetManager resources;
	
	protected Aircraft(float x, float y, float width, float height,
			float checkRadius, AssetManager resources, World world) {
		super(x, y, width, height, checkRadius, null, world);
		this.resources = resources;
		lastX = x;
		loadAtlas(resources);
		currentState = selfIdle;
		inUse = true;
	}
	
	protected abstract void loadAtlas(AssetManager resources);
	
	@Override
	public void act(float delta) {
		time += delta;
		super.act(delta);
		float currentX = getX();
		if (lastX < currentX) {
			currentState = moveRight;
		} else if (lastX > currentX) {
			currentState = moveLeft;
		} else {
			currentState = selfIdle;
		}
		shoot(delta);
	}

	@Override
	public void draw(SpriteBatch sb, float alpha) {
		img = currentState.getKeyFrame(time, true);
		super.draw(sb, alpha);
	}
	
	public abstract void shoot(float delta);
}
