package com.me.mygdxgame.item;

import java.util.LinkedList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.me.mygdxgame.stage.World;

public class Butterfly extends Enemy {

	public static final LinkedList<Butterfly> uselessButterfly = new LinkedList<Butterfly>();
	public static final float WIDTH = Self.IMG_WIDTH * 2f;
	public static final float HEIGHT = WIDTH;
	public static final float RADIUS = WIDTH / 2f;

	protected Butterfly(float x, float y, AssetManager resources, World world,
			int hp) {
		super(x, y, WIDTH, HEIGHT, RADIUS, resources, world, hp);
		time = 0;
	}

	@Override
	public void dead() {
		PowerItem power;
		PointItem point;
		float x = getCheckX(), y = getCheckY();
		for (int i = 0; i < 5; ++i) {
			power = PowerItem.newPowerItem(MathUtils.cosDeg(72 * i) * RADIUS + x,
					MathUtils.sinDeg(72 * i) * RADIUS + y, resources, world);
			point = PointItem.newPointItem(MathUtils.cosDeg(72 * i + 36) * RADIUS + x,
					MathUtils.sinDeg(72 * i + 36) * RADIUS + y, resources, world);
			world.addItem(power);
			world.addItem(point);
		}
		setInUse(false);
	}

	@Override
	protected void loadAtlas(AssetManager resources) {
		Texture pic = resources.get("images/enemy2.jpg", Texture.class);
		TextureRegion[] frames = new TextureRegion(pic, 0, 128, 256, 64).split(
				64, 64)[0];
		selfIdle = new Animation(RUNNING_FRAME_DURATION, frames);
		frames = new TextureRegion[4];
		TextureRegion[][] tmp = new TextureRegion(pic, 384, 0, 128, 128).split(
				64, 64);
		frames[0] = tmp[0][0];
		frames[1] = tmp[0][1];
		frames[2] = tmp[1][0];
		frames[3] = tmp[1][1];
		moveRight = new Animation(RUNNING_FRAME_DURATION, frames);
		frames = new TextureRegion[4];
		tmp = new TextureRegion(pic, 384, 0, 128, 128).split(64, 64);
		frames[0] = tmp[0][0];
		frames[1] = tmp[0][1];
		frames[2] = tmp[1][0];
		frames[3] = tmp[1][1];
		for (TextureRegion frame : frames) {
			frame.flip(true, false);
		}
		moveLeft = new Animation(RUNNING_FRAME_DURATION, frames);
	}

	public synchronized static Butterfly newButterfly(float x, float y,
			AssetManager resources, World world, int hp) {
		Butterfly butterfly;
		if (uselessButterfly.isEmpty()) {
			butterfly = new Butterfly(x, y, resources, world, hp);
		} else {
			butterfly = uselessButterfly.poll();
			butterfly.setPosition(x, y);
			butterfly.hp = hp;
			butterfly.inUse = true;
			butterfly.clearActions();
		}
		return butterfly;
	}

	@Override
	public void recycle() {
		synchronized (Butterfly.class) {
			setInUse(false);
			setShootPattern(null);
			uselessButterfly.add(this);
		}
	}

}
