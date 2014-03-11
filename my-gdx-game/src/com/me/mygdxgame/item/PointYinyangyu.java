package com.me.mygdxgame.item;

import java.util.LinkedList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.me.mygdxgame.stage.World;

public class PointYinyangyu extends Yinyangyu {

	public static LinkedList<PointYinyangyu> uselessPointYinYangyu = new LinkedList<PointYinyangyu>();

	public PointYinyangyu(float x, float y, AssetManager resources,
			World world, int hp) {
		super(x, y, resources, world, hp);
	}

	@Override
	public void dead() {
		PointItem point;
		float x = getCheckX(), y = getCheckY();
		for (int i = 0; i < 5; ++i) {
			point = PointItem.newPointItem(MathUtils.cosDeg(72 * i) * RADIUS + x,
					MathUtils.sinDeg(72 * i) * RADIUS + y, resources, world);
			world.addItem(point);
		}
		inUse = false;
	}

	@Override
	public void recycle() {
		synchronized (PointYinyangyu.class) {
			inUse = false;
			shootPattern = null;
			uselessPointYinYangyu.add(this);
		}
	}

	@Override
	protected void loadAtlas(AssetManager resources) {
		Texture enemy1 = resources.get("images/enemy1.jpg", Texture.class);
		TextureRegion[] frames = {new TextureRegion(enemy1, 192, 192, 32, 32)};
		selfIdle = new Animation(RUNNING_FRAME_DURATION, frames);
		moveLeft = moveRight = selfIdle;
		balls = new TextureRegion(enemy1, 192, 224, 32, 32);
	}

	public static PointYinyangyu newPointYinyangyu(float x, float y,
			AssetManager resources, World world, int hp) {
		PointYinyangyu yyy;
		if (uselessPointYinYangyu.isEmpty()) {
			yyy = new PointYinyangyu(x, y, resources, world, hp);
		} else {
			yyy = uselessPointYinYangyu.poll();
			yyy.setPosition(x, y);
			yyy.inUse = true;
			yyy.hp = hp;
			yyy.clearActions();
		}
		return yyy;
	}

}
