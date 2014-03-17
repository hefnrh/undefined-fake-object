package com.me.mygdxgame.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.me.mygdxgame.stage.World;

public abstract class PItem extends Item {

	public static final float TRACE_SPEED = 15f;
	public static final float WIDTH = 1f;
	public static final float HEIGHT = 1f;
	public static final float RADIUS = 0.5f;
	
	protected PItem(float x, float y, TextureRegion img, World world) {
		super(x, y, WIDTH, HEIGHT, RADIUS, img, world);
		addAction(Actions.repeat(RepeatAction.FOREVER, Actions.moveBy(0f, -4.8f, 1f)));
	}

	public abstract void hit(Self s);
	
	public abstract void recycle();
}
