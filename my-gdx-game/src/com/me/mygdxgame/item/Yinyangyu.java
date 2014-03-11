package com.me.mygdxgame.item;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.mygdxgame.stage.World;

public abstract class Yinyangyu extends Enemy {

	public static final float WIDTH = 1f;
	public static final float HEIGHT = 1f;
	public static final float RADIUS = 0.5f;
	public static final float ROTATE_SPEED = 270f;

	protected TextureRegion balls;

	public Yinyangyu(float x, float y, AssetManager resources, World world,
			int hp) {
		super(x, y, WIDTH, HEIGHT, RADIUS, resources, world, hp);
		time = 0;
	}

	@Override
	public void draw(SpriteBatch sb, float alpha) {
		sb.draw(balls, getX(), getY(), WIDTH / 2f, HEIGHT / 2f, getWidth(),
				getHeight(), getScaleX(), getScaleY(), time * ROTATE_SPEED);
		super.draw(sb, alpha);
	}

}
