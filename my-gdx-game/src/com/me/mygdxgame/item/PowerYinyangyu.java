package com.me.mygdxgame.item;

import java.util.LinkedList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.me.mygdxgame.stage.World;

public class PowerYinyangyu extends Yinyangyu {

	public static LinkedList<PowerYinyangyu> uselessPowerYinYangyu = new LinkedList<PowerYinyangyu>();

	public PowerYinyangyu(float x, float y, AssetManager resources,
			World world, int hp) {
		super(x, y, resources, world, hp);
	}

	@Override
	public void dead() {
		PowerItem power;
		float x = getCheckX(), y = getCheckY();
		for (int i = 0; i < 5; ++i) {
			power = PowerItem.newPowerItem(MathUtils.cosDeg(72 * i) * RADIUS + x,
					MathUtils.sinDeg(72 * i) * RADIUS + y, resources, world);
			world.addItem(power);
		}
		inUse = false;
	}

	@Override
	public void recycle() {
		synchronized (PowerYinyangyu.class) {
			inUse = false;
			shootPattern = null;
			uselessPowerYinYangyu.add(this);
		}
	}

	@Override
	protected void loadAtlas(AssetManager resources) {
		Texture enemy1 = resources.get("images/enemy1.jpg", Texture.class);
		TextureRegion[] frames = {new TextureRegion(enemy1, 128, 192, 32, 32)};
		selfIdle = new Animation(RUNNING_FRAME_DURATION, frames);
		moveLeft = moveRight = selfIdle;
		balls = new TextureRegion(enemy1, 128, 224, 32, 32);
	}

	public static synchronized PowerYinyangyu newPowerYinyangyu(float x, float y,
			AssetManager resources, World world, int hp) {
		PowerYinyangyu yyy;
		if (uselessPowerYinYangyu.isEmpty()) {
			yyy = new PowerYinyangyu(x, y, resources, world, hp);
		} else {
			yyy = uselessPowerYinYangyu.poll();
			yyy.setPosition(x, y);
			yyy.setRotation(0);
			yyy.inUse = true;
			yyy.hp = hp;
			yyy.clearActions();
		}
		return yyy;
	}

}
