package com.me.mygdxgame.stage;

import java.util.LinkedList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.me.mygdxgame.GameScreen;
import com.me.mygdxgame.item.Bullet;
import com.me.mygdxgame.item.Enemy;
import com.me.mygdxgame.item.Self;

public class WorldController {

	private World world;
	private Self self;
	private LinkedList<Bullet> uselessEnemyBullet;
	private AssetManager resources;
	private GameScreen parent;
	private float time;

	public WorldController(World world, AssetManager resources,
			GameScreen parent) {
		this.world = world;
		this.resources = resources;
		this.parent = parent;
		world.setSelf(Self.getInstance(World.CAMERA_WIDTH / 2, 10f, resources,
				"reimu"));
		self = world.getSelf();
		uselessEnemyBullet = new LinkedList<Bullet>();
		world.setBg(resources.get("images/bg1.jpg", Texture.class));
		time = 0;
		addDebugBullets();
	}

	private void addDebugBullets() {
		TextureRegion img = new TextureRegion(resources.get(
				"images/bullet2.jpg", Texture.class), 0, 0, 32, 32);
		Bullet b;
		for (int i = 0; i < 10; ++i) {
			b = newEnemyBullet((i * 1.5f + 5), 30, 1.2f, 1.2f, 0.4f, img);
			b.setOrigin(b.getWidth() * 4, b.getHeight() * 4);
			b.addAction(Actions.repeat(RepeatAction.FOREVER,
					Actions.rotateBy(MathUtils.random(-180, 180), 0.5f)));
			world.addEnemyBullet(b);
		}
	}

	public void update(float delta) {
		time += delta;
		world.updateTime(delta);
		world.getSelf().act(delta);
		for (Bullet b : world.getEnemyBullets()) {
			b.act(delta);
		}
		for (Bullet b : world.getSelfNormalBullets()) {
			b.act(delta);
		}
		for (Enemy e : world.getEnemies()) {
			e.act(delta);
		}
		detectCollision();
		detectOutOfWorld();
		recycleUselessBullets();
		// TODO recycle enemies
		selfShoot(delta);
	}

	private void selfShoot(float delta) {
		float tmp = time * 60f;
		if (tmp < 1)
			return;
		time -= 1f / 60f;
		Bullet myBullet1 = self.newNormalBullet(self.getX() + Self.IMG_WIDTH
				/ 2, self.getY() + Self.IMG_HEIGHT);
		Bullet myBullet2 = self.newNormalBullet(self.getX() + Self.IMG_WIDTH,
				self.getY() + Self.IMG_HEIGHT);
		world.addSelfBullet(myBullet1);
		world.addSelfBullet(myBullet2);
	}

	private void detectCollision() {
		for (Bullet b : world.getEnemyBullets()) {
			if (b.isHit(self)) {
				// TODO do sth
				uselessEnemyBullet.add(b);
				self.hitBy(b);
				break;
			}
		}
		for (Bullet b : world.getSelfNormalBullets()) {
			for (Enemy enemy : world.getEnemies()) {
				if (b.isHit(enemy)) {
					// TODO do sth
					self.recycleNormalBullet(b);
					enemy.hitBy(b);
				}
			}
		}
	}

	private void detectOutOfWorld() {
		for (Bullet b : world.getEnemyBullets()) {
			if (b.isOutOfWorld()) {
				b.clearActions();
				uselessEnemyBullet.add(b);
			}
		}
		for (Bullet b : world.getSelfNormalBullets()) {
			if (b.isOutOfWorld()) {
				self.recycleNormalBullet(b);
			}
		}
		if (self.getX() < 0) {
			self.setX(0);
		} else if (self.getX() + Self.IMG_WIDTH > World.CAMERA_WIDTH) {
			self.setX(World.CAMERA_WIDTH - Self.IMG_WIDTH);
		}
		if (self.getY() < 0) {
			self.setY(0);
		} else if (self.getY() + Self.IMG_HEIGHT > World.CAMERA_HEIGHT) {
			self.setY(World.CAMERA_HEIGHT - Self.IMG_HEIGHT);
		}
	}

	private void recycleUselessBullets() {
		for (Bullet b : uselessEnemyBullet) {
			world.removeEnemyBullet(b);
		}
		for (Bullet b : self.getUselessNormalBullet()) {
			world.removeSelfNormalBullet(b);
		}
	}

	public Bullet newEnemyBullet(float x, float y, float width, float height,
			float checkRadius, TextureRegion img) {
		if (uselessEnemyBullet.isEmpty()) {
			return new Bullet(x, y, width, height, checkRadius, img);
		} else {
			Bullet b = uselessEnemyBullet.removeFirst();
			b.init(x, y, width, height, checkRadius, img);
			return b;
		}
	}

	public void setSelfVerticalDirection(int i) {
		self.setVerticalDirection(i);
	}

	public void setSelfHorizentalDirection(int i) {
		self.setHorizentalDirection(i);
	}

	public void setSelfMoving(boolean b) {
		self.setMoving(b);
	}

	public void setSelfSlow(boolean b) {
		self.setLowSpeed(b);
	}

	public World getWorld() {
		return world;
	}
}
