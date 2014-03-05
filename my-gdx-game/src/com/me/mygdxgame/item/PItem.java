package com.me.mygdxgame.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;

public class PItem extends Item {

	protected PItem(float x, float y, float width, float height,
			float checkRadius, TextureRegion img) {
		super(x, y, width, height, checkRadius, img);
		addAction(Actions.repeat(RepeatAction.FOREVER, Actions.moveBy(0f, 4.8f, 1f)));
	}

	@Override
	public void hitBy(Item i) {
		// TODO Auto-generated method stub

	}

}
