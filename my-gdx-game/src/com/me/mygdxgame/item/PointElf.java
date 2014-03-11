package com.me.mygdxgame.item;

import java.util.LinkedList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.mygdxgame.stage.World;

public class PointElf extends Enemy {

	public static final LinkedList<PointElf> uselessPointElf = new LinkedList<PointElf>();
	public static final float WIDTH = Self.IMG_WIDTH;
	public static final float HEIGHT = WIDTH;
	public static final float RADIUS = WIDTH / 2f;
	
	protected PointElf(float x, float y, AssetManager resources, World world,
			int hp) {
		super(x, y, WIDTH, HEIGHT, RADIUS, resources, world, hp);
		time = 0;
	}

	@Override
	public void dead() {
		PointItem point = PointItem.newPointItem(getCheckX(), getCheckY(),
				resources, world);
		world.addItem(point);
		setInUse(false);
	}

	public synchronized static PointElf newPointElf(float x, float y,
			AssetManager resources, World world, int hp) {
		PointElf pointElf;
		if (uselessPointElf.isEmpty()) {
			pointElf = new PointElf(x, y, resources, world, hp);
		} else {
			pointElf = uselessPointElf.poll();
			pointElf.setPosition(x, y);
			pointElf.hp = hp;
			pointElf.inUse = true;
			pointElf.clearActions();
		}
		return pointElf;
	}
	
	@Override
	public void recycle() {
		synchronized (PointElf.class) {
			setInUse(false);
			setShootPattern(null);
			uselessPointElf.add(this);
		}
	}

	@Override
	protected void loadAtlas(AssetManager resources) {
		Texture pic = resources.get("images/enemy2.jpg", Texture.class);
		TextureRegion[] frames = new TextureRegion(pic, 0, 0, 128, 32).split(
				32, 32)[0];
		selfIdle = new Animation(RUNNING_FRAME_DURATION, frames);
		frames = new TextureRegion(pic, 256, 0, 128, 32).split(32, 32)[0];
		moveRight = new Animation(RUNNING_FRAME_DURATION, frames);
		frames = new TextureRegion(pic, 256, 0, 128, 32).split(32, 32)[0];
		for (TextureRegion frame : frames) {
			frame.flip(true, false);
		}
		moveLeft = new Animation(RUNNING_FRAME_DURATION, frames);
	}

}
