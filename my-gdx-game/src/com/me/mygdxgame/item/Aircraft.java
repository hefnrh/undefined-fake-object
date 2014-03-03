package com.me.mygdxgame.item;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Aircraft extends Item {
	
	protected static final float RUNNING_FRAME_DURATION = 0.06f;
	
	protected float time = 0f;
	protected Animation selfIdle;
	
	protected Aircraft(float x, float y, float width, float height,
			float checkRadius, AssetManager resources) {
		super(x, y, width, height, checkRadius, null);
		loadAtlas(resources);
	}
	
	protected abstract void loadAtlas(AssetManager resources);
	
	@Override
	public void act(float delta) {
		time += delta;
		super.act(delta);
	}

	@Override
	public void draw(SpriteBatch sb, float alpha) {
		img = selfIdle.getKeyFrame(time, true);
		super.draw(sb, alpha);
	}
}
