package com.me.mygdxgame.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.me.mygdxgame.stage.World;

public abstract class PItem extends Item {

	public static final float TRACE_SPEED = 45f;
	
	protected PItem(float x, float y, float width, float height,
			float checkRadius, TextureRegion img, World world) {
		super(x, y, width, height, checkRadius, img, world);
		addAction(Actions.repeat(RepeatAction.FOREVER, Actions.moveBy(0f, -4.8f, 1f)));
	}

	public abstract void hit(Self s);
	
	public abstract void recycle();
}
