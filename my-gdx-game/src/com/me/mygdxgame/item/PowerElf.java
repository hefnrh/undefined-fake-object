package com.me.mygdxgame.item;

import java.util.LinkedList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.me.mygdxgame.stage.World;

public class PowerElf extends Enemy {

	public static final LinkedList<PowerElf> uselessPowerElf = new LinkedList<PowerElf>();
	public static final float WIDTH = Self.IMG_WIDTH;
	public static final float HEIGHT = WIDTH;
	public static final float RADIUS = WIDTH / 2f;

	protected PowerElf(float x, float y, AssetManager resources, World world,
			int hp) {
		super(x, y, WIDTH, HEIGHT, RADIUS, resources, world, hp);
		time = 0;
	}

	@Override
	public void dead() {
		PowerItem power = PowerItem.newPowerItem(getCheckX(), getCheckY(),
				resources, world);
		world.addItem(power);
		setInUse(false);
	}

	@Override
	protected void loadAtlas(AssetManager resources) {
		Texture pic = resources.get("images/enemy2.jpg", Texture.class);
		TextureRegion[] frames = new TextureRegion(pic, 0, 32, 128, 32).split(
				32, 32)[0];
		selfIdle = new Animation(RUNNING_FRAME_DURATION, frames);
		frames = new TextureRegion(pic, 256, 32, 128, 32).split(32, 32)[0];
		moveRight = new Animation(RUNNING_FRAME_DURATION, frames);
		frames = new TextureRegion(pic, 256, 32, 128, 32).split(32, 32)[0];
		for (TextureRegion frame : frames) {
			frame.flip(true, false);
		}
		moveLeft = new Animation(RUNNING_FRAME_DURATION, frames);
		currentState = selfIdle;
	}

	@Override
	public void shoot(float delta, Bullet b, Action a) {
	}

	@Override
	public void aimShoot(float delta, Bullet b, float speed) {
		Self s = world.getSelf();
		float vx = s.getCheckX() - getCheckX();
		float vy = s.getCheckY() - getCheckY();
		float v = (float) Math.sqrt(vx * vx + vy * vy);
		float f = speed * delta / v;
		b.setPosition(getCheckX() - b.getWidth(), getCheckY() - b.getHeight());
		b.addAction(Actions.repeat(RepeatAction.FOREVER,
				Actions.moveBy(f * vx, f * vy)));
		world.addEnemyBullet(b);
	}

	@Override
	public void randomShoot(float delta, Bullet b, float speed) {
	}

	public synchronized static PowerElf newPowerElf(float x, float y,
			AssetManager resources, World world, int hp) {
		PowerElf powerElf;
		if (uselessPowerElf.isEmpty()) {
			powerElf = new PowerElf(x, y, resources, world, hp);
		} else {
			powerElf = uselessPowerElf.poll();
			powerElf.setPosition(x, y);
			powerElf.hp = hp;
			powerElf.inUse = true;
			powerElf.clearActions();
		}
		return powerElf;
	}

	@Override
	public void recycle() {
		synchronized (PowerElf.class) {
			setInUse(false);
			uselessPowerElf.add(this);
		}
	}
}
